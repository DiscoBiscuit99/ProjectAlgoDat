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

public class Encode {

    public static void main( String[] args ) {

        // Make sure two file-names are given.
        if ( args.length < 2 ) {

            System.out.println( "The name of the file to compress and the new compressed file must be given..." );
            System.exit( 1 );

        }

        String filenameOriginal = args[ 0 ];
        String filenameCompressed = args[ 1 ];

        int[] frequencies = constructFreqTable( filenameOriginal );

        Huffman ht = new Huffman();
        ht.constructTree( frequencies );
        String[] codewords = ht.constructCodes();

        writeCompressed( frequencies, codewords, filenameOriginal, filenameCompressed );

    }

    /**
     * Given a file-name, construct the frequency table.
     *
     * @return  An integer array containing the character frequencies.
     */
    private static int[] constructFreqTable( String filename ) {

        int[] frequencies = new int[ 256 ];

        for ( int f : frequencies ) f = 0; // Make sure no noise exists in the array.

        try {

            // Construct the relevant streams.

            File originalFile = new File( filename );
            FileInputStream fiStream = new FileInputStream( originalFile );

            // Read the characters and increment accordingly.

            int c;
            while ( ( c = fiStream.read() ) != -1 )
                frequencies[ c ] += 1;

            // Close the stream.

            fiStream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

        return frequencies;

    }

    /**
     * Writes the frequency table and the compressed data to a file.
     */
    private static void writeCompressed( int[] frequencies, String[] codewords, String filenameOriginal, String filenameCompressed ) {

        try {

            // Construct the relevant output streams and write each frequency to the top of the compressed file.

            File compressedFile = new File( filenameCompressed );
            FileOutputStream foStream = new FileOutputStream( compressedFile );
            BitOutputStream boStream = new BitOutputStream( foStream );

            for ( int f : frequencies )
                boStream.writeInt( f );

            // Construct the relevant input stream.

            File original = new File( filenameOriginal );
            FileInputStream fiStream = new FileInputStream( original );

            // For each character in the input file, write each bit of the codeword to the output file.

            int c;
            while ( ( c = fiStream.read() ) != -1 ) {

                String[] splitWord = codewords[ c ].split( "" );

                for ( String b : splitWord )
                    boStream.writeBit( Integer.parseInt( b ) );

            }

            // Close the streams.

            fiStream.close();
            boStream.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }

    }

}

