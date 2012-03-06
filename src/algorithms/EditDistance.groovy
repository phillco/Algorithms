package algorithms

/**
 * Algorithms to determine the differences between two strings.
 *
 * @see http://en.wikipedia.org/wiki/Levenshtein_distance
 */
class EditDistance {

    static enum BasicAction {
        Insert, Delete, Replace, Skip
    }

    /**
     * Returns the minimum number of operations needed to change the string one into two.
     * Supports these operations: Insert, Delete, Replace, Skip.
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

                // Which of these three is cheapest?
                int deletionCost = computations[i - 1][j] + 1;
                int additionCost = computations[i][j - 1] + 1;
                int replacementCost = computations[i - 1][j - 1] + ((two.charAt(i - 1) == one.charAt(j - 1)) ? 0 : 1);

                computations[i][j] = [additionCost, deletionCost, replacementCost].min()
            }
        }

        [one: one, two: two, cost: computations[two.length()][one.length()], table: computations]
    }

    /**
     * Given a costs matrix, returns the best (cheapest) list of operations by walking the table (backwards).
     */
    static List<BasicAction> getListOfChanges(result) {

        List<BasicAction> actions = new LinkedList<BasicAction>();

        // Start at the end result and work backwards.
        int j = result.table[0].size() - 1;
        int i = result.table.size() - 1;

        while (j > 0 && i > 0) {

            // Determine which edit was made by seeing which neighbor (insert, delete, modify/skip) is cheapest.
            // Then push this action to the top of the "actions" stack.
            def cheapestNeighbor = [result.table[i - 1][j], result.table[i][j - 1], result.table[i - 1][j - 1]].min()
            if (cheapestNeighbor == result.table[i - 1][j]) { // Insert
                actions.add(0, BasicAction.Insert);
                i--;
            } else if (cheapestNeighbor == result.table[i][j - 1]) { // Delete
                actions.add(0, BasicAction.Delete);
                j--;
            } else { // Replace

                // Was it a replacement, or just a skip?
                if (result.table[i - 1][j - 1] == result.table[i][j])
                    actions.add(0, BasicAction.Skip);
                else
                    actions.add(0, BasicAction.Replace);

                j--;
                i--;
            }
        }

        return actions;
    }

    /**
     * Nicely prints the results of a calculation.
     */
    static prettyPrint(result) {
        println "Cost for changing \"${result.one}\" to \"${result.two}\" is ${result.cost}:"
        MatrixChaining.print2dArray(result.table, 3);
        println();

        println "Actions to take: " + getListOfChanges(result).join(", ") + ".\n"
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
