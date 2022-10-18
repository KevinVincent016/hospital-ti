package Structures;

public class Stack<T> implements IStack<T>{

    private NodeStack<T> top;

    public Stack() {
    }

    @Override
    public T top() {
        return top.getValue();
    }

    @Override
    public boolean isEmpty() {

        boolean flag = false;

        if (top == null) {
            flag = true;
        }

        return flag;
    }

    @Override
    public T pop() {
        NodeStack<T> newTop = top.getNext();
        T topValue = top.getValue();
        top.setNext(null);
        top = newTop;
        return topValue;
    }

    @Override
    public void push(T value) {
        NodeStack<T> newTop = new NodeStack<>(value);

        if (top == null) {
            top = newTop;
        } else {
            newTop.setNext(top);
            top = newTop;
        }
    }

}
