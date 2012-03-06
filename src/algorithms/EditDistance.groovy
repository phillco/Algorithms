package algorithms

/**
 * Algorithms to determine the differences between two strings.
 * @see "Algorithms", Problem 15-5, "Edit Distance"
 */
class EditDistance {

    /**
     * Prints the list of (cheapest) operations needed to change one into two.
     */
    static def computeLevenshteinDistance(String one, String two) {

        def computations = new int[two.length() + 1][one.length() + 1]

        // Prefix the special case for the first row and column.
        for (int j = 1; j <= one.length(); j++)
            computations[0][j] = j;
        for (int i = 1; i <= two.length(); i++)
            computations[i][0] = i;

        for (int j = 1; j <= one.length(); j++) {
            for (int i = 1; i <= two.length(); i++) {

                int replacementCost = (two.charAt(i - 1) == one.charAt(j - 1)) ? 0 : 1;

                computations[i][j] = threeWayMin(computations[i - 1][j] + 1, computations[i][j - 1] + 1, computations[i - 1][j - 1] + replacementCost)
            }
        }

        [one: one, two: two, cost: computations[two.length()][one.length()], table: computations]
    }

    /**
     * Returns the mininum of the given three numbers.
     */
    static int threeWayMin(first, second, third) {
        return (int) Math.min(Math.min(first, second), third);
    }

    /**
     * Nicely prints the results of a calculation.
     */
    static prettyPrint(result) {
        println "Cost for changing ${result.one} to ${result.two} is ${result.cost}:"
        MatrixChaining.print2dArray(result.table, 3);
        println();
    }

    /**
     * Tests all the algorithms.
     */
    static test() {
        prettyPrint(computeLevenshteinDistance("kitten", "sitting"));
        prettyPrint(computeLevenshteinDistance("Saturday", "Sunday"));
    }

    static void main(String[] args) { test(); }

}
