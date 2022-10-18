package Structures;

import java.util.ArrayList;

public class EQueue <T> implements IEQueue<T>{

    private ArrayList<T> queue;

    public EQueue(){
        queue = new ArrayList<>();
    }

    @Override
    public T dequeue() {
        if (isEmpty()){
            return null;
        }else {
            T firstPlace = queue.get(0);
            queue.remove(0);
            return firstPlace;
        }
    }

    @Override
    public void enqueue(T value) {
        queue.add(value);
    }

    @Override
    public T peek() {
        if (isEmpty()){
            return null;
        }else {
            return queue.get(0);
        }
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

