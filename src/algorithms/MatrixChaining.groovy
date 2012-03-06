package algorithms

/**
 * Given a chain of matrices, determines their ideal parenthesization so as to minimize scalar multiplications.
 *
 * @see "Algorithms", Ch. 15.2, "Dynamic Programming"
 */
class MatrixChaining {

    /**
     * Determines the ideal cutting order for the given chain of dimensions (ie [5, 10, 3, 7] represents a [5x10] * [10x3] * [3x7] multiplication.)
     * Returns [cost, cutPoints].
     *
     * Reference of the book's variable names:
     *      p = dimensions
     *      m = cost
     *      s = cutPoints
     *      k = splitPoint
     *      i = left
     *      j = right
     *      l = chainSize
     */
    static def calculateChain(int[] dimensions) {

        def cost = new int[dimensions.size()][dimensions.size()]
        def cutPoints = new int[dimensions.size()][dimensions.size()]
        def accessCount = new int[dimensions.size()][dimensions.size()]

        // Test all possible chain sizes from 2 to the size of all the dimensions.
        for (int chainSize = 2; chainSize <= dimensions.length - 1; chainSize++) {

            for (int left = 1; left <= dimensions.length - chainSize; left++) {

                int right = left + chainSize - 1;
                cost[left][right] = Integer.MAX_VALUE;
                accessCount[left][right]++;

                // Test all possible split points.
                for (int splitPoint = left; splitPoint < right; splitPoint++) {

                    // What's the potential cost for this split?
                    // This is equal to the costs of the left and right side, PLUS the cost to combine the two. (Which will be a multiplication itself.)
                    int potentialCost = cost[left][splitPoint] + cost[splitPoint + 1][right] + (dimensions[left - 1] * dimensions[splitPoint] * dimensions[right]);

                    accessCount[left][splitPoint]++;
                    accessCount[splitPoint + 1][right]++;

                    // Is it better than the cost we already have?
                    accessCount[left][right]++;
                    if (potentialCost < cost[left][right]) {
                        cost[left][right] = potentialCost;
                        accessCount[left][right]++;
                        cutPoints[left][right] = splitPoint;
                    }
                }
            }
        }

        return [cost: cost, cutPoints: cutPoints, accessCount: accessCount]
    }

    /**
     * Given the array of cut points and the starting and ending points, prints their parenthesization.
     */
    static void printParentheses(cutPoints, left, right) {
        if (left == right)
            print "A${(left)}"
        else {
            print "("
            printParentheses(cutPoints, left, cutPoints[left][right])
            printParentheses(cutPoints, cutPoints[left][right] + 1, right)
            print ")"
        }
    }

    /**
     * Nicely prints a 2D array.
     */
    static void print2dArray(array, int spacing) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
//                if (array[i][j])
                    System.out.print(String.format("%${spacing}s", array[i][j]));
//                else
//                    System.out.print(String.format("%${spacing}s", " "));
            }
            System.out.println();
        }
    }

    /**
     * Simulates the multiplication of the list of the matrices given, using the cutPoints given.
     * Note that this is psuedocode.
     */
    static def matrixChainMultiply(matrices, cutPoints, left, right) {

        if (left == right)
            return matrices[left]

        def leftSide = matrixChainMultiply(matrices, cutPoints, left, cutPoints[left][right])
        def rightSide = matrixChainMultiply(matrices, cutPoints, cutPoints[left][right] + 1, right)

        return (leftSide * rightSide)
    }

    /**
     * Just calculate & print details about the given matrix chain.
     */
    static void printResults(int[] dimensions) {

        println "Calculating optimal chain for $dimensions..."
        def results = calculateChain(dimensions)

        print "\nResults: "
        printParentheses(results.cutPoints, 1, dimensions.length - 1);

        println "\nCosts table (m):"
        print2dArray(results.cost, 8)

        println "\nCut points table (s):"
        print2dArray(results.cutPoints, 8)

        println "\nAccess counts:"
        print2dArray(results.accessCount, 8)

        // Test the multiplication.
     //   matrixChainMultiply(dimensions, results.cutPoints, 1, dimensions.length);
    }

    /**
     * Demonstrates all the algorithms.
     */
    static demonstrate() {

        int[] testDimensions = [30, 35, 15, 5, 10, 20, 25]
        printResults(testDimensions)

        int[] testDimensions2 = [5, 10, 3, 12, 5, 50, 6]
        printResults(testDimensions2)
    }

    static void main(args) { demonstrate() }
}