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

    public void removePacient() {
        if (HPrioQueue.isEmpty() == true) {
            System.out.println("There is no patient to remove");
        } else {
            receptionUnit.addToQueue(HPrioQueue.extractMax());
        }
    }

    public void printQueue() {
        if (getHPrioQueue().getSize() == 0) {
            System.out.println("There is no patient on the queue");
        } else {
            System.out.println(
                    "Name: " + getHPrioQueue().maximun().getNombre() + "\n"
                            + "Age: " + getHPrioQueue().maximun().getEdad() + "\n"
                            + "ID: " + getHPrioQueue().maximun().getCedula() + "\n"
                            + "Level of Priority: " + getHPrioQueue().maximun().getPrioridad() + "\n"
            );
        }
    }
}
