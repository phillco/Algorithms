package algorithms

/**
 * Implements a randomized QuickSort, which is still O(n^2) but has a better expected running time
 * and is harder to slow down by sending it bad data.
 *
 * @see "Algorithms", Ch 7-3, "A randomized version of QuickSort"
 */
class RandomizedQuickSort {

    static sort(int[] A) { sort(A, 0, A.length - 1) }

    static sort(int[] A, int start, int end) {
        if (start < end) {
            int pivot = partition(A, start, end);

            sort(A, start, pivot - 1)
            sort(A, pivot + 1, end)
        }
    }

    /**
     * Rearranges A into three parts, like QuickSort.partition, except
     * that the pivot is chosen randomly!
     */
    static int partition(int[] A, int start, int end) {

        int randomIndex = new Random().nextInt(end - start) + start;

        Util.swap(A, end, randomIndex);
        return QuickSort.partition(A, start, end);
    }
}
