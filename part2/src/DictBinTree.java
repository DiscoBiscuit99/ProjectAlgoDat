/*
 * Bastian Graver (...)
 * Ian Andersen (...)
 * Valdemar Lorenzen (valor21@student.sdu.dk)
 *
 * Aflevering af delprojekt 2 - Algoritmer og Datastrukturer.
 */

import java.util.ArrayList;

public class DictBinTree implements Dict {
    private BinNode root;

    /*
     * Returns a new, empty, DictBinTree.
     */
    public DictBinTree() {
        root = null;
    }

    /*
     * Auxiliary method.
     */
    private void insert(BinNode z) { 
        BinNode y = null;
        BinNode x = root;

        // Help z find its place in the tree.
        while (x != null) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        // If `y` is null, the tree is empty. Set `z` as the new root.
        if (y == null)
            root = z;
        // ... otherwise, set a new child reference for the parent.
        else if (z.key < y.key)
            y.left = z;
        else
            y.right = z;
    }

    /*
     * Auxiliary method.
     */
    private ArrayList<Integer> orderedTraversal(BinNode x, ArrayList<Integer> xs) {
        // Add the keys of this node and its subtrees in sorted order.
        // First the smaller keys, then the current key, and lastly, the bigger keys.
        if (x != null) {
            orderedTraversal(x.left, xs);
            xs.add(x.key);
            orderedTraversal(x.right, xs);
        }
        return xs;
    }

    // Overrides //
    
    /*
     * Returns whether the given key exists in the tree.
     */
    public boolean search(int k) {
        BinNode x = root;

        // Search the tree iteratively.
        while (x != null && x.key != k) {
            if (k > x.key)
                x = x.right;
            else
                x = x.left;
        }

        return (x == null ? false : k == x.key);
    }

    /*
     * Inserts the given key into the tree.
     */
    public void insert(int k) {
        insert(new BinNode(k));
    }

    /*
     * Returns a sorted ArrayList of the keys in the tree.
     * Pre: the dictionary cannot be empty.
     */
    public ArrayList<Integer> orderedTraversal() {
        return orderedTraversal(root, new ArrayList<Integer>());
    }

    /*
     * Returns a textual representation of the tree.
     */
    public String toString() {
        return "DictBinTree toString is not yet implemented...";
    }

    /*
     * Implementation of the nodes of the tree.
     */
    private class BinNode {
        public int key;
        public BinNode left;
        public BinNode right;

        /*
         * Returns a new BinNode with the given key.
         */
        public BinNode(int k) {
            key = k;
            left = null;
            right = null;
        }

        // Overrides //

        /*
         * Returns a textual representation of this BinNode.
         */
        public String toString() {
            return "" + key;
        }
    }
}

