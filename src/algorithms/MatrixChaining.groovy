package algorithms

/**
 * Given a chain of matrices, determines their ideal parenthesization so as to minimize scalar multiplications.
 *
 * @see "Algorithms", Ch. 15.2, "Dynamic Programming"
 */
class MatrixChaining {


    static def calculateChain(int[] matrixDimensions) {

        /*
            p = matrixDimensions
            m = cost
            s = cutPoints
         */

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

        return [cost: cost, cutPoint: cutPoints]
    }

    static test() {

        int[] testDimensions = [30, 35, 15, 5, 10, 20, 25]

//        int[] testDimensions2 = [5, 10, 3, 12, 5, 50, 6]

        def results = calculateChain(testDimensions)

        println "COSTS for $testDimensions:"
        print2dArray(results.cost)

        println "\nCUT POINTS for $testDimensions:"
        print2dArray(results.cutPoint)
    }

    static void print2dArray(array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++)
                System.out.print(String.format("%8s", array[i][j]));
            System.out.println();
        }
    }

    static void main(args) { test() }
}
