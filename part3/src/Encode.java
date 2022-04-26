import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encode {

    public static void main( String[] args ) {

        // Make sure two filenames are given.
        if ( args.length < 2 ) {

            System.out.println( "The name of the file to encode and the new compressed file must be given..." );
            System.exit( 1 );

        }

        int[] frequencies = constructFreqTable( args[0] );

        for ( int i = 0; i < frequencies.length; i++ )
            System.out.print( "[" + (char) i + ": " + frequencies[i] + "]   " );

        HuffmanTree huffman = huffman( frequencies );

        // TESTING STUFF //

        Node root = huffman.root();

        System.out.println();
        System.out.println();
        System.out.println( "root frequency: " + root.key() );

        int total = 0;
        for ( int i = 0; i < frequencies.length; i++ )
            total += frequencies[i];

        System.out.println( "total frequencies: " + total );

        String[] codewords = generateCodewords( huffman );

        for ( int c = 0; c < codewords.length; c++ )
            System.out.println( (char) c + ": " + codewords[ c ] );

        // TODO: write the frequencies table to the compressed file,
        //       scan the input file again and write the codeword for any given character to the file.

        writeCompression( frequencies, codewords, args[ 0 ], args[ 1 ] );
        
    }

    /**
     *
     */
    private static void writeCompression( int[] frequencies, String[] codewords, String filenameOriginal, String filenameCompressed ) {

        try {

            File compressedFile = new File( filenameCompressed );
            FileOutputStream oStream = new FileOutputStream( compressedFile );
            BitOutputStream bStream = new BitOutputStream( oStream );

            for ( int f : frequencies )
                bStream.writeInt( f );

            File original = new File( filenameOriginal );
            FileInputStream iStream = new FileInputStream( original );

            int c;
            while ( ( c = iStream.read() ) != -1 ) {

                String[] splitWord = codewords[ c ].split( "" );

                for ( String b : splitWord )
                    bStream.writeBit( Integer.parseInt( b ) );

            }

            iStream.close();
            bStream.close();

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
    private static HuffmanTree huffman( int[] frequencies ) {

        // 1.) Construct the priority queue.

        PQHeap huffmanQueue = new PQHeap();

        for ( int i = 0; i < frequencies.length; i++ )
            huffmanQueue.insert( new Element( frequencies[i], i ) );

        // 2.) Construct the huffman nodes from the priority queue.

        while ( huffmanQueue.size() > 1 ) {

            Element x = huffmanQueue.extractMin();
            Element y = huffmanQueue.extractMin();

            int key = x.key() + y.key();

            Node node = new Node( key, x, y );

            Element nodeWrapped = new Element( node.key(), node );

            huffmanQueue.insert( nodeWrapped );

        }

        // 3.) Construct the Huffman tree.

        Element rootWrapped = huffmanQueue.extractMin();

        Node root = (Node) rootWrapped.data();

        HuffmanTree huffman = new HuffmanTree( root );

        return huffman;

    }

    /**
     * Returns an integer array of codewords generated from the given Huffman tree.
     * Each index corresponds to a character.
     *
     * @param   huffman The Huffman tree to generate the codewords from.
     *
     * @return          An integer array of the generated codewords.
     */
    private static String[] generateCodewords( HuffmanTree huffman ) {

        // Traverse the tree and generate the codewords.

        String[] codewords = new String[ 256 ];

        generateCodewords( huffman.root(), "", codewords );

        return codewords;

    }

    /**
     * Auxiliary function... TODO: write the doc.
     */
    private static void generateCodewords( Node node, String word, String[] codewords ) {

        if ( node.left() != null ) {

            Element leftElement = (Element) ( (Node) node ).left();

            if ( leftElement.data() instanceof Node ) {

                Node left = (Node) leftElement.data();

                word += "0";

                generateCodewords( left, word, codewords );

            } else if ( leftElement.data() instanceof Integer ) {

                int c = (int) leftElement.data();
                codewords[ c ] = word;

            }

        }

        if ( node.right() != null ) {

            Element rightElement = (Element) node.right();

            if ( rightElement.data() instanceof Node ) {

                Node right = (Node) rightElement.data();

                word += "1";

                generateCodewords( right, word, codewords );

            } else if ( rightElement.data() instanceof Integer ) {

                int c = (int) rightElement.data();
                codewords[ c ] = word;

            }

        }

    }

}
