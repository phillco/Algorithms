package datastructures

/**
 * A skip list.
 */
class SkipList<T> {

    static final MAX_LEVEL = 16;

    /**
     * A node in the list.
     */
    class InternalNode<T> {

        int key;
        T value;
        int height
        InternalNode<T>[] next = new InternalNode<T>[height]
    }

    // The "header" of the list.
    ArrayList<InternalNode<T>> headers = new ArrayList<InternalNode<T>>();

    int level() { headers.size() }

    /**
     * Returns the value of the node with the given key, or null if it doesn't exist.
     */
    def search(int searchKey) {

        // Start at the highest possible header.
        int i = headers.size()
        while (i >= 1 && headers[i - 1].key > searchKey)
            i--;

        InternalNode node = headers[i];
        for (int i = node.height; i > 1; i--)
            while (node.next[i].key < searchKey)
                node = node.next[i]

        node = node.next[0]
        if (node.key == searchKey)
            return node.value;
        else
            return null;
    }

    /**
     * Adds the key/value combo to the list.
     */
    def add(int key, T value) {

        // Track the last node at each level.
        InternalNode<T>[] toUpdate = new InternalNode<T>[MAX_LEVEL]

        // Start at the highest applicable level.
        int i = headers.size()
        while (i >= 1 && headers[i - 1].key > key)
            i--;

        // Exactly like search() but we update toUpdate.
        InternalNode node = headers[i];
        for (int i = node.height; i > 1; i--) {
            while (node.next[i].key < key)
                node = node.next[i]

            toUpdate[i] = node;
        }

        node = node.next[0]
        if (node.key == key)
            node.value = value; // Update
        else {
            int level = randomLevel()

            def newNode = new InternalNode<T>(value: value, key: key, height: level)

            // Update all the past references.
            for (int i = 0; i < newNode.height; i++) {
                newNode.next[i] = toUpdate[i].next[i];
                toUpdate[i].next[i] = newNode;

                if (i >= headers.size())
                    headers.add(newNode)
            }
        }
    }

    /**
     * Finds the smallest element in the list using (implicit) compareTo.
     */
    public T min() {
        if (!headers[0])
            return null;

        InternalNode<T> node = headers[0];
        T min = node.value
        while (node) {
            if (node.value < min)
                min = node.value
            node = node.next[0];
        }

        return min;
    }

    /**
     * Returns a random height for a new node.
     */
    static int randomLevel() {

        def random = new Random()
        int level = 0
        while (random.nextBoolean() && level < MAX_LEVEL)
            level++;

        return level;
    }

}
