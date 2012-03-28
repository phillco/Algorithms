package algorithms

/**
 * Implements a heap sort using the Heap class.
 *
 * @see "Algorithms", Ch 6-4, "The Heapsort Algorithm"
 */
class HeapSort {

    /**
     * Returns a sorted version of the given list.
     */
    static def heapSort(List data) {
        Heap.turnIntoHeap(data);

        // This is junk: we have to track the results separately because we do not have a separate A.heap-size property.
        LinkedList sorted = []
        for (int i: (data.size()-1..2)) {

            sorted.addFirst(data[1]);
            Util.swap(data, 1, i);
            data.remove(data.size() - 1)
            Heap.heapify(data, 1);
        }

        return sorted;
    }

}
