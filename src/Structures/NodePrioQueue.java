package Structures;

public class NodePrioQueue<T> {

    int priorityValue;
    T value;


    public NodePrioQueue(T value, int priorityValue) {
        this.priorityValue = priorityValue;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getPriorityValue() {
        return priorityValue;
    }

    public void setPriorityValue(int priorityValue) {
        this.priorityValue = priorityValue;
    }

}
