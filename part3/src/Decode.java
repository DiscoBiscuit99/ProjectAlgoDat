/*
 * Bastian Blohm (bablo21@student.sdu.dk)
 * Ian Andersen (iaand21@student.sdu.dk)
 * Valdemar Lorenzen (valor21@student.sdu.dk)
 *
 * Aflevering af delprojekt 3 - Algoritmer og Datastrukturer.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decode {

    public static void main( String[] args ) {

        if ( args.length < 2 ) {

            System.out.println( "The name of the file to decompressed and the new decompressed file must be given..." );
            System.exit( 1 );

        }

        String filenameCompressed = args[ 0 ];
        String filenameDecompressed = args[ 1 ];

        decompress( filenameCompressed, filenameDecompressed );

    }

    /**
     * Decompresses the given (compressed) file.
     */
    private static void decompress( String filenameCompressed, String filenameDecompressed ) {

        try {

            // Construct the relevant streams.

            File compressed = new File( filenameCompressed );
            File decompressed = new File( filenameDecompressed );

            FileInputStream fiStream = new FileInputStream( compressed );
            BitInputStream biStream = new BitInputStream( fiStream );

            FileOutputStream foStream = new FileOutputStream( decompressed );

            // Construct the frequency table from the start of the compressed file.

            int[] frequencies = constructFreqTable( biStream );
            
            // Construct the Huffman tree from the frequency table.

            Huffman ht = new Huffman();
            ht.constructTree( frequencies );

            // Decode the file.

            decode( ht.root(), biStream, foStream );

            // Close the streams.

            biStream.close();
            foStream.close();

        } catch ( IOException e ) {
            
            e.printStackTrace();

        }

    }

    /**
     * Takes a BitInputStream and copies the next 256 integers into a frequency list.
     * 
     * Post: close the stream.
     *
     * @return  An integer array of frequencies.
     */
    private static int[] constructFreqTable( BitInputStream biStream ) {

        int[] frequencies = new int[ 256 ];

        for ( int f : frequencies ) f = 0; // Make sure no noise exists in the array.

        try {

            // Copy the next 256 integers into frequencies.

            for ( int i = 0; i < frequencies.length; i++ ) {

                int frequency = biStream.readInt();
                frequencies[ i ] = frequency;

            }

        } catch ( IOException e ) {

            e.printStackTrace();

        }

        return frequencies;

    }

    /**
     * Takes a node, a BitInputStream, and a FileOutputStream and decodes the file by 
     * descending the tree with each bit in the compressed file.
     *
     * Post: close the streams.
     */
    private static void decode( Element root, BitInputStream biStream, FileOutputStream foStream ) {

        try {

            Element currentNode = root;

            int b = 0;
            while ( b != -1 ) {

                b = biStream.readBit();

                switch ( b ) {

                    // A left node is found.
                    case 0:

                        // 1. Descend to the left.

                        if ( currentNode.data() instanceof Node )
                            currentNode = (Element) ( (Node) currentNode.data() ).left();

                        // 2. Check whether the child node is a leaf and act accordingly.

                        if ( currentNode.data() instanceof Character ) {

                            char c = (char) currentNode.data();
                            foStream.write( c );
                            currentNode = root;

                        }

                        break;

                    // A right node is found.
                    case 1:

                        // 1. Descend to the right.

                        if ( currentNode.data() instanceof Node )
                            currentNode = (Element) ( (Node) currentNode.data() ).right();

                        // 2. Check whether the child node is a leaf and act accordingly.

                        if ( currentNode.data() instanceof Character ) {

                            char c = (char) currentNode.data();
                            foStream.write( c );
                            currentNode = root;

                        }

                        break;

                }

            }

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

}

