package Structures;

public class HashNode<K, V> {
    private V value;
    private K key;

    private HashNode<K,V> next;
    private HashNode<K,V> previous;

    public HashNode(K key, V value) {
        this.value = value;
        this. key = key;
        next = null;
        previous = null;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public HashNode<K, V> getNext() {
        return next;
    }

    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }

    public HashNode<K, V> getPrevious() {
        return previous;
    }

    public void setPrevious(HashNode<K, V> previous) {
        this.previous = previous;
    }
}
