package algorithms

/**
 * Algorithms to determine the differences between two strings.
 *
 * This is like EditDistance but with more operations -- twiddle and kill.
 *  Twiddle = insert the next two characters in reverse order
 *  Kill = stop the string here
 *  Copy = same as "skip" in basic mode but (potentially) with a cost
 *
 * Also, the costs for each operation must be user-customizable.
 *
 * @see "Algorithms", Problem 15-5, "Edit Distance"
 */
class AdvancedEditDistance {

    static enum BasicAction {
        Insert, Delete, Replace, Copy, Twiddle, Kill
    }


    static getDefaultCosts() {
        ["Copy": 0, "Replace": 2, "Delete": 5, "Insert": 5, "Twiddle": 7, "Kill": 7]
    }

    /**
     * Returns the minimum number of operations needed to change the string one into two.
     */
    static def computeLevenshteinDistance(String one, String two, costs) {

        def computations = new int[two.length() + 1][one.length() + 1]

        // Prefix the special case for the first row and column.
        for (int j = 1; j <= one.length(); j++)
            computations[0][j] = j * costs["Delete"];
        for (int i = 1; i <= two.length(); i++)
            computations[i][0] = i * costs["Insert"];

        for (int j = 1; j <= one.length(); j++) {
            for (int i = 1; i <= two.length(); i++) {

                boolean copyAllowed = (two.charAt(i - 1) == one.charAt(j - 1));

                // Which of these three is cheapest?
                int deletionCost = costs["Delete"] + computations[i - 1][j];
                int insertCost = costs["Insert"] + computations[i][j - 1];
                int copyCost = copyAllowed ? (costs["Copy"] + computations[i - 1][j - 1]) : Integer.MAX_VALUE;
                int replaceCost = costs["Replace"] + computations[i - 1][j - 1];

                int replacementCost = computations[i][j] = fourWayMin(insertCost, deletionCost, copyCost, replaceCost)
            }
        }

        [one: one, two: two, cost: computations[two.length()][one.length()], costs: costs, table: computations]
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
                        
            def cheapestNeighbor = threeWayMin(result.table[i - 1][j], result.table[i][j - 1], result.table[i - 1][j - 1])

            if (cheapestNeighbor == result.table[i - 1][j]) { // Insert
                actions.add(0, BasicAction.Insert);
                i--;
            } else if (cheapestNeighbor == result.table[i][j - 1]) { // Delete
                actions.add(0, BasicAction.Delete);
                j--;
            } else { // Replace

                // Was it a replacement, or just a skip?
                if (result.table[i][j] == result.costs["Copy"] + result.table[i - 1][j - 1])
                    actions.add(0, BasicAction.Copy);
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
     * Returns the mininum of the given three numbers.
     */
    static int threeWayMin(first, second, third) {
        return (int) Math.min(Math.min(first, second), third);
    }

    /**
     * Returns the mininum of the given four numbers.
     */
    static int fourWayMin(first, second, third, fourth) {
        return (int) Math.min(Math.min(Math.min(first, second), third), fourth);
    }

    /**
     * Tests all the algorithms.
     */
    static test() {
        prettyPrint(computeLevenshteinDistance("kitten", "sitting", defaultCosts));
        prettyPrint(computeLevenshteinDistance("Saturday", "Sunday", defaultCosts));
    }

    static void main(String[] args) { test(); }
}
