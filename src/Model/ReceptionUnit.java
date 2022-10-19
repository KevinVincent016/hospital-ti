package Model;

import Structures.EQueue;
import Structures.HashTable;

import java.util.ArrayList;

public class ReceptionUnit {

    private HashTable<Integer, Patient> database;

    private final int DBSIZE = 10000000;

    private EQueue<Patient> reception;

    public ReceptionUnit(){
        database = new HashTable<>(DBSIZE);
        reception = new EQueue<>();
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

}
