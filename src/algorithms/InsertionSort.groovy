package algorithms

/**
 * Implements a simple insertion sort.
 *
 * @see "Algorithms", Chapter 2-1, "Insertion Sort"
 */
class InsertionSort {

    /**
     * Sorts the array A in-place in O(n^2) time.
     * @param A
     */
    static def sort(int[] A) {

        for (int j = 1; j < A.length; j++) {

            int key = A[j];

            // Insert A[j] into the sorted sequence A[1..j-1].
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i--;
            }

            A[i + 1] = key
        }

    }
}
