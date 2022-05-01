import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encode {

    private static int[] frequencies = new int[ 256 ];

    public static void main( String[] args ) {

        // Make sure two file-names are given.
        if ( args.length < 2 ) {

            System.out.println( "The name of the file to compress and the new compressed file must be given..." );
            System.exit( 1 );

        }

        String filenameOriginal = args[ 0 ];
        String filenameCompressed = args[ 1 ];

        constructFreqTable( filenameOriginal );

        Huffman ht = new Huffman( null );
        ht.constructTree( frequencies );
        String[] codewords = ht.constructCodes();

        writeCompressed( codewords, filenameOriginal, filenameCompressed );

    }

    /**
     * Given a file-name, construct the frequency table.
     */
    private static void constructFreqTable( String filename ) {

        for ( int f : frequencies ) f = 0;

        try {

            File originalFile = new File( filename );
            FileInputStream fiStream = new FileInputStream( originalFile );

            int c;
            while ( ( c = fiStream.read() ) != -1 )
                frequencies[ c ] += 1;

            fiStream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

    /**
     * Writes the frequency table and the compressed data to a file.
     */
    private static void writeCompressed( String[] codewords, String filenameOriginal, String filenameCompressed ) {

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

                for ( String b : splitWord )
                    boStream.writeBit( Integer.parseInt( b ) );

            }

            fiStream.close();
            boStream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

}

