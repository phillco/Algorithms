package algorithms

/**
 * Implements a byte-wise radix sort. A radix sort solves many of the problems of counting sort, while maintaining its O(n) speed.
 *
 * Radix sort makes k passes through the input, where k is the number of bytes in each value. In each pass, it
 * compares (and sorts by) the kth byte. Meanwhile, since it only sorts on one byte at a time, maxKey is 256.
 *
 * It sorts on the least significant bit first and moves outward. So, the last pass (which has the greatest influence on the output ordering)
 * is run on the most important bit. The second-most recent is on the second-most important bit, and so on. This sort relies on the fact that
 * it's stable.
 *
 * @see CountingSort
 * @see "Algorithms", Chapter 8-3, "Radix Sort"
 */
class RadixSort {

    /**
     * Sorts inputArray into outputArray, much like counting sort, but comparing each value's N-th (sortByte) byte.
     */
    static radix(int sortByte, int[] inputArray, int[] outputArray) {

        // Count number of values at this byte offset.
        int[] count = new int[256];
        for (int i = 0; i < inputArray.length; ++i)
            count[((inputArray[i]) >> (sortByte * 8)) & 0xff]++;

        // Create the array of final locations...
        int[] index = new int[256];
        for (int i = 1; i < 256; ++i)
            index[i] = index[i - 1] + count[i - 1];

        // Move the values to the right place.
        for (int i = 0; i < inputArray.length; ++i)
            outputArray[index[((inputArray[i]) >> (sortByte * 8)) & 0xff]++] = inputArray[i];
    }

    static sort(int[] input) {

        int[] temp = new int[input.length];

        // 4-byte integers.
        radix(0, input, temp);
        radix(1, temp, input);
        radix(2, input, temp);
        radix(3, temp, input);
    }

}
