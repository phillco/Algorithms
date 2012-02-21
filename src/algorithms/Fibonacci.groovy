package algorithms

/**
 * Explores more dynamic programming examples in much the same fashion of RodCutting.
 * I didn't include bottom-up as it's trivial.
 */
class Fibonacci {

    static int[] cache;

    /**
     * Runs the "naive" method of fibonacci but uses memoization to achieve O(n).
     */
    static def fibMemoized(i) {

        // Return the cached version if it exists.
        if (cache[i] > Integer.MIN_VALUE)
            return cache[i];

        def result = fibMemoized(i - 1) + fibMemoized(i - 2)

        // Save the cached value.
        cache[i] = result;
        return result;
    }

    /**
     * Runs fibMemoized.
     */
    static def runFibMemoized(int i) {

        // runFibMemoized(0) cannot set cache[1] below...
        if (i == 0)
            return 0;

        // Prepare the cache.
        cache = new int[i + 1];
        Arrays.fill(cache, Integer.MIN_VALUE);
        cache[0] = 0;
        cache[1] = 1;

        return fibMemoized(i);
    }

    /**
     * Calculates fibonacci using tail-call optimized method (which is O(n) and would normally
     * take constant memory space).
     *
     * Except, of course, that Java (and therefore Groovy) does not do tail call optimization!
     * So this function uses a linear amount of memory.
     */
    static def fibTailOptimized(i, current = 0, next = 1) {
        if (i == 0)
            return current
        else
            return fibTailOptimized(i - 1, next, current + next)
    }

    /**
     * Tests all the algorithms.
     */
    static void test() {

        // rightAnswers[i] = fibonacci(i), predetermined.
        def rightAnswers = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765]

        rightAnswers.eachWithIndex { int answer, int i ->

            assert fibTailOptimized(i) == answer
            assert runFibMemoized(i) == answer
        }

        println "Success!"
    }

    static void main(args) { test() }
}
