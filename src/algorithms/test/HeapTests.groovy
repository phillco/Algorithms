package algorithms.test

import algorithms.Heap
import algorithms.HeapSort

/**
 * Tests heaps!
 */
class HeapTests extends GroovyTestCase {

    def testTurnIntoHeap() {

        getTestCases(20).each { testCase ->

            testMaxHeap(Heap.turnIntoHeap(testCase.input));
        }
    }

    def testHeapSort() {

        getTestCases(20).each { testCase ->

            HeapSort.heapSort(testCase.input)
            assert testCase.input == testCase.reference;
        }
    }

    def testMaxHeap(Heap.HeapArray A, int i = 1) {

        if (Heap.hasLeft(A, i)) {
            assert A.data[i] >= A.data[Heap.left(i)]
            testMaxHeap(A, Heap.left(i))
        }

        if (Heap.hasRight(A, i)) {
            assert A.data[i] >= A.data[Heap.right(i)]
            testMaxHeap(A, Heap.right(i))
        }
    }

    def getTestCases(int num) {
        def testCases = SortTestUtils.getTestCases(20, 5000, 500);

        testCases.each { testCase ->

            // Ugh: Force 1-based numbering and resort
            testCase.input[0] = 0;
            testCase.reference = Arrays.copyOf(testCase.input, testCase.input.length);
            Arrays.sort(testCase.reference);
        }

        return testCases
    }
}