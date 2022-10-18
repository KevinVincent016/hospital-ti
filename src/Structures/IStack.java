package Structures;

public interface IStack<T> {

    public T top();

    public boolean isEmpty();

    public T pop();

    public void push(T value);

}
