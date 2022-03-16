import java.util.ArrayList;

public class PQHeap implements PQ {
    private ArrayList<Element> queue;

    public PQHeap() {
        queue = new ArrayList<Element>();
    }

    /*
     * Removes the element of least priority and returns it.
     * Pre: the priority queue cannot be empty.
     */
    public Element extractMin() {
        // TODO: everything inside here...

        return new Element(0, "test_string");
    }

    /*
     * Inserts element e into the priority queue.
     */
    public void insert(Element e) {

    }

    private int leftIdx(int i) {
        return 2 * i + 1;
    }

    private int rightIdx(int i) {
        return 2 * i + 2;
    }

    private int parentIdx(int i) {
        return (i - 1) / 2;
    }

    private void minHeapInsert(Element elem) {
        queue.add(elem);
        int i = queue.size() - 1;
        while (i > 0 && queue.get(parentIdx(i)).getKey() > queue.get(i).getKey()) {
            // Exchange queue[i] with queue[parent(i)]
            Element tmpElem = queue.get(i);
            queue.set(i, queue.get(parentIdx(i)));
            queue.set(parentIdx(i), tmpElem);

            i = parentIdx(i);
        }
    }

    public void insert(int key, Object data) {
        minHeapInsert(new Element(key, data));
    }

    /* Overrides */

    public String toString() {
        String str = "";

        for (Element elem : queue) {
            str += "(" + elem.getKey() + ", " + elem.getData().toString() + ")   ";
        }

        return str;
    }
}

