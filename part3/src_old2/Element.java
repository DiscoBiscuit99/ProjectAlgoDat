
public class Element {

    private int key;
    private Object data;

    /**
     * Returns a new element with the given properties.
     */
    public Element( int key, Object data ) {

        this.key = key;
        this.data = data;

    }

    /**
     * Returns the key of the element.
     */
    public int key() { 

        return key; 

    }

    /**
     * Returns the data of the element.
     * Note: leaking private object(?), but whatever...
     */
    public Object data() { 

        return data; 

    }

}

