package algorithms.test

import algorithms.Heap
import algorithms.HeapSort

/**
 * Tests heaps!
 */
class HeapTests extends GroovyTestCase {

    def testTurnIntoHeap() {

        def list = [0, 4, 8, 9, 2, 0, 9, 7, 2].toArray()

        Heap.HeapArray A = Heap.turnIntoHeap(list);
        testMaxHeap(A);
    }

    def testHeapSort() {

        def list = [0, 4, 8, 9, 2, 0, 9, 7, 2].toArray()
        HeapSort.heapSort(list)
        assert list == [0, 0, 2, 2, 4, 7, 8, 9, 9];
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
}