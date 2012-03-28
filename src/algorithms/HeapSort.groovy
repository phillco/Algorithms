package algorithms

/**
 * Implements a heap sort using the Heap class.
 *
 * @see Heap
 * @see "Algorithms", Ch 6-4, "The Heapsort Algorithm"
 */
class HeapSort {

    /**
     * Sorts the given list in-place using a heapsort.
     * First it heapifies the array, in-place.
     * Then it turns it back into a list, by repeatedly moving the biggest elements to the back and re-heaping until the heap is gone.
     *
     * O(nlgn)
     */
    static def heapSort(int[] data) {
        Heap.HeapArray A = Heap.turnIntoHeap(data);

        for (int i: (A.data.length - 1..2)) {

            // Move the biggest element (A[1]) to the end.
            Util.swap(A.data, 1, i);
            A.heapSize--; // And out of the jurisdiction of the heap.

            // Fix any unheaping that occurred by moving A[i] to the front.
            Heap.heapify(A, 1);
        }
    }

}
