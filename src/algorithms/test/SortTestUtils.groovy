package algorithms.test

/**
 * Useful methods for testing sorts.
 */
class SortTestUtils {

    /**
     * Returns <numCases> test cases.
     */
    static getTestCases(int numCases, int numValuesPerCase, int maxKey) {

        def list = [];

        numCases.times { list << generateTestCase(numValuesPerCase, maxKey) }

        return list;
    }

    /**
     * Returns a test case: a large random int array and a sorted reference to test the sort against.
     * Sample output: [input: [8, 2, 4, 5, 2, 9, 0], reference: [0, 2, 2, 4, 5, 8, 9]]
     */
    static generateTestCase(int numValues, int maxKey) {
        int[] input = generateRandomData(numValues, maxKey);
        int[] referenceOutput = Arrays.copyOf(input, input.length);

        Arrays.sort(referenceOutput);
        [input: input, reference: referenceOutput];
    }

    /**
     * Generates an int array, of size numValues, filled with random values from 0 to maxKey.
     */
    static Integer[] generateRandomData(int numValues, int maxKey) {

        Integer[] data = new Integer[numValues];

        int i = 0;
        numValues.times {
            data[i++] = new Random().nextInt(maxKey);
        }

        return data;
    }
}
