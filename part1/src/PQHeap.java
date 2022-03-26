/*
 * Bastian Graver (...)
 * Ian Andersen (...)
 * Valdemar Lorenzen (valor21@student.sdu.dk)
 *
 * Aflevering af delprojekt 1 - Algoritmer og Datastrukturer.
 */

import java.util.ArrayList;

public class PQHeap implements PQ {
    private ArrayList<Element> queue;

    /*
     * Returns a new, empty, `PQHeap`.
     */
    public PQHeap() {
        queue = new ArrayList<Element>();
    }

    /*
     * Returns the index of the left child of the element at the given index.
     */
    private int leftIdx(int i) {
        return 2 * i + 1;
    }

    /*
     * Returns the index of the right child of the element at the given index.
     */
    private int rightIdx(int i) {
        return 2 * i + 2;
    }

    /*
     * Returns the index of the parent of the element at the given index.
     */
    private int parentIdx(int i) {
        return (i - 1) / 2;
    }

    /*
     * Inserts the given element into the priority queue.
     */
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

    /*
     * Orders the elements in the priority queue correctly.
     */
    private void minHeapify(int i) {
        int l = leftIdx(i);
        int r = rightIdx(i);

        int smallest;

        if (l < queue.size() && queue.get(l).getKey() <= queue.get(i).getKey()) {
            smallest = l;
        } else {
            smallest = i;
        }

        if (r < queue.size() && queue.get(r).getKey() <= queue.get(smallest).getKey()) {
            smallest = r;
        }

        if (smallest != i) {
            Element tmpElem = queue.get(i);
            queue.set(i, queue.get(smallest));
            queue.set(smallest, tmpElem);

            minHeapify(smallest);
        }
    }

    /*
     * Returns whether the priority queue is empty or not.
     */
    public boolean isEmpty() { 
        return (queue.size() == 0);
    }

    // Overrides //

    /*
     * Removes the element of least priority and returns it.
     * Pre: the priority queue cannot be empty.
     */
    public Element extractMin() {
        Element min;

        if (queue.size() > 1) {
            min = queue.get(0);
            queue.set(0, queue.remove(queue.size() - 1));
            minHeapify(0);
        } else
            min = queue.remove(0);

        return min;
    }

    /*
     * Inserts element e into the priority queue.
     */
    public void insert(Element e) {
        minHeapInsert(e);
    }

    /*
     * Returns a textual representation of the priority queue.
     */
    public String toString() {
        String str = "";

        for (Element elem : queue) {
            str += "(" + elem.getKey() + ", " + elem.getData().toString() + ")   ";
        }

        return str;
    }
}

