package Structures;

public interface IPriorityQueue<T> {

    public void insert(int key, T element);

    public T maximun();

    public T extractMax();

    public void increaseKey(int newKey, T element);

}
