
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Encode {

    private static int[] frequencies = new int[ 256 ];
    private static PQHeap queue = new PQHeap();
    private static HuffmanTree tree = new HuffmanTree( queue );

    public static void main( String[] args ) {

        scanInputFile( args[0] );

        huffman();

    }

    /**
     * Given a filename, scans the text and constructs a frequency table.
     */
    private static void scanInputFile( String fileName ) {

        try {

            // create a reader
            FileInputStream stream = new FileInputStream( new File( fileName ) );

            // read one byte at a time
            int c;
            while ( ( c = stream.read() ) != -1 )
                frequencies[ c ] += 1;

            // close the reader
            stream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

    /**
     * 
     */
    private static void huffman() {

        for ( int i = 0; i < frequencies.length; i++ )
            queue.insert( new Element( frequencies[ i ], i ) );

        while ( queue.size() > 1 ) {

            // TODO: merge the two minimum nodes.

            tree.mergeMin();

        }

    }

}

