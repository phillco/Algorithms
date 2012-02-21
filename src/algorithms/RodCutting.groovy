package algorithms

/**
 * Demonstrates different algorithms for rod cutting.
 *
 * @see "Algorithms", Ch. 15.1, "Dynamic Programming"
 */
class RodCutting {

    // The price for a rod of length [index]. These never change.
    final static int[] prices = [0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30]

    // Memoize the value of subproblems here.
    static int[] bestPrices = new int[prices.size()]

    /**
     * Initializes (resets) static data.
     */
    static void init() {
        Arrays.fill(bestPrices, Integer.MIN_VALUE)
        bestPrices[0] = 0;
    }

    /**
     * Determines the best value you can get for a rod of length size.
     * Phenomenally stupid, O(2^n) algorithm.
     */
    static int cutRodDumb(int size) {

        if (size == 0)
            return 0;

        // Find the optimal cut point.
        int best = Integer.MIN_VALUE
        for (int cutIndex: 1..size)
            best = Math.max(best, prices[cutIndex] + cutRodDumb(size - cutIndex))

        return best;
    }

    /**
     * Determines the best value you can get for a rod of length size.
     * Uses memoization to store the result of smaller runs; O(n^2).
     */
    static int cutRodMemoized(int size) {

        if (bestPrices[size] >= 0)
            return bestPrices[size]

        // Find the optimal cut point.
        int best = Integer.MIN_VALUE
        for (int cutIndex: 1..size)
            best = Math.max(best, prices[cutIndex] + cutRodMemoized(size - cutIndex))

        bestPrices[size] = best;
        return best;
    }

    /**
     * Determines the best value you can get for a rod of length size.
     * Uses memoization in a bottom-up fashion: the values are calculated first from small to large
     *
     * Achieves O(n^2) but with a smaller constant (as there are no spurious recursive calls).
     * More efficient for problems with densely-packed sub-problems (like rod cutting, where value[i] needs all of the values[1..n]).
     * Less efficient for problems with sparsely-packed sub-problems (ie, the memoization table doesn't need every value from 1..n).
     */
    static int cutRodBottomUp(int size) {

        // Generate the table from 1 to <size>.
        for (int i: 1..size) {

            // Find the optimal cut point.
            int best = Integer.MIN_VALUE
            for (int cutIndex: 1..size)
                best = Math.max(best, prices[cutIndex] + bestPrices[size - cutIndex]) // Note: No recursion! We can safely access the array directly.

            bestPrices[size] = best;
        }

        return bestPrices[size]
    }

    /**
     * Tests all the algorithms.
     */
    static void test() {

        // These are the predetermined right answers.
        def rightAnswers = [1: 1, 2: 5, 3: 8, 4: 10, 5: 13, 6: 17, 8: 22, 9: 25, 10: 30]

        rightAnswers.each { int size, int price ->

            init();
            assert cutRodDumb(size) == price
            assert cutRodMemoized(size) == price
            assert cutRodBottomUp(size) == price
        }

        println "Success!"
    }

    static void main(args) { test() }
}


