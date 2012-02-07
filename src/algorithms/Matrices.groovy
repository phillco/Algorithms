package algorithms

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

//==============
// TESTING
//==============

assert splitMatrix([[1, 3], [7, 5]]) == [topLeft: [[1]], topRight: [[3]], bottomLeft: [[7]], bottomRight: [[5]]]
assert splitMatrix([[1, 3, 9, 3], [7, 5, 9, 1], [9, 3, -8, 4], [8, -2, -4, 6]]) == [topLeft: [[1, 3], [7, 5]], topRight: [[9, 3], [9, 1]], bottomLeft: [[9, 3], [8, -2]], bottomRight: [[-8, 4], [-4, 6]]]
