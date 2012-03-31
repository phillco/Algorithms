package algorithms

/**
 * Implements a QuickSort, which sorts in O(n^2) but O(n lgn) average.
 *
 * @see "Algorithms", Ch 7-1, "QuickSort"
 */
class QuickSort {

    static sort(int[] A) { sort(A, 0, A.length - 1) }

    static sort(int[] A, int start, int end) {
        if (start < end) {
            int pivot = partition(A, start, end);

            sort(A, start, pivot - 1)
            sort(A, pivot + 1, end)
        }
    }

    /**
     * Rearranges A into three parts:
     *  - Elements less than A[end]
     *  - A[end]
     *  - Elements greater than/equal to A[end]
     */
    static int partition(int[] A, int start, int end) {

        int value = A[end]

        int left = start - 1;
        for (int right = start; right < end; right++) {
            if (A[right] <= value) {
                left++;
                Util.swap(A, left, right)
            }
        }

        Util.swap(A, left + 1, end)
        return (left + 1);
    }

}
