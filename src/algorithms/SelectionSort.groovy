package algorithms

/**
 * A selection sort, which is a slow O(n^2) sort.
 *
 * This sort works by iterative through the list n times. For each pass,
 * it finds the smallest unexplored value and puts it in A[i].
 *
 * @see "Algorithms", Chapter 2-2, Exercise 2.2-2
 */
class SelectionSort {

    static sort(int[] data) {
        for (int i = 0; i < data.length; i++) {

            // Find the minimum value.
            int minIndex = i;
            for (int j = i+1; j < data.length; j++)
                if (data[j] < data[minIndex])
                    minIndex = j;

            // Move it to the front.
            Util.swap(data, i, minIndex);
        }
    }
}
