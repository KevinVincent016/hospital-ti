package Model;

import Structures.EQueue;
import Structures.HashTable;

import java.util.ArrayList;

public class ReceptionUnit {

    private HashTable<Integer, Patient> database;

    private final int DBSIZE = 10000000;

    private EQueue<Patient> reception = new EQueue<>();

    public ReceptionUnit(){
        database = new HashTable<>(DBSIZE);
    }

    public HashTable<Integer, Patient> getDatabase() {
        return database;
    }

    public void setDatabase(HashTable<Integer, Patient> database) {
        this.database = database;
    }

    public EQueue<Patient> getReception() {
        return reception;
    }

    public void setReception(EQueue<Patient> reception) {
        this.reception = reception;
    }

    public int getDBSIZE() {
        return DBSIZE;
    }

    public void addToQueue(Patient p){
        reception.enqueue(p);
    }

    public ArrayList<Patient> getArrayFromDB(){
        ArrayList<Patient> arr = new ArrayList<>();
        for (int i = 0; i < DBSIZE && database.search(i) != null; i++) {
            arr.add(database.search(i));
        }
        return arr;
    }

    public void printQueue() {
        if (reception.peek() == null) {
            System.out.println("There is no patient on queue");
            System.out.println(reception.size());
            System.out.println(reception.peek());
        } else{
            System.out.println("The first patient on queue is: ");
            System.out.println("Name: " + reception.peek().getNombre());
            System.out.println("Age: " + reception.peek().getEdad());
            System.out.println("ID: " + reception.peek().getCedula());
        }
    }

}
