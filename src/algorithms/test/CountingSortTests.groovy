package algorithms.test

import algorithms.CountingSort

/**
 * Tests counting sort.
 */
class CountingSortTests extends GroovyTestCase {

    def testCountingSort() {

        def data = [4, 8, 9, 2, 0, 9, 7, 2]
        
        def input = new int[data.size()];
        def output = new int[data.size()];

        data.eachWithIndex { val, i -> input[i] = val }
        
        CountingSort.sort(input, output, data.max());
        
        assert input == [4, 8, 9, 2, 0, 9, 7, 2]
        assert output == [0, 2, 2, 4, 7, 8, 9, 9];
    }


}
