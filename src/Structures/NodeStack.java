package Structures;

public class NodeStack<T> {

    private T value;
    private NodeStack<T> next;

    public NodeStack(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodeStack<T> getNext() {
        return next;
    }

    public void setNext(NodeStack<T> next) {
        this.next = next;
    }

}
