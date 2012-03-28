package algorithms

/**
 * Implements a counting sort.
 *
 * Counting sort lets us sort in O(n) (rare!) instead of O(nlgn) because it's not a comparison sort;
 * it never actually compares the values in the input to each other, it simply aggregates them by type.
 *
 * Sadly, it doesn't perform well for data with a wide range of possible values because of all the space it'd need
 * to count each type. [It uses O(k) extra memory where k is the number of possible values.] It does well
 * for sorting a large amount of data spread over a few distinct keys. (Eg, sorting students by classification.)
 *
 * Note, this is usually more useful if the keys have extra data attached to them. (See simplerSort below)
 *
 * @see "Algorithms", Chapter 8-2, "Sorting in Linear Time"
 */
class CountingSort {

    /**
     * Sorts the data in inputArray into outputArray in O(n + maxKey) time and O(maxKey) memory.
     *
     * @param maxKey The largest value expected in inputArray.
     */
    static int[] sort(int[] inputArray, int[] outputArray, maxKey) {

        int[] countArray = new int[maxKey + 1]; // Include 0

        // First, count the number of occurrences of each number. So countArray[i] will be the number of times that "i" occurs.
        inputArray.each { value ->
            countArray[value]++;
        }

        // Next, modify countArray so that countArray[i] stores the number of times values LESS than "i" occur.
        // (Which equals the place to put "i" in the sorted output array)
        int total = 0;
        countArray.eachWithIndex { value, i ->

            countArray[i] = total;
            total += value;
        }

        // Finally, go through the input array again, and put all the values in the right place in output array.
        inputArray.each { value ->
            outputArray[countArray[value]] = value;
            countArray[value]++; // Place the next occurrence of this number one index over.
        }
    }

    /**
     * An even simpler (but unstable) version that works when there's no extra data attached to the integers.
     *
     * Instead of rearranging the original input into the output, we just count the number of occurrences of each
     * type of value and then insert that number of values into the output array.
     */
    static int[] simplerSort(int[] inputArray, int[] outputArray, maxKey) {

        int[] countArray = new int[maxKey + 1]; // Include 0

        // First, count the number of occurrences of each number. So countArray[i] will be the number of times that "i" occurs.
        inputArray.each { value ->
            countArray[value]++;
        }

        // Fill the output with the values, in order.
        int outputPosition = 0;
        countArray.eachWithIndex { count, n ->

            // Instead the value "n" count times. (count is countArray[n])
            count.times {
                outputArray[outputPosition++] = n;
            }
        }
    }

}
