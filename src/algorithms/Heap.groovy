package algorithms

/**
 * Provides methods to turn an array(list) into a heap. This is not a object-oriented "Heap" class,
 * but a collection of algorithms as described in Ch 4.
 *
 * The heap property (dfn): every node is greater (minheap) or less than (maxheap) its parent.
 * Here we represent a maxheap (where the root is always the biggest element).
 *
 * IMPORTANT: The book uses 1-based indexing (A[1] is the root). For consistency, we use
 * the same notation -- this means that A[0] is allocated but not used.
 *
 * @see "Algorithms", Ch 6-1 to 6-3, "Heaps"
 */
class Heap {

    /**
     * Given the unsorted data, turns it into a heap.
     */
    static HeapArray turnIntoHeap(int[] data) {
        assert data[0] == 0 // Ensure 1-based numbering (see "important" above)

        HeapArray A = HeapArray.wrap(data); // Wrap the data so we can have the heapSize property.
        for (int i: (A.data.length / 2..1)) // Start at floor(max/2) and move left.
            heapify(A, i);

        return A;
    }

    /**
     * Makes A[i] a max-heap.
     *
     * Assumes that A[i]'s left and right children are already max-heaps but that A[i] may not be. (It could be smaller than its own children).
     * Fixes that by "bubbling down" the value in A[i] until it finds the right place.
     * Remember, everything above of A[i] is ignored.
     *
     * O(lg n)
     */
    static void heapify(HeapArray A, int i) {

        int largest = getLargestOfFamily(A, i);

        // If either child is larger than us, swap with them.
        if (largest != i) {
            Util.swap(A.data, i, largest);
            heapify(A, largest); // And keep swapping until the heap is fixed
        }
    }

    /**
     * Finds which is the biggest: this node, its left child, or its right child. Returns the index of the biggest.
     */
    static int getLargestOfFamily(HeapArray A, i) {

        int largest = i;

        // Is the left child bigger?
        if (hasLeft(A, i) && A.data[left(i)] > A.data[i])
            largest = left(i);

        // Is the right child bigger?
        if (hasRight(A, i) && A.data[right(i)] > A.data[largest])
            largest = right(i);

        return largest;
    }

    //==========================
    // Utility functions
    //==========================

    static boolean hasLeft(A, index) { return left(index) < A.heapSize }

    static boolean hasRight(A, index) { return right(index) < A.heapSize }

    static int parent(int i) { i / 2 } // Normally a >> shift

    static int left(int i) { 2 * i } // Normally a << shift

    static int right(int i) { left(i) + 1 }

    /**
     * A simple wrapper class so we can add the heapSize property to the array.
     */
    static class HeapArray {

        int[] data

        int heapSize

        static HeapArray wrap(int[] data) { new HeapArray(data: data, heapSize: data.length) }
    }
}
