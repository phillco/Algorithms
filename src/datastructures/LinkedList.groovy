package datastructures

/**
 * Created by IntelliJ IDEA.
 * User: Phillip
 * Date: 5/8/12
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
class LinkedList<T> {

    class Node<T> {

        T value;
        Node<T> next
        Node<T> previous;
    }

    Node<T> head, tail;
    int size = 0;

    public synchronized void addToHead(T object) {
        Node<T> node = new Node<T>(value: object, next: head);
        head?.previous = node;
        head = node;

        size++;
        if (!tail)
            tail = head;
    }

    public synchronized void addToTail(T object) {
        Node<T> node = new Node<T>(value: object, previous: tail);
        tail?.next = node;
        tail = node;

        size++;
        if (!head)
            head = tail;
    }

    public T min() {
        if (!head)
            return null;

        T min = head.value
        Node<T> node = head;
        while (node) {
            if (node.value < min)
                min = node.value
            node = node.next;
        }

        return min;
    }

    public boolean search(T value) {
        if (!head)
            return false;

        Node<T> node = head;
        while (node) {
            if (node.value == value)
                return true;

            node = node.next;
        }

        return false;
    }

    public int size() {
        return size;
    }
}
