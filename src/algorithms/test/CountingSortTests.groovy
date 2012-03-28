package algorithms.test

import algorithms.CountingSort

/**
 * Tests counting sort and simplerSort().
 */
class CountingSortTests extends GroovyTestCase {

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


}
