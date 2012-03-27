package algorithms.test

import algorithms.Heap

/**
 * Tests heaps!
 */
class HeapTests extends GroovyTestCase {

    void testTurnIntoHeap() {

        def list = [0, 4, 8, 9, 2, 0, 9, 7, 2]

        Heap.turnIntoHeap(list);
        testMaxHeap(list);
    }

    def testMaxHeap(A, int i = 1) {

        if (Heap.hasLeft(A, i)) {
            assert A[i] >= A[Heap.left(i)]
            testMaxHeap(A, Heap.left(i))
        }

        if (Heap.hasRight(A, i)) {
            assert A[i] >= A[Heap.right(i)]
            testMaxHeap(A, Heap.right(i))
        }
    }
}