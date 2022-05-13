/*
 * Bastian Blohm (bablo21@student.sdu.dk)
 * Ian Andersen (iaand21@student.sdu.dk)
 * Valdemar Lorenzen (valor21@student.sdu.dk)
 *
 * Aflevering af delprojekt 3 - Algoritmer og Datastrukturer.
 */

public class Huffman {

    private Element root;

    /**
     * Returns a new Huffman tree with the given node as the root.
     */
    public Huffman( Element root ) {

        this.root = root;

    }

    /**
     * Returns a new Huffman tree with no root (root = null).
     */
    public Huffman() {

        this.root = null;

    }

    /**
     * Returns the root node of this Huffman tree.
     */
    public Element root() {

        return root;

    }

    /**
     * Constructs the Huffman tree from the given frequencies.
     */
    public void constructTree( int[] frequencies ) {

        // 1. Construct a priority queue from the given frequencies. 

        PQHeap pq = new PQHeap();

        for ( char c = 0; c < frequencies.length; c++ )
            if ( frequencies[ c ] > 0 )
                pq.insert( new Element( frequencies[ c ], c ) );

        // 2. Construct the huffman tree by continuously extracting the 
        //    first element of the priority queue.

        while ( pq.size() > 1 ) {

            // Extract the two smallest elements.
            Element leftElem = pq.extractMin();
            Element rightElem = pq.extractMin();

            // Calculate the frequency.
            int freq = leftElem.key() + rightElem.key();

            // Construct the new node.
            Node n = new Node( leftElem, rightElem );
            Element wrappedNode = new Element( freq, n );

            // Insert it back into the priority queue.
            pq.insert( wrappedNode );

        }

        // 3. Extract the root from the priority queue.

        root = pq.extractMin();

    }

    /**
     * Constructs the codewords from the Huffman tree.
     *
     * Pre: root cannot be null.
     */
    public String[] constructCodes() {

        String[] codewords = new String[ 256 ];

        for ( int i = 0; i < codewords.length; i++ )
            codewords[ i ] = "";

        constructCodes( root, "", codewords );

        return codewords;

    }

    /**
     * Constructs the Huffman codes as it descends the tree.
     */
    public void constructCodes( Element wrappedNode, String word, String[] codewords ) {

        /* 
         * Procedure:
         * If the current element contains a node, proceed further down 
         * tree from both its children while appending the corresponding 
         * bit to the codeword.
         * If the current element contains an integer, add the generated
         * codeword to the array of codewords at the index of the integer 
         * (the integer representing a character).
         */

        if ( wrappedNode.data() instanceof Node ) {

            Element leftChild = (Element) ( (Node) wrappedNode.data() ).left();
            Element rightChild = (Element) ( (Node) wrappedNode.data() ).right();

            constructCodes( leftChild, word + "0", codewords );
            constructCodes( rightChild, word + "1", codewords );

        } else if ( wrappedNode.data() instanceof Character ) {

            char c = (char) wrappedNode.data();
            codewords[ c ] = word;

        }

    }

    /**
     * Returns a textual representation of the tree.
     */
    public String toString() {
        
        return subtreeString( root, 0, false );

    }

    /**
     * Auxiliary method: recursively constructs a textual representation 
     * of each node in the tree.
     */
    public String subtreeString( Element wrappedNode, int indent, boolean isLeftChild ) {

        // Determine the prefix.

        int freq = wrappedNode.key();
        String leftOrRight = isLeftChild ? "l" : "r";

        // Indent sufficiently.

        String indentation = "";
        for ( int i = 0; i < indent; i++ )
            indentation += "| ";

        String subtree = indentation + leftOrRight + ": ";

        // If it's not a leaf node, add some relevant information to the string and recurse.
        if ( wrappedNode.data() instanceof Node ) {

            subtree += "( frequency: " + freq + " )\n";

            Element leftChild = (Element) ( (Node) wrappedNode.data() ).left();
            Element rightChild = (Element) ( (Node) wrappedNode.data() ).right();

            subtree += subtreeString( leftChild, indent + 1, true );
            subtree += subtreeString( rightChild, indent + 1, false );

        // If it is a leaf node, add its data to the string.
        } else if ( wrappedNode.data() instanceof Character ) {
            
            char c = (char) wrappedNode.data();
            subtree += "( frequency: " + freq + ", character: " + c + " )\n";

        }

        return subtree;

    }

}

