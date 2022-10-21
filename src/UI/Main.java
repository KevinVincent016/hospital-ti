package UI;

import Model.GeneralPropuseUnit;
import Model.HematologyUnit;
import Structures.Stack;
import Model.Patient;
import Model.ReceptionUnit;
import com.google.gson.Gson;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Main {

    static ReceptionUnit receptionUnit = new ReceptionUnit();
    static GeneralPropuseUnit generalPropuseUnit = new GeneralPropuseUnit();
    static HematologyUnit hematologyUnit = new HematologyUnit();

    static Stack<String> undo = new Stack<>();

    public static void main(String[] args) {

        Thread hilo = new Thread(runnable);
        hilo.start();

        ArrayList<Patient> temp = new ArrayList<>();
        loadDataBaseFromJson(temp);
        int index = 0;

        if (temp.size() > 0) {
            for (int i = 0; i < temp.size(); i++) {
                receptionUnit.getDatabase().insert(i, temp.get(i));
                index = i + 1;
            }
        }

        int opcion;

        do {
            opcion = menu();
            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, "If you wish wish to undo type the command : UNDO");
                    Patient patient = registerPatients(index);

                    if (patient == null) {
                        break;
                    } else {
                        receptionUnit.getReception().enqueue(patient);
                        receptionUnit.getDatabase().insert(index, patient);
                        index++;

                        if (patient.getPrioridad() == 0) {
                            generalPropuseUnit.addToQueue(patient);
                        } else {
                            hematologyUnit.addToQueue(patient);
                        }
                        break;
                    }

                case 2:
                    if (receptionUnit.getReception().isEmpty() != true) {
                        JOptionPane.showMessageDialog(null, "The person that is going to be removed is: ");
                        JOptionPane.showMessageDialog(null,
                                "Name: " + receptionUnit.getReception().peek().getNombre() + "\n" +
                                        "ID: " + receptionUnit.getReception().peek().getCedula() + "\n" +
                                        "Age: " + receptionUnit.getReception().peek().getEdad() + "\n");
                        receptionUnit.getReception().dequeue();
                    } else {
                        JOptionPane.showMessageDialog(null, "There is no people on the queue");
                    }
                    break;
                case 3:
                    int option;
                    do {
                        option = menuGeneralPropuse();

                        switch (option) {
                            case 1:
                                JOptionPane.showMessageDialog(null, "Patient " + generalPropuseUnit.getGPQueue().peek().getNombre()
                                        + " has been attended");
                                generalPropuseUnit.removeFromQueue();
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null, "you are now exiting General Unit...");
                                option = 2;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "ERROR !! THATS NOT AN OPTION");
                                break;
                        }

                    } while (option != 2);
                    break;
                case 4:
                    int option2;
                    do {
                        option2 = menuHematologyUnit();

                        switch (option2) {
                            case 1:
                                JOptionPane.showMessageDialog(null, "Patient " + hematologyUnit.getHPrioQueue().maximun().getNombre()
                                        + " has been attended");
                                hematologyUnit.removePacient();
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null, "you are now exiting Hematology Unit...");
                                option2 = 2;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "ERROR !! THATS NOT AN OPTION");
                                break;
                        }

                    } while (option2 != 2);
                    break;
                case 5:
                    saveDataBaseInJson(receptionUnit.getArrayFromDB());
                    JOptionPane.showMessageDialog(null, "See you !!! Thank you!");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "¡¡¡ERROR THATS NOT AN OPTION!!!");
                    break;
            }
        } while (opcion != 5);

    }

    public static int menu() {
        int option = Integer.parseInt(JOptionPane.showInputDialog("Welcome to the EPS \nMenu: \n 1. Put a patient on the reception queue. " +
                "\n 2. Remove a patient from the reception queue." + "\n 3. Enter to General Atention " + "\n 4. Enter to Hematology " + "\n 5. Exit"));
        return option;
    }

    public static int menuGeneralPropuse() {
        int option = Integer.parseInt(JOptionPane.showInputDialog("Welcome to General Unit \n" + "The first person in the queue is: \n" +
                generalPropuseUnit.printQueue() + "\n" + "Menu: \n" + "1. Attend the patient \n" + "2. Return to main menu"));
        return option;
    }

    public static int menuHematologyUnit() {
        int option = Integer.parseInt(JOptionPane.showInputDialog("Welcome to Hematology Unit \n" + "The first person in the queue is: \n" +
                hematologyUnit.printQueue() + "Menu: \n" + "1. Attend the patient \n" + "2. Return to main menu \n"));
        return option;
    }

    // METODO PARA BORRAR CADA DOS MINUTOS
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    // En él, hacemos que el hilo duerma
                    Thread.sleep(120000);
                    JOptionPane.showMessageDialog(null, "2 minutes have passed....");
                    int temp = (int) (Math.random() * 2 + 1);
                    ;

                    if (temp == 1) {
                        if (hematologyUnit.getHPrioQueue().isEmpty() == false) {
                            JOptionPane.showMessageDialog(null, "The person that is going to be removed is: \n" + hematologyUnit.removePacient());
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no people on the hematology queue");
                        }
                    }
                    if (temp == 2) {
                        if (generalPropuseUnit.getGPQueue().isEmpty() == false) {
                            JOptionPane.showMessageDialog(null, "The person that is going to be removed is: \n" + generalPropuseUnit.removeFromQueue());
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no people on the general queue");
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static Patient registerPatients(int i) {
        String nameOption;
        String idOption;
        String gnerOption;
        boolean pregnantOption;
        int ageOption;
        int illOption;
        int priority;

        String auxName = regPatientName(i);
        if (auxName == "exit") {
            return null;
        } else {
            nameOption = auxName;
        }

        String auxID = regPatientID(i);
        while (auxID.equals("UNDO")) {
            if (undo.pop().equals("regID")) {
                auxName = regPatientName(i);
                if (auxName.equals("exit")) {
                    return null;
                }
                auxID = regPatientID(i);
            }
        }

        String auxGner = regPatientGner(i);
        while (auxGner.equals("UNDO")) {
            if (undo.pop().equals("regGner")) {
                auxID = regPatientID(i);
                if (auxID.equals("UNDO")) {
                    auxName = regPatientName(i);
                    if (auxName.equals("exit")) {
                        return null;
                    }
                    auxID = regPatientID(i);
                }
                auxGner = regPatientGner(i);
            }
        }

        String auxPreg = "";
        if (auxGner.equals("1")){
            auxPreg = regPatientPregnant(i);
            while (auxPreg.equals("UNDO")){
                if (undo.pop().equals("regPreg")){
                    auxGner = regPatientGner(i);
                    if (auxGner.equals("UNDO")){
                        auxID = regPatientID(i);
                        if (auxID.equals("UNDO")){
                            auxName = regPatientName(i);
                            if (auxName.equals("exit")){
                                return null;
                            }
                            auxID = regPatientID(i);
                        }
                        auxGner = regPatientGner(i);
                        if (auxGner == "2"){
                            break;
                        }
                    }
                    auxPreg = regPatientPregnant(i);
                }
            }
        }

        String auxAge = regPatientAge(i);
        while (auxAge.equals("UNDO")){
            if (undo.pop().equals("regAge")){
                if (auxGner.equals("1")){
                    auxPreg = regPatientPregnant(i);
                    if (auxPreg.equals("UNDO")){
                        auxGner = regPatientGner(i);
                        if (auxGner.equals("UNDO")){
                            auxID = regPatientID(i);
                            if (auxID.equals("UNDO")){
                                auxName = regPatientName(i);
                                if (auxName.equals("exit")){
                                    return null;
                                }
                                auxID = regPatientID(i);
                            }
                            auxGner = regPatientGner(i);
                        }
                        if (auxGner.equals("1")){
                            regPatientPregnant(i);
                        }
                    }
                    auxAge = regPatientAge(i);
                }else {
                    auxGner = regPatientGner(i);
                    if (auxGner.equals("UNDO")){
                        auxID = regPatientID(i);
                        if (auxID.equals("UNDO")){
                            auxName = regPatientName(i);
                            if (auxName.equals("exit")){
                                return null;
                            }
                            auxID = regPatientID(i);
                        }
                        auxGner = regPatientGner(i);
                    }
                    auxAge = regPatientAge(i);
                }
            }
        }

        String auxIll = regPatientIll(i);
        while(auxIll.equals("UNDO")){
            if (undo.pop().equals("regIll")){
                auxAge = regPatientAge(i);
                if (auxAge.equals("UNDO")){
                    if (auxGner.equals("1")){
                        auxPreg = regPatientPregnant(i);
                        if (auxPreg.equals("UNDO")){
                            auxGner = regPatientGner(i);
                            if (auxGner.equals("UNDO")){
                                auxID = regPatientID(i);
                                if (auxID.equals("UNDO")){
                                    auxName = regPatientName(i);
                                    if (auxName.equals("exit")){
                                        return null;
                                    }
                                    auxID = regPatientID(i);
                                }
                                auxGner = regPatientGner(i);
                            }
                            auxPreg = regPatientPregnant(i);
                        }
                    }else{
                        auxGner = regPatientGner(i);
                        if (auxGner.equals("UNDO")){
                            auxID = regPatientID(i);
                            if (auxID.equals("UNDO")){
                                auxName = regPatientName(i);
                                if (auxName.equals("exit")){
                                    return null;
                                }
                                auxID = regPatientID(i);
                            }
                            auxGner = regPatientGner(i);
                        }
                    }
                    auxAge = regPatientAge(i);
                }
                auxIll = regPatientIll(i);
            }
        }

        nameOption = auxName;
        idOption = auxID;
        if (Integer.parseInt(auxGner)==1){
            gnerOption = "Women";
        }else{
            gnerOption = "Man";
        }

        if (Integer.parseInt(auxPreg)==1){
            pregnantOption = true;
        } else{
            pregnantOption = false;
        }

        ageOption = Integer.parseInt(auxAge);
        illOption = Integer.parseInt(auxIll);

        if (ageOption >= 65 && pregnantOption == true && illOption == 1) {
            priority = 6;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (ageOption >= 65 && pregnantOption == true && illOption == 2) {
            priority = 4;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (ageOption >= 65 && pregnantOption == false && illOption == 1) {
            priority = 3;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (ageOption <= 65 && pregnantOption == true && illOption == 1) {
            priority = 3;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (ageOption <= 65 && pregnantOption == false && illOption == 1) {
            priority = 1;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (pregnantOption == true && illOption == 1 && ageOption <= 65) {
            priority = 3;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else if (pregnantOption == false && illOption == 2 && ageOption <= 65) {
            priority = 1;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the main row.");
        } else if (pregnantOption == true && illOption == 2 && ageOption <= 65) {
            priority = 2;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the hematology row.");
        } else {
            priority = 0;
            JOptionPane.showMessageDialog(null,"The patient " + (i) + " is on the main row.");
        }

        return new Patient(idOption,nameOption,ageOption,illOption,priority,gnerOption,pregnantOption);
    }

    public static String regPatientName(int i) {
        String auxName = JOptionPane.showInputDialog("Patient number " + (i+1) + ". Name:");
        String nameOption;
        if (auxName.equals("UNDO")) {
            return "exit";
        } else {
            undo.push("regName");
            nameOption = auxName;
        }
        return nameOption;
    }

    public static String regPatientID(int i) {
        String auxID = JOptionPane.showInputDialog("The patient " + (i+1) + ". ID:");
        undo.push("regID");
        return auxID;
    }

    public static String regPatientGner(int i) {
        String auxGner = JOptionPane.showInputDialog("The patient " + (i+1) + " is: " + "\n 1.Woman" + "\n 2.Man");
        undo.push("regGner");
        return auxGner;
    }

    public static String regPatientPregnant(int i) {
        String auxPreg = JOptionPane.showInputDialog("Is the patient pregnant? " + "\n 1. Yes" + "\n 2. No");
        undo.push("regPreg");
        return auxPreg;
    }

    public static String regPatientAge(int i){
        String auxAge = JOptionPane.showInputDialog("Patient number " + (i+1) + ". Age:");
        undo.push("regAge");
        return auxAge;
    }

    public static String regPatientIll(int i){
        String auxIll = JOptionPane.showInputDialog("Does patient number " + (i+1) + ". preents any emergency diase?" + "\n 1.Yes \n 2.No");
        undo.push("regIll");
        return auxIll;
    }

    public static void saveDataBaseInJson(ArrayList<Patient> arr) {
        try {
            File file = new File("dataBase/patientData.json");
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataBaseFromJson(ArrayList<Patient> arr) {
        try {
            File file = new File("dataBase/patientData.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            Gson gson = new Gson();
            Patient[] data = gson.fromJson(json, Patient[].class);
            if (data != null) {
                for (Patient b : data) {
                    arr.add(b);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

