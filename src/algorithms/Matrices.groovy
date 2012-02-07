package algorithms

/**
 * Adds the two matrices.
 */
def addMatrices(one, two) {

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

    def returnMatrix = new int[matrix.size()][matrix[0].size()];

    for (int i = 0; i < matrix.size(); i++)
        for (int j = 0; j < matrix[0].size(); j++)
            returnMatrix[i][j] = matrix[i][j] * scalar;

    return returnMatrix;
}

def multiplyMatrices(one, two) {

    // One's # of columns and two's # of rows must match.
    assert one[0].size() == two.size()

    // Resultant matrix is [one's # of rows][two's # of columns].
    // def returnMatrix = new int[one.size()][two[0].size()]
    def returnMatrix = new int[one.size()][one.size()]

    if (one.size() == 1)
        return one[0][0] * two[0][0]
    else {
        def splitA = splitMatrix(one)
        def splitB = splitMatrix(two)

        returnMatrix = [topLeft: multiplyMatrices(splitA.topLeft, splitB.topLeft) +
                multiplyMatrices(splitA.topRight, splitB.bottomLeft),

                topRight: multiplyMatrices(splitA.topLeft, splitB.topRight) +
                        multiplyMatrices(splitA.topRight, splitB.bottomRight),

                bottomLeft: multiplyMatrices(splitA.bottomLeft, splitB.topLeft) +
                        multiplyMatrices(splitA.bottomRight, splitB.bottomLeft),

                bottomRight: multiplyMatrices(splitA.bottomLeft, splitB.topRight) +
                        multiplyMatrices(splitA.bottomRight, splitB.bottomRight)]

        return combineMatrix(returnMatrix);
    }
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
def combineMatrix(topLeft, topRight, bottomLeft, bottomRight) {

    if (topLeft instanceof Integer)
        return [[topLeft, topRight], [bottomLeft, bottomRight]];

    def returnMatrix = new int[topLeft.size() + bottomLeft.size()][topLeft[0].size() + topRight[0].size()]

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
    assert multiplyMatrices([[1, 3], [7, 5]], [[6, 8], [4, 2]]) == [[18, 14], [62, 66]]

    println "Success!"
}

test();