package Model;

import Structures.EQueue;

public class GeneralPropuseUnit {
    private EQueue<Patient> GPQueue = new EQueue<>();

    private ReceptionUnit receptionUnit = new ReceptionUnit();

    public GeneralPropuseUnit() {
    }

    public EQueue<Patient> getGPQueue() {
        return GPQueue;
    }

    public void setGPQueue(EQueue<Patient> GPQueue) {
        this.GPQueue = GPQueue;
    }

    public void addToQueue(Patient p) {
        boolean flag = false;
        for (int i = 0; i < receptionUnit.getDBSIZE(); i++) {
            if (receptionUnit.getDatabase().search(i) != null && receptionUnit.getDatabase().search(i).getCedula() == p.getCedula()){
                GPQueue.enqueue(p);
                flag = true;
                break;
            }else{
                flag = false;
            }
        }
        if (!flag){
            for (int i = 0; i < receptionUnit.getDBSIZE(); i++) {
                if (receptionUnit.getDatabase().search(i) == null){
                    receptionUnit.getDatabase().insert(i,p);
                    GPQueue.enqueue(p);
                    break;
                }
            }
        }
    }

    public String removeFromQueue() {
        String aux = "";
        if (GPQueue.isEmpty()==true){
            aux = "There is no patient to remove";
        }else{
            receptionUnit.addToQueue(GPQueue.dequeue());
            aux = "Patient removed";
        }
        return aux;
    }

    public String printQueue() {
        if (getGPQueue().size() == 0) {
            return "There are no patients on the queue";
        } else {
            return
                    "Name: " + GPQueue.peek().getNombre() + "\n"
                            + "Age: " + GPQueue.peek().getEdad() + "\n"
                            + "ID: " + GPQueue.peek().getCedula() + "\n"
                            + "Pregnant: " + GPQueue.peek().getEmbarazada() + "\n"
                    ;
        }
    }
}