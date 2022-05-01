public class Node {

    private Object left;
    private Object right;

    /**
     * Returns a new node with the given children.
     */
    public Node( Object left, Object right ) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left child.
     */
    public Object left() {
        return left;
    }

    /**
     * Returns the right child.
     */
    public Object right() {
        return right;
    }

}
