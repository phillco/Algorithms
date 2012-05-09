package datastructures

/**
 * Created by IntelliJ IDEA.
 * User: Phillip
 * Date: 5/8/12
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
class SkipList<T> {

    static final MAX_LEVEL = 16;

    class InternalNode<T> {

        int key;
        T value;
        int height
        InternalNode<T>[] next = new InternalNode<T>[height]
    }

    ArrayList<InternalNode<T>> headers = new ArrayList<InternalNode<T>>();

    int level() { headers.size() }

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

    static int randomLevel() {

        def random = new Random()
        int level = 0
        while (random.nextBoolean() && level < MAX_LEVEL)
            level++;

        return level;
    }

    def add(int key, T value) {

        // Start at the highest possible header.
        InternalNode<T>[] toUpdate = new InternalNode<T>[MAX_LEVEL]

        int i = headers.size()
        while (i >= 1 && headers[i - 1].key > key)
            i--;

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
            for (int i = 0; i < newNode.height; i++) {
                newNode.next[i] = toUpdate[i].next[i];
                toUpdate[i].next[i] = newNode;
                
                if ( i >= headers.size())
                    headers.add(newNode)
            }
        }
    }

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
}
