package datastructures

/**
 * Created by IntelliJ IDEA.
 * User: Phillip
 * Date: 5/8/12
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
class BST {

    class BNode {
        int value;
        BNode left;
        BNode right;
    }

    BNode root = null;

    public void add(value) {
        if (root) {

            def node = root;
            while (node) {
                if (node.value == value)
                    return;
                else if (value < node.value) {
                    if (node.left)
                        node = node.left;
                    else {
                        node.left = new BNode(value: value)
                        return;
                    }
                }
                else if (value > node.value) {
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

    public int min() {
        if (root) {

            def node = root;
            while (node?.left)
                node = node.left

            node.value;
        }
    }

}
