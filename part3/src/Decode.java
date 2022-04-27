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
    
        //for ( int i = 0; i < frequencies.length; i++ )
            //System.out.println( (char) i + ": " + frequencies[i] );

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

            for ( i = 0; i < frequencies.length; i++ )
                System.out.println( (char) i + ": " + frequencies[ i ] );

            HuffmanTree huffman = huffman( frequencies );

            Node currentNode = huffman.root();
            i = 0;
            while ( i < total ) {

                //System.out.println( "i " + i + " out of " + total );

                int b = biStream.readBit();

                // Check which way to descend in the tree.
                if ( b == 0 ) {

                    if ( ( (Node) currentNode ).left() != null ) {

                        Element element = (Element) ( (Node) currentNode ).left();

                        if ( element.data() instanceof Node )
                            currentNode = (Node) element.data();

                        else if ( element.data() instanceof Integer ) {

                            int c = (int) element.data();

                            System.out.println( "Writing " + (char) c );

                            foStream.write( (char) c );

                            currentNode = huffman.root();

                            i++;

                        }

                    }


                } else {

                    if ( ( (Node) currentNode ).right() != null ) {

                        Element element = (Element) ( (Node) currentNode ).right();

                        if ( element.data() instanceof Node )
                            currentNode = (Node) element.data();

                        else if ( element.data() instanceof Integer ) {

                            int c = (Integer) element.data();

                            System.out.println( "Writing " + (char) c );
                            System.out.println();

                            foStream.write( (char) c );

                            currentNode = huffman.root();

                            i++;

                        }

                    }

                }

            }

            System.out.println( "total: " + total );

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

}

