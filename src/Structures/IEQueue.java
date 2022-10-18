package Structures;

public interface IEQueue <T>{

    public T dequeue();

    public void enqueue(T value);

    public T peek();

    public int size();

    public boolean isEmpty();

}
