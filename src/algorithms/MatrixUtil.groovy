package algorithms

class MatrixUtil {

/**
 * Adds the two matrices.
 */
    def addMatrices(one, two) {

        one = engulfInMatrix(one)
        two = engulfInMatrix(two)

        def returnMatrix = new int[one.size()][two.size()]

        for (int i = 0; i < one.size(); i++)
            for (int j = 0; j < one[0].size(); j++)
                returnMatrix[i][j] = one[i][j] + two[i][j]

        return returnMatrix;
    }

/**
 * Subtracts the two matrices.
 */
    def subtractMatrices(one, two) {
        return addMatrices(one, scalarMultiply(two, -1))
    }

/**
 * Multiplies every value in the matrix by the given scalar.
 * Not a normal matrix function; this is mostly used as a helper method.
 */
    def scalarMultiply(matrix, scalar) {

        matrix = engulfInMatrix(matrix)

        def returnMatrix = new int[matrix.size()][matrix[0].size()];

        for (int i = 0; i < matrix.size(); i++)
            for (int j = 0; j < matrix[0].size(); j++)
                returnMatrix[i][j] = matrix[i][j] * scalar;

        return returnMatrix;
    }

/**
 * Multiplies the two given square matrices naively (O(n^3), simply recurse down.)
 */
    def naiveMultiplyMatrices(one, two) {

        // The matrix must be square.
        assert one.size() == one[0].size() && two.size() == two[0].size() && one.size() == two.size()

        if (one.size() == 1)
            return one[0][0] * two[0][0]
        else {
            def splitA = splitMatrix(one)
            def splitB = splitMatrix(two)

            def returnMatrix = [topLeft: addMatrices(naiveMultiplyMatrices(splitA.topLeft, splitB.topLeft),
                    naiveMultiplyMatrices(splitA.topRight, splitB.bottomLeft)),

                    topRight: addMatrices(naiveMultiplyMatrices(splitA.topLeft, splitB.topRight),
                            naiveMultiplyMatrices(splitA.topRight, splitB.bottomRight)),

                    bottomLeft: addMatrices(naiveMultiplyMatrices(splitA.bottomLeft, splitB.topLeft),
                            naiveMultiplyMatrices(splitA.bottomRight, splitB.bottomLeft)),

                    bottomRight: addMatrices(naiveMultiplyMatrices(splitA.bottomLeft, splitB.topRight),
                            naiveMultiplyMatrices(splitA.bottomRight, splitB.bottomRight))]

            return combineMatrix(returnMatrix);
        }
    }

/**
 * Multiplies the two matrices using Strassen's method, which is O(n^2.81).
 */
    int[][] intelligentlyMultiplyMatrices(one, two) {

        if (one.size() == 1)
            return one[0][0] * two[0][0];

        def a = splitMatrix(one)
        def b = splitMatrix(two)

        // Define the "S" matrix.
        def s1 = subtractMatrices(b.topRight, b.bottomRight)
        def s2 = addMatrices(a.topLeft, a.topRight)
        def s3 = addMatrices(a.bottomLeft, a.bottomRight)
        def s4 = subtractMatrices(b.bottomLeft, b.topLeft)
        def s5 = addMatrices(a.topLeft, a.bottomRight)
        def s6 = addMatrices(b.topLeft, b.bottomRight)
        def s7 = subtractMatrices(a.topRight, a.bottomRight)
        def s8 = addMatrices(b.bottomLeft, b.bottomRight)
        def s9 = subtractMatrices(a.topLeft, a.bottomLeft)
        def s10 = addMatrices(b.topLeft, b.topRight)

        // Define the "P" matrix.
        int[][] p1 = intelligentlyMultiplyMatrices(a.topLeft, s1)
        int[][] p2 = intelligentlyMultiplyMatrices(s2, b.bottomRight)
        int[][] p3 = intelligentlyMultiplyMatrices(s3, b.topLeft)
        int[][] p4 = intelligentlyMultiplyMatrices(a.bottomRight, s4)
        int[][] p5 = intelligentlyMultiplyMatrices(s5, s6)
        int[][] p6 = intelligentlyMultiplyMatrices(s7, s8)
        int[][] p7 = intelligentlyMultiplyMatrices(s9, s10)

        // Fill in the resultant "C" matrix.
        def c11 = addMatrices(subtractMatrices(addMatrices(p5, p4), p2), p6)
        def c12 = addMatrices(p1, p2)
        def c21 = addMatrices(p3, p4)
        def c22 = subtractMatrices(subtractMatrices(addMatrices(p5, p1), p3), p7)

        return (int[][]) combineMatrix(c11, c12, c21, c22);
    }

/**
 * Splits the given matrix into four sub-matrices, one for each quadrant.
 * The new matrices are returned in a map with keys topLeft, topRight, bottomLeft, and bottomRight.
 */
    def splitMatrix(matrix) {

        // Ensure the matrix's size is divisible by n^2.
        int matrixSize = (int) matrix.size()
        assert (int) (Math.log(matrixSize) / Math.log(2)) == (Math.log(matrixSize) / Math.log(2))
        assert (int) (Math.log(matrixSize) / Math.log(2)) == (Math.log(matrixSize) / Math.log(2))

        // Result is a map with four sub-matrices.
        def result_matrices = [:]

        // Transpose the values in the quadrants.
        int halfSize = (int) (matrix.size() / 2)
        result_matrices.topLeft = selectSubset(matrix, 0, 0, halfSize, halfSize);
        result_matrices.topRight = selectSubset(matrix, 0, halfSize, halfSize, halfSize);
        result_matrices.bottomLeft = selectSubset(matrix, halfSize, 0, halfSize, halfSize)
        result_matrices.bottomRight = selectSubset(matrix, halfSize, halfSize, halfSize, halfSize)

        return result_matrices;
    }

/**
 * Does the opposite of splitMatrix. Combines the four given parts into one big matrix.
 */
    int[][] combineMatrix(topLeft, topRight, bottomLeft, bottomRight) {

        if (topLeft instanceof Integer)
            return [[topLeft, topRight], [bottomLeft, bottomRight]] as int[][];

        int[][] returnMatrix = new int[topLeft.size() + bottomLeft.size()][topLeft[0].size() + topRight[0].size()]

        fillInSubset(returnMatrix, 0, 0, topLeft);
        fillInSubset(returnMatrix, (int) topLeft.size(), 0, bottomLeft);
        fillInSubset(returnMatrix, 0, (int) topLeft[0].size(), topRight);
        fillInSubset(returnMatrix, (int) topLeft.size(), (int) topLeft[0].size(), bottomRight);

        return returnMatrix;
    }

/**
 * Does the opposite of splitMatrix. Combines the four given parts into one big matrix.
 */
    def combineMatrix(map) { combineMatrix(map.topLeft, map.topRight, map.bottomLeft, map.bottomRight) }

/**
 * Selects a square subset of the given matrix, returned as a new, smaller matrix.
 * The new matrix has size [numRows][numColumns] and is selected from the offset [row][column].
 */
    def selectSubset(matrix, int row, int column, int numRows, int numColumns) {

        def returnMatrix = new int[numRows][numColumns];

        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                returnMatrix[i][j] = matrix[i + row][j + column];

        return returnMatrix;
    }

/**
 * Puts the given subset into the given matrix at position (row, column).
 */
    def fillInSubset(matrix, int row, int column, subset) {

        for (int i = 0; i < subset.size(); i++)
            for (int j = 0; j < subset[0].size(); j++)
                matrix[i + row][j + column] = subset[i][j];
    }

/**
 * Ensures the given item is part of a 2D matrix. If it's merely an integer (a result of some base case operations), it's surrounded into a 1x1 matrix.
 * This is useful for simplifying many matrix operation functions.
 */
    def engulfInMatrix(argument) {
        if (argument instanceof Integer)
            return [[argument]];
        else if (argument.size() == 1 && argument[0] instanceof Integer)
            return [[argument[0]]]
        else
            return argument;
    }

//==============
// TESTING
//==============

