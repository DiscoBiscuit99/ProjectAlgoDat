public class Node {

    private int key;
    private Object left;
    private Object right;

    public Node( int key, Object left, Object right ) {

        this.key = key;
        this.left = left;
        this.right = right;

    }

    public int key() {

        return key;

    }

    public Object left() {

        return left;

    }

    public Object right() {

        return right;

    }

}
