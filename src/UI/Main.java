package UI;

import Model.GeneralPropuseUnit;
import Model.HematologyUnit;
import Model.Patient;
import Model.ReceptionUnit;
import com.google.gson.Gson;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner lector = new Scanner(System.in);
    static ReceptionUnit receptionUnit = new ReceptionUnit();
    static GeneralPropuseUnit generalPropuseUnit = new GeneralPropuseUnit();
    static HematologyUnit hematologyUnit = new HematologyUnit();

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
                    JOptionPane.showMessageDialog(null,"If you wish wish to undo type the command : UNDO");
                    Patient patient = registerPatients(index);

                    if (patient == null) {
                        break;
                    } else {
                        receptionUnit.getReception().enqueue(patient);
                        receptionUnit.getDatabase().insert(index, patient);
                        index++;

                        if (patient.getPrioridad() <= 0) {
                            generalPropuseUnit.addToQueue(patient);
                        } else {
                            hematologyUnit.addToQueue(patient);
                        }
                        break;
                    }

                case 2:
                    if (receptionUnit.getReception().isEmpty() != true) {
                        JOptionPane.showMessageDialog(null,"The person that is going to be removed is: ");
                        JOptionPane.showMessageDialog(null,
                                "Name: " + receptionUnit.getReception().peek().getNombre() + "\n" +
                                        "ID: " + receptionUnit.getReception().peek().getCedula() + "\n" +
                                        "Age: " + receptionUnit.getReception().peek().getEdad() + "\n");
                        receptionUnit.getReception().dequeue();
                    } else {
                        JOptionPane.showMessageDialog(null,"There is no people on the queue");
                    }
                    break;
                case 3:
                    int option;
                    do {
                        option = menuGeneralPropuse();

                        switch (option) {
                            case 1:
                                JOptionPane.showMessageDialog(null,"Patient " + generalPropuseUnit.getGPQueue().peek().getNombre()
                                        + " has been attended");
                                generalPropuseUnit.removeFromQueue();
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null,"you are now exiting General Unit...");
                                option = 2;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null,"ERROR !! THATS NOT AN OPTION");
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
                                JOptionPane.showMessageDialog(null,"Patient " + hematologyUnit.getHPrioQueue().maximun().getNombre()
                                        + " has been attended");
                                hematologyUnit.removePacient();
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null,"you are now exiting Hematology Unit...");
                                option2 = 2;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null,"ERROR !! THATS NOT AN OPTION");
                                break;
                        }

                    } while (option2 != 2);
                    break;
                case 5:
                    saveDataBaseInJson(receptionUnit.getArrayFromDB());
                    JOptionPane.showMessageDialog(null,"See you !!! Thank you!");

                    break;
                default:
                    JOptionPane.showMessageDialog(null,"¡¡¡ERROR THATS NOT AN OPTION!!!");
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
                    JOptionPane.showMessageDialog(null,"2 minutes have passed....");
                    int temp = (int) ( Math.random() * 2 + 1);;

                    if (temp == 1) {
                        if (hematologyUnit.getHPrioQueue().isEmpty() == false) {
                            JOptionPane.showMessageDialog(null,"The person that is going to be removed is: \n" + hematologyUnit.removePacient());
                        } else {
                            JOptionPane.showMessageDialog(null,"There is no people on the hematology queue");
                        }
                    }
                    if (temp == 2) {
                        if (generalPropuseUnit.getGPQueue().isEmpty() == false) {
                            JOptionPane.showMessageDialog(null,"The person that is going to be removed is: \n" + generalPropuseUnit.removeFromQueue());
                        } else {
                            JOptionPane.showMessageDialog(null,"There is no people on the general queue");
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static Patient registerPatients(int i) {
        Patient p = new Patient();

        int aux = 0;
        int auxx = 0;
        System.out.println("The patient " + (i + 1) + " is : \n1.Woman  \n2.Men   ");
        aux = lector.nextInt();

        if (aux == 001) {

        } else {
            if (aux == 1) {
                p.Sexo = "Women";
                System.out.println("¿Is the patient pregnant?\n1. Yes\n2. No  :");
                auxx = lector.nextInt();
                if (auxx == 1) {
                    p.Embarazada = true;
                } else
                    p.Embarazada = false;

            } else if (aux == 2) {
                p.Sexo = "Man";
            } else {
                System.out.println("Value n");
                System.out.println("The Patient " + (i + 1) + " es : \n1.Women  \n2. Man  :");
            }
            lector.nextLine();
        }

        // ID ASK
        System.out.println("Patient number " + (i + 1) + ". ID:");
        p.setCedula(lector.nextLine());

        if (p.getCedula().equals("001")) {
            System.out.println("The patient " + (i + 1) + " is : \n1.Woman  \n2.Men   :");
            aux = lector.nextInt();

            if (aux == 1) {
                p.Sexo = "Women";
                System.out.println("¿Is the patient pregnant?\n1. Yes\n2. No  :");
                auxx = lector.nextInt();
                if (auxx == 1) {
                    p.Embarazada = true;
                } else
                    p.Embarazada = false;

            } else if (aux == 2) {
                p.Sexo = "Man";
            }
            lector.nextLine();

            System.out.println("Patient number " + (i + 1) + ". ID:");
            p.setCedula(lector.nextLine());
        }

        // NAME ASK
        System.out.println("Patient number " + (i + 1) + ". Name:");
        p.setNombre(lector.nextLine());

        if (p.getNombre() == "001") {
            System.out.println("Patient number " + (i + 1) + ". ID:");
            p.setCedula(lector.nextLine());

            System.out.println("Patient number " + (i + 1) + ". Name:");
            p.setNombre(lector.nextLine());
        }

        // AGE ASK
        System.out.println("Patient number " + (i + 1) + ". Age:");
        p.setEdad(lector.nextInt());
        int temp2 = Integer.valueOf(p.getEdad());

        if (temp2 == 001) {
            System.out.println("Patient number " + (i + 1) + ". Name:");
            p.setNombre(lector.nextLine());

            System.out.println("Patient number " + (i + 1) + ". Age:");
            p.setEdad(lector.nextInt());
        }

        System.out.println("¿Does Patient number " + (i + 1) + ". presents any emergency diase? \n1. Yes \n2. No:");
        p.setEnfermedad(lector.nextInt());

        if (p.getEnfermedad() == 01) {
            System.out.println("Patient number " + (i + 1) + ". Age:");
            p.setEdad(lector.nextInt());
            System.out.println("¿Does Patient number " + (i + 1) + ". presents any emergency diase? \n1. Yes \n2. No:");
            p.setEnfermedad(lector.nextInt());
        }

        // EMBARAZADA +2 , ENFERMEDAD +1 , AÑOS +2

        if (p.getEdad() >= 65 && p.getEmbarazada() == true && p.getEnfermedad() == 1) {
            p.setPrioridad(6);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEdad() >= 65 && p.getEmbarazada() == true && p.getEnfermedad() == 2) {
            p.setPrioridad(4);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEdad() >= 65 && p.getEmbarazada() == false && p.getEnfermedad() == 1) {
            p.setPrioridad(3);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEdad() <= 65 && p.getEmbarazada() == true && p.getEnfermedad() == 1) {
            p.setPrioridad(3);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEdad() <= 65 && p.getEmbarazada() == false && p.getEnfermedad() == 1) {
            p.setPrioridad(1);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEmbarazada() == true && p.getEnfermedad() == 1 && p.getEdad() <= 65) {
            p.setPrioridad(3);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else if (p.getEmbarazada() == false && p.getEnfermedad() == 2 && p.getEdad() <= 65) {
            p.setPrioridad(1);
            System.out.println("The patient " + (i) + " is on the main row.");
        } else if (p.getEmbarazada() == true && p.getEnfermedad() == 2 && p.getEdad() <= 65) {
            p.setPrioridad(2);
            System.out.println("The patient " + (i) + " is on the hematology row.");
        } else {
            p.setPrioridad(0);
            System.out.println("The patient " + (i) + " is on the main row.");
        }
        return p;
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

