package Structures;

import java.util.ArrayList;

public class HeapMax<T> implements IPriorityQueue<T> {

    private ArrayList<NodePrioQueue<T>> PQ;

    public HeapMax() {
        PQ = new ArrayList<>();
    }

    public void maxHeapify(int index) {

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        //Store the major priority object
        int max = index;

        if (left < PQ.size() && PQ.get(left).getPriorityValue() > PQ.get(index).getPriorityValue()) {
            max = left;
        }

        if (right < PQ.size() && PQ.get(right).getPriorityValue() > PQ.get(index).getPriorityValue()) {
            max = right;
        }

        //If max is equals to index is because the index already sort in the first position or root, otherwise
        //we do the exchange
        if (max != index) {
            NodePrioQueue tempMax = PQ.get(max);
            NodePrioQueue tempMin = PQ.get(index);

            PQ.set(max, tempMin);
            PQ.set(index, tempMax);

            //We make the method recursivity to ensure than the object with major priority ends in the first position or root
            maxHeapify(max);
        }
    }

    public void buildMaxHeap() {
        for (int i = PQ.size() / 2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    @Override
    public void insert(int key, T element) {
        PQ.add(new NodePrioQueue<T>(element, key));
        buildMaxHeap();
    }

    @Override
    public T maximun() {
        return PQ.get(0).getValue();
    }

    @Override
    public T extractMax() {
        if (PQ.size() < 1) {
            System.out.println("heap underflow");
        }
        T max = PQ.get(0).getValue();
        PQ.set(0, PQ.get(PQ.size() - 1));
        PQ.remove(PQ.size() - 1);
        maxHeapify(0);
        return max;
    }

    @Override
    public void increaseKey(int newKey, T element) {
        int index = -1;
        for (int i = 0; i < PQ.size(); i++) {
            if (PQ.get(i).getValue() == element) {
                index = i;
            }
        }

        if (newKey < PQ.get(index).getPriorityValue()) {
            System.out.println("New key is smaller than actual key");
        } else {
            PQ.get(index).setPriorityValue(newKey);
        }

        while (index > 0 && PQ.get(index / 2).getPriorityValue() < PQ.get(index).getPriorityValue()) {
            NodePrioQueue<T> temp1 = PQ.get(index);
            NodePrioQueue<T> temp2 = PQ.get(index / 2);

            PQ.set(index, temp2);
            PQ.set(index / 2, temp1);

            index = index / 2;
        }
    }

    public String printPQ() {
        String priorityQueue = "";
        for (int i = 0; i < PQ.size(); i++) {
            priorityQueue += PQ.get(i).getValue() + " " + PQ.get(i).getPriorityValue() + "  ";
        }
        return priorityQueue;
    }

    public int getSize(){
        return PQ.size();
    }

    public boolean isEmpty(){
        return PQ.isEmpty();
    }

}
