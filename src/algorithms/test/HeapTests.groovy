package algorithms.test

import algorithms.Heap
import algorithms.HeapSort

/**
 * Tests heaps!
 */
class HeapTests extends GroovyTestCase {

    def testTurnIntoHeap() {

        def list = [0, 4, 8, 9, 2, 0, 9, 7, 2]

        Heap.turnIntoHeap(list);
        testMaxHeap(list);
    }
    
    def testHeapSort() {
        
        def list = [0, 4, 8, 9, 2, 0, 9, 7, 2]
        assert (HeapSort.heapSort(list) == [2, 2, 4, 7, 8, 9, 9]);
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