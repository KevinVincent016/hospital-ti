package Model;
import Structures.HeapMax;

public class HematologyUnit {

    private HeapMax<Patient> HPrioQueue = new HeapMax<>();

    private ReceptionUnit receptionUnit = new ReceptionUnit();

    public HematologyUnit() {
    }

    public HeapMax<Patient> getHPrioQueue() {
        return HPrioQueue;
    }

    public void setHPrioQueue(HeapMax<Patient> HPrioQueue) {
        this.HPrioQueue = HPrioQueue;
    }

    public void addToQueue(Patient p) {
        boolean flag = false;
        for (int i = 0; i < receptionUnit.getDBSIZE(); i++) {
            if (receptionUnit.getDatabase().search(i) != null && receptionUnit.getDatabase().search(i).getCedula() == p.getCedula()) {
                HPrioQueue.insert(p.getPrioridad(), p);
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (!flag) {
            for (int i = 0; i < receptionUnit.getDBSIZE(); i++) {
                if (receptionUnit.getDatabase().search(i) == null) {
                    receptionUnit.getDatabase().insert(i, p);
                    HPrioQueue.insert(p.getPrioridad(), p);
                    break;
                }
            }
        }
    }

    public String removePacient() {
        String aux = "";
        if (HPrioQueue.isEmpty() == true) {
            aux = "There is no patient to remove";
        } else {
            receptionUnit.addToQueue(HPrioQueue.extractMax());
            aux = "Patient removed";
        }
        return aux;
    }

    public String printQueue() {
        if (getHPrioQueue().getSize()==0){
            return "There is no patient on the queue";
        }else {
            return
                    "Name: " + getHPrioQueue().maximun().getNombre() + "\n"
                            + "Age: " + getHPrioQueue().maximun().getEdad() + "\n"
                            + "ID: " + getHPrioQueue().maximun().getCedula() + "\n"
                            + "Level of Priority: " + getHPrioQueue().maximun().getPrioridad() + "\n"
                    ;
        }
    }
}
