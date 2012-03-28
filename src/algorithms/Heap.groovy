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
     * Given the unsorted list A, turns it into a heap.
     */
    static void turnIntoHeap(List A) {
        assert A[0] == 0 // See "important" above -- just make sure nothing's in A[0]

        for (int i: (A.size() / 2..1)) // Start at floor(max/2) and move left.
            heapify(A, i);
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
    static void heapify(List A, int i) {

        int largest = getLargestOfFamily(A, i);

        // If either child is larger than us, swap with them.
        if (largest != i) {
            Util.swap(A, i, largest);
            heapify(A, largest); // And keep swapping until the heap is fixed
        }
    }

    /**
     * Finds which is the biggest: this node, its left child, or its right child. Returns the index of the biggest.
     */
    static int getLargestOfFamily(A, i) {

        int largest = i;

        // Is the left child bigger?
        if (hasLeft(A, i) && A[left(i)] > A[i])
            largest = left(i);

        // Is the right child bigger?
        if (hasRight(A, i) && A[right(i)] > A[largest])
            largest = right(i);

        return largest;
    }

    //==========================
    // Utility functions
    //==========================

    static boolean hasLeft(A, index) { return left(index) < A.size() }

    static boolean hasRight(A, index) { return right(index) < A.size() }

    static int parent(int i) { i / 2 } // Normally a >> shift

    static int left(int i) { 2 * i } // Normally a << shift

    static int right(int i) { left(i) + 1 }

}
