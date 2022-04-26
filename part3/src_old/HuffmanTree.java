public class HuffmanTree {

    private Node root;
    private PQHeap queue;

    public HuffmanTree( PQHeap queue ) {

        this.root = null;
        this.queue = queue;

    }

    /**
     * Extracts the two minimum values, merges them, and inserts them back into the queue.
     * Pre: queue length is greater than 1.
     */
    public void mergeMin() {

        Node z = new Node( 0, null );

        z.left = queue.extractMin();
        z.right = queue.extractMin();
        z.key = z.left.key() + z.right.key();

        queue.insert( z );

    }

    /**
     * Wrapper class for Element.
     */
    private class Node extends Element {

        private int key;
        private Object data;
        private Node left;
        private Node right;

        public Node( int key, Object data ) { 

           this.key = key;
           this.data = data;
           this.left = null;
           this.right = null;

        }

        public Node left() { return left; }

        public Node right() { return right; }

    }

}

