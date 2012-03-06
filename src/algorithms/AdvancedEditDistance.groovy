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

    public static final enum BasicAction {
        Insert, Delete, Replace, Copy, Twiddle, Kill
    }

    static getBasicCosts() { ["Copy": 0, "Replace": 1, "Delete": 1, "Insert": 1, "Twiddle": 5, "Kill": 10] }

    static getDefaultCosts() { ["Copy": 1, "Replace": 3, "Delete": 5, "Insert": 5, "Twiddle": 0, "Kill": 10] }

    /**
     * Returns the minimum number of operations needed to change the string one into two.
     */
    static def computeLevenshteinDistance(String one, String two, costs) {

        def actions = new BasicAction[one.length() + 1][two.length() + 1]
        def computations = new int[one.length() + 1][two.length() + 1]

        // Prefix the special case for the first row and column.
        for (int i = 1; i <= one.length(); i++) {
            computations[i][0] = i * costs["Delete"];
            actions[i][0] = BasicAction.Delete;
        }
        for (int j = 1; j <= two.length(); j++) {
            computations[0][j] = j * costs["Insert"];
            actions[0][j] = BasicAction.Insert;
        }

        for (int i = 1; i <= one.length(); i++) {
            for (int j = 1; j <= two.length(); j++) {

                boolean copyAllowed = (one.charAt(i - 1) == two.charAt(j - 1));
                boolean twiddleAllowed = (i > 1 && j > 1) && (one.charAt(i - 1) == two.charAt(j - 2)) && (one.charAt(i - 2) == two.charAt(j - 1));

                // Which of these three is cheapest?
                int insertCost = costs["Insert"] + computations[i][j - 1];
                int deleteCost = costs["Delete"] + computations[i - 1][j];
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

        // Search for the cheapest cut-off point for killing.
        int lowestIndex = 1;
        for (int i = 1; i < one.length(); i++) {
            if (computations[i][two.length()] < computations[lowestIndex][two.length()])
                lowestIndex = i;
        }

        // Is it worth it to kill?
        if (costs["Kill"] + computations[lowestIndex][two.length()] < computations[one.length()][two.length()]) {
            computations[one.length()][two.length()] = costs["Kill"] + computations[lowestIndex][two.length()]
            actions[one.length()][two.length()] = BasicAction.Kill;
        }

        [one: one, two: two, cost: computations[one.length()][two.length()], costs: costs, table: computations, actions: actions]
    }

    /**
     * Given a costs matrix, returns the best (cheapest) list of operations by walking the table (backwards).
     */
    static List<BasicAction> getListOfChanges(result) {

        List<BasicAction> actions = new LinkedList<BasicAction>();

        // Start at the end result and work backwards.
        int i = result.table.size() - 1;
        int j = result.table[0].size() - 1;

        while (i > 0 || j > 0) {

            // Push this action to the top of the "actions" stack.
            actions.add(0, result.actions[i][j]);

            switch (result.actions[i][j]) {
                case BasicAction.Insert:
                    j--;
                    break;
                case BasicAction.Delete:
                    i--;
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
                case BasicAction.Kill:

                    // A bit tricky. Back-track to find the cheapest value at the end of the substring; this is the cutoff point.
                    int lowestIndex = 1;
                    for (int k = 1; k < result.table.size(); k++) {
                        if (result.table[k][result.table[0].length - 1] < result.table[lowestIndex][result.table[0].length - 1])
                            lowestIndex = k;
                    }
                    i = lowestIndex;
                    break;
            }
        }

        return actions;
    }

    /**
     * Nicely prints a 2D array.
     */
    static void printTable(result) {
        for (int i = 0; i < result.table[0].length; i++) {
            for (int j = 0; j < result.table.length; j++)
                System.out.print(String.format("%${13}s", "${result.actions[j][i]} (${result.table[j][i]})"));
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
     * Tests the algorithms.
     */
    static test() {

        assert getListOfChanges(computeLevenshteinDistance("kitten", "sitting", basicCosts)) == [BasicAction.Replace, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Replace, BasicAction.Copy, BasicAction.Insert]
        assert getListOfChanges(computeLevenshteinDistance("Saturday", "Sunday", basicCosts)) == [BasicAction.Copy, BasicAction.Delete, BasicAction.Delete, BasicAction.Copy, BasicAction.Replace, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy]
        assert getListOfChanges(computeLevenshteinDistance("potc", "ptoc", defaultCosts)) == [BasicAction.Copy, BasicAction.Twiddle, BasicAction.Copy]
        assert getListOfChanges(computeLevenshteinDistance("123pot", "a123pot", basicCosts)) == [BasicAction.Insert, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy]
        assert getListOfChanges(computeLevenshteinDistance("a123pot", "123pot", basicCosts)) == [BasicAction.Delete, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy, BasicAction.Copy]
        assert getListOfChanges(computeLevenshteinDistance("otp", "top", defaultCosts)) == [BasicAction.Twiddle, BasicAction.Copy];
        assert getListOfChanges(computeLevenshteinDistance("ot", "to", defaultCosts)) == [BasicAction.Twiddle];
        assert getListOfChanges(computeLevenshteinDistance("applesauce", "apple", defaultCosts)) == [BasicAction.Copy, BasicAction.Twiddle, BasicAction.Copy, BasicAction.Copy, BasicAction.Kill];

        println "== Tests passed! ==\n"
    }

    /**
     * Demonstrates the algorithms.
     */
    static demonstrate() {
        prettyPrint(computeLevenshteinDistance("kitten", "sitting", basicCosts));
        prettyPrint(computeLevenshteinDistance("Saturday", "Sunday", basicCosts));
        prettyPrint(computeLevenshteinDistance("applesauce", "apple", defaultCosts));
    }

    static void main(String[] args) { test(); demonstrate(); }
}
