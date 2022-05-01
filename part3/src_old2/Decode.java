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

        int[] frequencies = decode( args[ 0 ], args[ 1 ] );

    }

    private static int[] decode( String filenameCompressed, String filenameDecompressed ) {

        int[] frequencies = new int[ 256 ];

        try {

            File compressed = new File( filenameCompressed );
            File decompressed = new File( filenameDecompressed );

            FileInputStream fiStream = new FileInputStream( compressed );
            BitInputStream biStream = new BitInputStream( fiStream );

            FileOutputStream foStream = new FileOutputStream( decompressed );

            int i = 0;
            int total = 0;
            while ( i < frequencies.length ) {

                int frequency = biStream.readInt();

                total += frequency;
                frequencies[ i ] = frequency;

                i++;

            }

            Element root = huffman( frequencies );
            Element currentNode = root;

            i = 0;
            while ( i < total ) {

                int b = biStream.readBit();

                // Check which way to descend in the tree.
                if ( b == 0 ) {

                    if ( currentNode.data() instanceof Node )
                        currentNode = (Element) ( (Node) currentNode.data() ).left();

                    if ( currentNode.data() instanceof Integer ) {

                        int c = (int) currentNode.data();

                        foStream.write( (char) c );
                        
                        currentNode = root;

                        i++;

                    }

                } else if ( b == 1 ) {

                    if ( currentNode.data() instanceof Node )
                        currentNode = (Element) ( (Node) currentNode.data() ).right();

                    if ( currentNode.data() instanceof Integer ) {

                        int c = (int) currentNode.data();

                        foStream.write( (char) c );

                        currentNode = root;

                        i++;

                    }

                } else {

                    break;

                }

            }

            biStream.close();

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

}

