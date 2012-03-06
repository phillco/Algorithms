package algorithms

/**
 * Aligns two sequences of DNA using a variation of the EditDistance formula.
 *
 * @see "Algorithms", Problem 15-5, "Edit Distance"
 */
class DnaAlignment extends AdvancedEditDistance {

    static getDnaCosts() { ["Copy": -1, "Replace": 1, "Delete": 2, "Insert": 2, "Twiddle": 9999, "Kill": 9999] }

    /**
     * Nicely prints the results of a calculation.
     * @param result
     */
    static prettyPrintDna(result) {
        println "Best cost for aligning \"${result.one}\" to \"${result.two}\" is ${result.cost}:"
        printTable(result);

        // Print the strands of DNA, by inserting spaces whenever an insertion or deletion occurs.
        String one = result.one;
        String two = result.two;

        int i = 0;
        for (Action action: getListOfChanges(result)) {

            // Deleting = insert space in two; inserting = insert space in one.
            if (action == Action.Delete)
                two = two.substring(0, i) + " " + two.substring(i);
            else if (action == Action.Insert)
                one = one.substring(0, i) + " " + one.substring(i);

            i++;
        }

        println "\n$one\n$two\n";
    }

    static demonstrateDna() {
        prettyPrintDna(computeEditDistance("ATGGCGT", "ATGAGT", dnaCosts));
        prettyPrintDna(computeEditDistance("GATCGGCAT", "CAATGTGAATC", dnaCosts));
    }

    static void main(String[] args) { demonstrateDna() }
}
