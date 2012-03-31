package algorithms.test

import algorithms.CountingSort
import algorithms.InsertionSort

/**
 * Tests various sorts.
 */
class GeneralSortTests extends GroovyTestCase {

    /**
     * Tests counting sort and simplerSort().
     */
    def testCountingSort() {

        def testCases = SortTestUtils.getTestCases(20, 5000, 500);

        testCases.each { testCase ->

            def outputOne = new int[testCase.input.length];
            def outputTwo = new int[testCase.input.length];

            CountingSort.sort(testCase.input, outputOne, 500);
            CountingSort.simplerSort(testCase.input, outputTwo, 500);

            assert outputOne == testCase.reference;
            assert outputTwo == testCase.reference;
        }
    }

    def testInsertionSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            InsertionSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

}
