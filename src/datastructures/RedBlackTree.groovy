package datastructures

/**
 * A red-black tree.
 */
class RedBlackTree {

    /**
     * A node in the tree.
     */
    class RBNode {
        int value;
        boolean colored;
        RBNode left;
        RBNode right;
        RBNode parent;

        public void setLeft(RBNode node) {
            node.parent = this;
            left = node;
        }

        public void setRight(RBNode node) {
            node.parent = this;
            right = node;
        }

        public RBNode getGrandParent() { parent?.parent }

        // The "other" child.
        public RBNode getSibling() {
            if (!parent)
                return null;

            return (this == parent.left) ? parent.right : parent.left;
        }

        // The sibling of our parent.
        public RBNode getUncle() {
            return parent.sibling;
        }
    }

    // Root of this tree.
    RBNode root = null;

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
                        node.left = new RBNode(value: value)
                        return;
                    }
                }
                else if (value > node.value) { // Follow right.
                    if (node.right)
                        node = node.right;
                    else {
                        node.right = new RBNode(value: value)
                        return;
                    }
                }
            }
        }
        else
            root = new RBNode(value: value);
    }

    /**
     * Returns if the given value is in the tree.
     * (Same as BST)
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
     * (Same as BST)
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
