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
        int level
        InternalNode<T>[] next = new InternalNode<T>[level + 1]

        String toString() { "[$key]: $value (level $level)"}
    }

    // The "header" of the list.
    ArrayList<InternalNode<T>> headers = new InternalNode<T>[MAX_LEVEL+1]
    int listLevel = 0;

    int level() { headers.size() }

    /**
     * Returns the value of the node with the given key, or null if it doesn't exist.
     */
    def search(int searchKey) {

        // Start at the highest possible header.
        int i = listLevel
        while (i > 0 && headers[i].key > searchKey)
            i--;

        InternalNode node = headers[i];
        for (; i > 0; i--)
            while (node.next[i] != null && node.next[i].key < searchKey)
                node = node.next[i]

        node = node.next[0]
        if (node?.key == searchKey)
            return node.value;
        else
            return null;
    }

    /**
     * Adds the key/value combo to the list.
     */
    def add(int key, T value) {

        // Track the last node at each level.
        InternalNode<T>[] toUpdate = new InternalNode<T>[MAX_LEVEL+1]

        // Start at the highest applicable level.
        int i = listLevel
        while (i > 0 && (!headers[i] || headers[i].key > key))
            i--;

        // We must insert it first!
        if (i == 0) {
            int level = randomLevel()
            def newNode = new InternalNode<T>(value: value, key: key, level: level, next: new InternalNode<T>[level + 1])
            for (i = 1; i <= level; i++) {

                if (listLevel >= i)
                    newNode.next[i] = headers[i]
                headers[i] = newNode;
            }
            listLevel = Math.max(level, listLevel);
            return;
        }

        // Exactly like search() but we update toUpdate.
        InternalNode node = headers[i];
        while (i > 0) {
            while (node.next[i] != null && node.next[i]?.key < key)
                node = node.next[i]

            toUpdate[i] = node;
            i--;
        }

        node = node.next[0]
        if (node?.key == key)
            node.value = value; // Update
        else {
            int level = randomLevel()

            def newNode = new InternalNode<T>(value: value, key: key, level: level, next: new InternalNode<T>[level + 1])

            // Update all the past references.
            for (i = 1; i <= newNode.level; i++) {
                if (toUpdate[i]) {
                    newNode.next[i] = toUpdate[i].next[i];
                    toUpdate[i].next[i] = newNode;
                }

                if (i > listLevel)
                    headers[++listLevel] = newNode
            }
        }
    }

    /**
     * Finds the smallest element in the list using (implicit) compareTo.
     */
    public T min() {
        if (!headers[0])
            return null;
        else
            return headers[0].value;

        return min;
    }

    /**
     * Returns a random level for a new node.
     */
    static int randomLevel() {

        def random = new Random()
        int level = 1
        while (random.nextBoolean() && level < MAX_LEVEL)
            level++;

        return level;
    }

}
