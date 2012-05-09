package datastructures

/**
 * A doubly linked list with generics.
 */
class LinkedList<T> {

    /**
     * A node in the list.
     */
    class Node<T> {

        T value;
        Node<T> next
        Node<T> previous;
    }

    Node<T> head, tail;
    int size = 0;

    /**
     * Adds the given node to the head of the list.
     */
    public synchronized void addToHead(T object) {
        Node<T> node = new Node<T>(value: object, next: head);
        head?.previous = node;
        head = node;

        size++;
        if (!tail)
            tail = head;
    }

    /**
     * Adds the given node to the tail of the list.
     */
    public synchronized void addToTail(T object) {
        Node<T> node = new Node<T>(value: object, previous: tail);
        tail?.next = node;
        tail = node;

        size++;
        if (!head)
            head = tail;
    }

    /**
     * Finds the smallest element in the list using (implicit) compareTo.
     */
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

    /**
     * Returns if the given value exists in the list.
     */
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

    /**
     * Returns the size of the list.
     */
    public int size() {
        return size;
    }
}
