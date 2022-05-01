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

        decode( filenameCompressed, filenameDecompressed );

    }

    private static int[] decode( String filenameCompressed, String filenameDecompressed ) {

        int[] frequencies = new int[ 256 ];

        try {

            File compressed = new File( filenameCompressed );
            File decompressed = new File( filenameDecompressed );

            FileInputStream fiStream = new FileInputStream( compressed );
            BitInputStream biStream = new BitInputStream( fiStream );

            FileOutputStream foStream = new FileOutputStream( decompressed );


            // 1. Construct the frequency table from the start of the compressed file.

            for ( int i = 0; i < frequencies.length; i++ ) {

                int frequency = biStream.readInt();
                frequencies[ i ] = frequency;

            }
            
            // 2. Construct the Huffman tree from the frequency table.

            Huffman ht = new Huffman();
            ht.constructTree( frequencies );

            // 3. Decode the file by descending the tree with each bit in the compressed file.

            Element currentNode = ht.root();

            int b = 0;
            while ( b != -1 ) {

                b = biStream.readBit();

                switch ( b ) {

                    // A left node is found.
                    case 0:

                        if ( currentNode.data() instanceof Node )
                            currentNode = (Element) ( (Node) currentNode.data() ).left();

                        if ( currentNode.data() instanceof Character ) {

                            char c = (char) currentNode.data();
                            foStream.write( c );
                            currentNode = ht.root();

                        }

                        break;

                    // A right node is found.
                    case 1:

                        if ( currentNode.data() instanceof Node )
                            currentNode = (Element) ( (Node) currentNode.data() ).right();

                        if ( currentNode.data() instanceof Character ) {

                            char c = (char) currentNode.data();
                            foStream.write( c );
                            currentNode = ht.root();

                        }

                        break;

                }

            }

            biStream.close();
            foStream.close();

        } catch ( IOException e ) {
            
            e.printStackTrace();

        }

        return frequencies;

    }

}

