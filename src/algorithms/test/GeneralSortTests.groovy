package algorithms.test

import algorithms.*

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

    def testMergeSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            MergeSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

    def testQuickSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            QuickSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

    def testRandomizedQuickSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            RandomizedQuickSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

    def testRadixSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            RadixSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

    def testSelectionSort() {
        SortTestUtils.getTestCases(20, 5000, 500).each { testCase ->

            SelectionSort.sort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }
}
