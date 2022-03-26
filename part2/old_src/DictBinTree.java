import java.util.ArrayList;

public class DictBinTree implements Dict {
    BinNode root;

    // Creates a empty tree.
    public DictBinTree(){
        root = null;
    }


    // Inserts a given key into the tree.
    public void insert(int key){
        root = insertRec(root, key);
    }
    // Does the recursive insertion
    private BinNode insertRec(BinNode root, int key){
        //System.out.println("insert");
        // If the subtree is empty place the key
        if(root == null){
            root = new BinNode(key);
            //System.out.println("key placed");
            return root;
        }
        // Recurse on either the left or right side.
        if(root.key < key){
            root.right = insertRec(root.right,key);
        }
        else if(root.key >= key){
            root.left = insertRec(root.left,key);
        }
        // if nothing is change return the original root.
        return root;
    }



    // Checks if the tree contains a given key.
    public boolean search(int key){
        boolean contains = false;
        contains = containsKeyRec(root, key, contains);
        return contains;
    }
    // Recursively Finds the given key
    private boolean containsKeyRec(BinNode root,int key,boolean contains){
        //System.out.println("contains");
        if(root.key == key ){
            contains = true;
            return contains;
        }
        else if(root.key < key && root.right != null){
            return containsKeyRec(root.right, key, contains);
        }
        else if(root.key >= key && root.left != null){
            return containsKeyRec(root.left, key, contains);
        }
        return contains;
    }

    // TODO
    public ArrayList<Integer> orderedTraversal(){
        ArrayList<Integer> list = new ArrayList<>();
        orderedTraversalRec(list, root);
        return list;
    }
    private void orderedTraversalRec(ArrayList<Integer> list, BinNode node){
        System.out.println(node.key);
        // Adds the notes key to the list if its a leaf.
        if(node.left == null && node.right == null){
            System.out.println("null add " + node.key);
            list.add(node.key);
        }
        // The recursion procedes down to the nodes smaller child.
        if(node.left != null){
            System.out.println("left ");
            orderedTraversalRec(list, node.left);
        }
        // Adds the note's key to list.
        if(node.left != null || node.right != null){
            System.out.println("add " + node.key);
            list.add(node.key);
        }
        //  The recursion procedes down to the nodes larger child.
        if(node.right != null){
            System.out.println("right");
            orderedTraversalRec(list, node.right);
        }
    }


    private class BinNode{
        BinNode left;
        BinNode right;
        int key;
        public BinNode(int key){
            this.key = key;
            left = null;
            right = null;
        }
    }
}
