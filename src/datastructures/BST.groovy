package datastructures

/**
 * A binary search tree.
 */
class BST {

    /**
     * A node in the tree.
     */
    class BNode {
        int value;
        BNode left;
        BNode right;
    }

    // Root of this tree.
    BNode root = null;

    /**
     * Adds the given value to the tree.
     */
    public void add(value) {

        if (root) {

            def node = root;
            while (node) {

                if (node.value == value) // Already exists.
                    return;
                else if (value < node.value) { // Follow left.
                    if (node.left)
                        node = node.left;
                    else {
                        node.left = new BNode(value: value)
                        return;
                    }
                }
                else if (value > node.value) { // Follow right.
                    if (node.right)
                        node = node.right;
                    else {
                        node.right = new BNode(value: value)
                        return;
                    }
                }
            }
        }
        else
            root = new BNode(value: value);
    }

    /**
     * Returns if the given value is in the tree.
     */
    public boolean search(int value) {
        if (root) {

            def node = root;
            while (node) {
                if (node.value == value)
                    return true;
                else if (value < node.value)
                    node = node.left;
                else if (value > node.value)
                    node = node.right;
            }

            return false;
        }
    }

    /**
     * Returns the smallest element in the tree.
     */
    public int min() {
        if (root) {

            def node = root;
            while (node?.left)
                node = node.left

            node.value;
        }
    }

}
