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
        Dummmy, Insert, Delete, Replace, Copy, Twiddle, Kill
    }


    static getDefaultCosts() {
        ["Copy": 0, "Replace": 1, "Delete": 1, "Insert": 1, "Twiddle": 10, "Kill": 7]
    }

    /**
     * Returns the minimum number of operations needed to change the string one into two.
     */
    static def computeLevenshteinDistance(String one, String two, costs) {

        def actions = new BasicAction[two.length() + 1][one.length() + 1]
        def computations = new int[two.length() + 1][one.length() + 1]

        // Prefix the special case for the first row and column.
        for (int j = 1; j <= one.length(); j++) {
            computations[0][j] = j * costs["Delete"];
            actions[0][j] = BasicAction.Delete;
        }
        for (int i = 1; i <= two.length(); i++) {
            computations[i][0] = i * costs["Insert"];
            actions[i][0] = BasicAction.Insert;
        }

        for (int j = 1; j <= one.length(); j++) {
            for (int i = 1; i <= two.length(); i++) {

                boolean copyAllowed = (two.charAt(i - 1) == one.charAt(j - 1));
                boolean twiddleAllowed = false;// (i > 2 && j > 2) && (two.charAt(i - 2) == one.charAt(j - 1)) && (two.charAt(i - 1) == one.charAt(j - 2));

                // Which of these three is cheapest?
                int insertCost = costs["Insert"] + computations[i - 1][j];
                int deleteCost = costs["Delete"] + computations[i][j - 1];
                int replaceCost = costs["Replace"] + computations[i - 1][j - 1];
                int copyCost = copyAllowed ? (costs["Copy"] + computations[i - 1][j - 1]) : Integer.MAX_VALUE;
                int twiddleCost = twiddleAllowed ? (costs["Twiddle"] + computations[i - 2][j - 2]) : Integer.MAX_VALUE;

                computations[i][j] = [deleteCost, insertCost, copyCost, replaceCost, twiddleCost].min();

                // Note what action we took in our adventurer's diary.
                switch (computations[i][j]) {
                    case insertCost:
                        actions[i][j] = BasicAction.Insert
                        break;
                    case deleteCost:
                        actions[i][j] = BasicAction.Delete
                        break;
                    case replaceCost:
                        actions[i][j] = BasicAction.Replace
                        break;
                    case copyCost:
                        actions[i][j] = BasicAction.Copy
                        break;
                    case twiddleCost:
                        actions[i][j] = BasicAction.Twiddle
                        break;
                }
            }
        }

        [one: one, two: two, cost: computations[two.length()][one.length()], costs: costs, table: computations, actions: actions]
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

            // Push this action to the top of the "actions" stack.
            actions.add(0, result.actions[i][j]);

            switch (result.actions[i][j]) {
                case BasicAction.Insert:
                    i--;
                    break;
                case BasicAction.Delete:
                    j--;
                    break;
                case BasicAction.Copy:
                case BasicAction.Replace:
                    i--;
                    j--;
                    break;
                case BasicAction.Twiddle:
                    i -= 2;
                    j -= 2;
                    break;
            }
        }

        return actions;
    }

    /**
     * Nicely prints a 2D array.
     */
    static void printTable(result) {
        for (int i = 0; i < result.table.length; i++) {
            for (int j = 0; j < result.table[0].length; j++)
                System.out.print(String.format("%${13}s", "${result.actions[i][j]} (${result.table[i][j]})"));
            System.out.println();
        }
    }

    /**
     * Nicely prints the results of a calculation.
     */
    static prettyPrint(result) {
        println "Cost for changing \"${result.one}\" to \"${result.two}\" is ${result.cost}:"
        printTable(result);
        println "\nActions to take: " + getListOfChanges(result).join(", ") + ".\n"
    }

    /**
     * Returns the mininum of the given three numbers.
     */
    static int min(Integer... values) { values?.min() }

    /**
     * Tests all the algorithms.
     */
    static test() {
        prettyPrint(computeLevenshteinDistance("kitten", "sitting", defaultCosts));
        prettyPrint(computeLevenshteinDistance("Saturday", "Sunday", defaultCosts));
    }

    static void main(String[] args) { test(); }
}
