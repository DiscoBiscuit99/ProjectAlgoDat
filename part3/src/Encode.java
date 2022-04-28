import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encode {

    public static void main( String[] args ) {

        // Make sure two filenames are given.
        if ( args.length < 2 ) {

            System.out.println( "The name of the file to compress and the new compressed file must be given..." );
            System.exit( 1 );

        }

        int[] frequencies = constructFreqTable( args[0] );

        for ( int i = 0; i < frequencies.length; i++ )
            System.out.println( (char) i + ": " + frequencies[i] );

        Element huffman = huffman( frequencies );

        // TESTING STUFF //

        String[] codewords = generateCodewords( huffman );

        for ( int c = 0; c < codewords.length; c++ )
            System.out.println( (char) c + ": " + codewords[ c ] );

        writeCompression( frequencies, codewords, args[ 0 ], args[ 1 ] );
        
    }

    /**
     *
     */
    private static void writeCompression( int[] frequencies, String[] codewords, String filenameOriginal, String filenameCompressed ) {

        try {

            File compressedFile = new File( filenameCompressed );
            FileOutputStream foStream = new FileOutputStream( compressedFile );
            BitOutputStream boStream = new BitOutputStream( foStream );

            for ( int f : frequencies )
                boStream.writeInt( f );

            File original = new File( filenameOriginal );
            FileInputStream fiStream = new FileInputStream( original );

            int c;
            while ( ( c = fiStream.read() ) != -1 ) {

                String[] splitWord = codewords[ c ].split( "" );

                for ( String b : splitWord ) {

                    System.out.println( "Writing " + b );
                    boStream.writeBit( Integer.parseInt( b ) );

                }

                System.out.println();

            }

            fiStream.close();
            boStream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

    /**
     * Constructs the frequency table from the given file(name).
     *
     * @param   filename    The name of the file to compress.
     * 
     * @return              An integer array of size 256 with each index 
     *                      representing a character and its value representing 
     *                      its frequency.
     */
    private static int[] constructFreqTable( String filename ) {

        int[] frequencies = new int[ 256 ];

        try {

            File original = new File( filename );
            FileInputStream stream = new FileInputStream( original );

            int c;
            while ( ( c = stream.read() ) != -1 )
                frequencies[ c ] += 1;

            stream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

        return frequencies;

    }

    /**
     * Constructs a Huffman tree from the given frequency list.
     *
     * @param   frequencies The frequency of each character in the original file.
     *
     * @return              The newly constructed huffman tree.
     */
    private static Element huffman( int[] frequencies ) {

        // 1.) Construct the priority queue.

        PQHeap huffmanQueue = new PQHeap();

        for ( int i = 0; i < frequencies.length; i++ )
            huffmanQueue.insert( new Element( frequencies[i], i ) );

        // 2.) Construct the huffman nodes from the priority queue and build the tree.

        while ( huffmanQueue.size() > 1 ) {

            Element leftElem = huffmanQueue.extractMin();
            Element rightElem = huffmanQueue.extractMin();

            int freq = leftElem.key() + rightElem.key();

            Node node = new Node( leftElem, rightElem );

            Element nodeElement = new Element( freq, node );

            huffmanQueue.insert( nodeElement );

        }

        return huffmanQueue.extractMin();

    }

    /**
     * Returns an integer array of codewords generated from the given Huffman tree.
     * Each index corresponds to a character.
     *
     * @param   huffman The Huffman tree to generate the codewords from.
     *
     * @return          An integer array of the generated codewords.
     */
    private static String[] generateCodewords( Element huffman ) {

        // Traverse the tree and generate the codewords.

        String[] codewords = new String[ 256 ];

        generateCodewords( huffman, "", codewords );

        return codewords;

    }

    /**
     * Auxiliary function... TODO: write the doc.
     */
    private static void generateCodewords( Element nodeElem, String word, String[] codewords ) {

        /* 
         * Procedure: check if the node is a leaf node.
         * If it is, add the generated codeword to the array of codewords.
         * If not, generate codewords for both the left and right child of the 
         * current node. It is observed that non-leaf nodes in the Huffman tree 
         * will always have both a left and a right child.
         */

        if ( nodeElem.data() instanceof Node ) {

            Element left = (Element) ( (Node) nodeElem.data() ).left();
            Element right = (Element) ( (Node) nodeElem.data() ).right();

            generateCodewords( left, word + "0", codewords );
            generateCodewords( right, word + "1", codewords );

        } else {

            // Must be an integer - hopefully.
            int c = (int) nodeElem.data();
            codewords[ c ] = word;

        }

        //if ( node instanceof Node ) {

            //Object left = ( (Node) node ).left();
            //Object right = ( (Node) node ).right();

            //generateCodewords( left, word + "0", codewords );
            //generateCodewords( right, word + "1", codewords );

        //} else {

            //int c = (int) node;
            //codewords[ c ] = word;

        //}

        //if ( elem.data() instanceof Node ) {

            //Element left = ( (Node) elem.data() ).left();
            //Element right = ( (Node) elem.data() ).right();

            //generateCodewords( left, word + "0", codewords );
            //generateCodewords( right, word + "1", codewords );

        //} else {

            //int c = (int) elem.data();

            //codewords[ c ] = word;

        //}

        //if ( node != null && node instanceof Element ) {

            //Element elem = (Element) node;

            //int c = (int) elem.data();

            //codewords[ c ] = word;

        //} else ( node != null && node instanceof Node ) {

            //generateCodewords( node.left(), word + "0", codewords );
            //generateCodewords( node.right(), word + "1", codewords );

        //}

        //if ( node.left() == null && node.right() == null ) {

            //Element elem = (Element) node;

            //int c = (int) elem.data();

            //codewords[ c ] = word;

        //} else {

            //generateCodewords( (Node) node.left(), word + "0", codewords );
            //generateCodewords( (Node) node.right(), word + "1", codewords );

        //}


        //if ( node.left() != null ) {

            //Element leftElement = (Element) ( (Node) node ).left();

            //if ( leftElement.data() instanceof Node ) {

                //Node left = (Node) leftElement.data();

                //word += "0";

                //generateCodewords( left, word, codewords );

            //} else if ( leftElement.data() instanceof Integer ) {

                //int c = (int) leftElement.data();
                //codewords[ c ] = word;

            //}

        //}

        //if ( node.right() != null ) {

            //Element rightElement = (Element) node.right();

            //if ( rightElement.data() instanceof Node ) {

                //Node right = (Node) rightElement.data();

                //word += "1";

                //generateCodewords( right, word, codewords );

            //} else if ( rightElement.data() instanceof Integer ) {

                //int c = (int) rightElement.data();
                //codewords[ c ] = word;

            //}

        //}

    }

}
