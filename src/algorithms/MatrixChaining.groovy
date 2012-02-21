package algorithms

/**
 * Given a chain of matrices, determines their ideal parenthesization so as to minimize scalar multiplications.
 *
 * @see "Algorithms", Ch. 15.2, "Dynamic Programming"
 */
class MatrixChaining {

    /**
     * Given the chain of dimensions (ie [5, 10, 3] represents a 5x10 * 10x3) determine the ideal cutting order.
     * Returns [cost, cutPoints].
     *
     * Reference of the book's variable names:
     *      p = dimensions
     *      m = cost
     *      s = cutPoints
     *      k = splitPoint
     *      i = left
     *      j = right
     */
    static def calculateChain(int[] dimensions) {

        def cost = new int[dimensions.size()][dimensions.size()]
        def cutPoints = new int[dimensions.size()][dimensions.size()]

        for (int l = 2; l < dimensions.length - 1; l++) {

            for (int left = 1; left < dimensions.length - l; left++) {

                int right = left + l - 1;
                cost[left][right] = Integer.MAX_VALUE;

                // Test all possible split points.
                for (int splitPoint = left; splitPoint < right - 1; splitPoint++) {

                    // Test the potential cost for a split at index splitPoint.
                    // This is equal to the costs of the left and right side, PLUS the cost to combine the two. (Which is a multiplication itself.)
                    int potentialCost = cost[left][splitPoint] + cost[splitPoint + 1][right] + (dimensions[left - 1] * dimensions[splitPoint] * dimensions[right]);

                    // Is it better than the cost we already have?
                    if (potentialCost < cost[left][right]) {
                        cost[left][right] = potentialCost;
                        cutPoints[left][right] = splitPoint;
                    }
                }
            }
        }

        return [cost: cost, cutPoints: cutPoints]
    }

    static void printParentheses(cutPoints, i, j) {
        if (i == j)
            print "A${(i + 1)}"
        else {
            print "("
            printParentheses(cutPoints, i, cutPoints[i][j])
            printParentheses(cutPoints, cutPoints[i][j] + 1, j)
            print ")"
        }
    }

    /**
     * Nicely prints a 2D array.
     */
    static void print2dArray(array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++)
                System.out.print(String.format("%8s", array[i][j]));
            System.out.println();
        }
    }

    static void printResults(int[] dimensions) {

        println "Calculating optimal chain for $dimensions..."
        def results = calculateChain(dimensions)

        print "\nResults: "
        printParentheses(results.cutPoints, 0, dimensions.length - 1);

        println "\nCosts table (m):"
        print2dArray(results.cost)

        println "\nCut points table (s):"
        print2dArray(results.cutPoints)
    }

    /**
     * Tests all the algorithms.
     */
    static test() {

        int[] testDimensions = [30, 35, 15, 5, 10, 20, 25]
//        printResults(testDimensions)

        int[] testDimensions2 = [5, 10, 3, 12, 5, 50, 6]
        printResults(testDimensions2)
//
    }

    static void main(args) { test() }
}
