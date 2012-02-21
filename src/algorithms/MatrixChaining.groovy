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
     *      p = matrixDimensions
     *      m = cost
     *      s = cutPoints
     */
    static def calculateChain(int[] matrixDimensions) {

        def cost = new int[matrixDimensions.size()][matrixDimensions.size()]
        def cutPoints = new int[matrixDimensions.size()][matrixDimensions.size()]

        for (int l: 2..matrixDimensions.size() - 1) {

            for (int i: 1..(matrixDimensions.length - l)) {

                int j = i + l - 1;
                cost[i][j] = Integer.MAX_VALUE;

                for (int k: i..(j - 1)) {
                    int potentialCost = cost[i][k] + cost[k + 1][j] + matrixDimensions[i - 1] * matrixDimensions[k] * matrixDimensions[j];

                    if (potentialCost < cost[i][j]) {
                        cost[i][j] = potentialCost;
                        cutPoints[i][j] = k;
                    }
                }
            }
        }

        return [cost: cost, cutPoints: cutPoints]
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

    /**
     * Tests all the algorithms.
     */
    static test() {

        int[] testDimensions = [30, 35, 15, 5, 10, 20, 25]

//        int[] testDimensions2 = [5, 10, 3, 12, 5, 50, 6]

        def results = calculateChain(testDimensions)

        println "COSTS for $testDimensions:"
        print2dArray(results.cost)

        println "\nCUT POINTS for $testDimensions:"
        print2dArray(results.cutPoints)
    }

    static void main(args) { test() }
}