    def test() {

        final def matrixOne = [[1, 3], [7, 5]]
        final def matrixTwo = [[1, 3, 9, 3], [7, 5, 9, 1], [9, 3, -8, 4], [8, -2, -4, 6]]

        // Test splitting...
        assert splitMatrix(matrixOne) == [topLeft: [[1]], topRight: [[3]], bottomLeft: [[7]], bottomRight: [[5]]]
        assert splitMatrix(matrixTwo) == [topLeft: [[1, 3], [7, 5]], topRight: [[9, 3], [9, 1]], bottomLeft: [[9, 3], [8, -2]], bottomRight: [[-8, 4], [-4, 6]]]

        // Test combining...
        assert combineMatrix([topLeft: [[1]], topRight: [[3]], bottomLeft: [[7]], bottomRight: [[5]]]) == matrixOne
        assert combineMatrix([topLeft: [[1, 3], [7, 5]], topRight: [[9, 3], [9, 1]], bottomLeft: [[9, 3], [8, -2]], bottomRight: [[-8, 4], [-4, 6]]]) == matrixTwo

        // Test adding...
        assert addMatrices(matrixOne, [[7, 4], [-7, 0]]) == [[8, 7], [0, 5]]
        assert addMatrices(matrixOne, new int[matrixOne.size()][matrixOne[0].size()]) == matrixOne

        // Test scalar multiply...
        assert scalarMultiply(matrixOne, -1) == [[-1, -3], [-7, -5]]
        assert scalarMultiply(matrixOne, 0) == new int[matrixOne.size()][matrixOne[0].size()]

        // Test subtracting...
        assert subtractMatrices(matrixOne, [[7, 4], [-7, 0]]) == [[-6, -1], [14, 5]]
        assert subtractMatrices(matrixOne, new int[matrixOne.size()][matrixOne[0].size()]) == matrixOne

        // Test multiplying.
        assert naiveMultiplyMatrices([[1, 3], [7, 5]], [[6, 8], [4, 2]]) == [[18, 14], [62, 66]]

        def bigMatrixOne = [[34, 21, -98, 6], [20, 4, -12, 9], [6, 3, 9, 13], [4, -2, 10, 13]]
        def bigMatrixTwo = [[0, 12, -7, -20], [23, 32, 14, 9], [8, 5, -8, 12], [5, -7, 20, 2]]
        def bigMatrixResult = [[-271, 548, 960, -1655], [41, 245, 192, -490], [206, 122, 188, 41], [99, -57, 124, 48]]
        assert naiveMultiplyMatrices(bigMatrixOne, bigMatrixTwo) == bigMatrixResult

        // Test SMART multiplying.
        assert intelligentlyMultiplyMatrices([[1, 3], [7, 5]], [[6, 8], [4, 2]]) == [[18, 14], [62, 66]]
        assert intelligentlyMultiplyMatrices(bigMatrixOne, bigMatrixTwo) == bigMatrixResult

        println "Success!"
    }
}