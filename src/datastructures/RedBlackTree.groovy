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
        boolean colored = false; // Black (true) or red (false)
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
    public void add(int value) {
        RBNode insertedNode = new RBNode(value: value);
        if (root == null)
            root = insertedNode; // Just make it the root.
        else {
            RBNode node = root;
            while (true) {   // Almost exactly like BST but track node to keep as parent.
                if (value == node.value) {
                    node.value = value;
                    return;
                } else if (value < node.value) {
                    if (node.left == null) {
                        node.left = insertedNode;
                        break;
                    } else
                        node = node.left;
                } else {
                    if (node.right == null) {
                        node.right = insertedNode;
                        break;
                    } else
                        node = node.right;
                }
            }
            insertedNode.parent = node;
        }

        // Correct any new problems with the red-black property.
        fixup(insertedNode);
    }

    private void fixup(RBNode n) {

    }

    /**
     * Rotates the subtree rooted at node left.
     */
    private void rotateLeft(RBNode node) {
        RBNode right = node.right;
        replaceNode(node, right);
        node.right = right.left;
        if (right.left != null)
            right.left.parent = node;
        right.left = node;
        node.parent = right;
    }

    /**
     * Rotates the subtree rooted at node right.
     */
    private void rotateRight(RBNode node) {
        RBNode left = node.left;
        replaceNode(node, left);
        node.left = left.right;
        if (left.right != null)
            left.right.parent = node;
        left.right = node;
        node.parent = left;
    }

    /**
     * Swaps these two nodes in the tree.
     * Cuts them away from their parents, potentially updating the root.
     */
    private void replaceNode(RBNode oldNode, RBNode newNode) {
        if (oldNode.parent == null)
            root = newNode;
        else {
            if (oldNode == oldNode.parent.left)
                oldNode.parent.left = newNode;
            else
                oldNode.parent.right = newNode;
        }

        if (newNode != null)
            newNode.parent = oldNode.parent;
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
