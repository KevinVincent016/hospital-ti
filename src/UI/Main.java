package UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner lector = new Scanner(System.in);
    static ReceptionUnit receptionUnit = new ReceptionUnit();
    static GeneralPropuseUnit generalPropuseUnit = new GeneralPropuseUnit();
    static HematologyUnit hematologyUnit = new HematologyUnit();

    public static void main(String[] args) {

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
                    Patient patient = registerPatients(index);
                    receptionUnit.getReception().enqueue(patient);
                    receptionUnit.getDatabase().insert(index, patient);
                    index++;

                    if (patient.getPrioridad() <= 1) {
                        generalPropuseUnit.addToQueue(patient);
                    } else {
                        hematologyUnit.addToQueue(patient);
                    }
                    break;
                case 2:
                    if (receptionUnit.getReception().isEmpty() != true) {
                        System.out.println("The person that is going to be removed is: ");
                        System.out.println(
                                "Name: " + receptionUnit.getReception().peek().getNombre() + "\n" +
                                        "ID: " + receptionUnit.getReception().peek().getCedula() + "\n" +
                                        "Age: " + receptionUnit.getReception().peek().getEdad() + "\n");
                        receptionUnit.getReception().dequeue();
                    } else {
                        System.out.println("There is no people on the queue");
                    }
                    break;
                case 3:
                    int option;
                    do {
                        option = menuGeneralPropuse();

                        switch (option) {
                            case 1:
                                System.out.println("Patient " + generalPropuseUnit.getGPQueue().peek().getNombre()
                                        + " has been attended");
                                generalPropuseUnit.removeFromQueue();
                                break;
                            case 2:
                                System.out.println("you are now exiting General Unit...");
                                option = 2;
                                break;
                            default:
                                System.out.println("ERROR !! THATS NOT AN OPTION");
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
                                System.out.println("Patient " + hematologyUnit.getHPrioQueue().maximun().getNombre()
                                        + " has been attended");
                                hematologyUnit.removePacient();
                                break;
                            case 2:
                                System.out.println("you are now exiting Hematology Unit...");
                                option2 = 2;
                                break;
                            default:
                                System.out.println("ERROR !! THATS NOT AN OPTION");
                                break;
                        }

                    } while (option2 != 2);
                    break;
                case 5:
                    saveDataBaseInJson(receptionUnit.getArrayFromDB());
                    System.out.println("See you !!! Thank you!");
                    break;
                default:
                    System.out.println("¡¡¡ERROR THATS NOT AN OPTION!!!");
                    break;
            }
        } while (opcion != 5);

    }

    public static int menu() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Welcome to the EPS \nMenu:");
        System.out.println("1. Put a patient on the reception queue.");
        System.out.println("2. Remove a patient from the reception queue.");
        System.out.println("3. Enter to General Atention ");
        System.out.println("4. Enter to Hematology ");
        System.out.println("5. Exit");
        int opcion = lector.nextInt();
        return opcion;
    }

    public static int menuGeneralPropuse() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Welcome to General Unit");
        System.out.println("The first person in the queue is: ");
        generalPropuseUnit.printQueue();
        System.out.println("Menu: ");
        System.out.println("1. Attend the patient ");
        System.out.println("2. Return to main menu");
        int option = lector.nextInt();
        return option;
    }

    public static int menuHematologyUnit() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Welcome to Hematology Unit");
        System.out.println("The first person in the queue is: ");
        hematologyUnit.printQueue();
        System.out.println("Menu: ");
        System.out.println("1. Attend the patient ");
        System.out.println("2. Return to main menu");
        int option = lector.nextInt();
        return option;
    }

    public static Patient registerPatients(int i) {
        Patient p = new Patient();

        int aux = 0;
        int auxx = 0;
        System.out.println("The patient " + (i + 1) + " is : \n1.Woman  \n2.Men  :");
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
        } else {
            System.out.println("Value n");
            System.out.println("The Patient " + (i + 1) + " es : \n1.Women  \n2. Man  :");
        }
        lector.nextLine();
        System.out.println("Patient number " + (i + 1) + ". ID:");
        p.setCedula(lector.nextLine());
        System.out.println("Patient number " + (i + 1) + ". Name:");
        p.setNombre(lector.nextLine());
        System.out.println("Patient number " + (i + 1) + ". Age:");
        p.setEdad(lector.nextInt());

        System.out.println("¿Does Patient number " + (i + 1) + ". presents any emergency diase? \n1. Yes \n2. No:");
        p.setEnfermedad(lector.nextInt());

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

}
