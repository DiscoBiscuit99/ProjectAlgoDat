import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Encode {

    private static int[] frequencies = new int[ 256 ];

    public static void main( String[] args ) {

        // Make sure two filenames are given.
        if ( args.length < 2 ) {

            System.out.println( "The name of the file to encode and the new compressed file must be given..." );
            System.exit( 1 );

        }

        constructFreqTable( args[0] );

        for ( int i = 0; i < frequencies.length; i++ )
            System.out.print( "[" + (char) i + ": " + frequencies[i] + "]   " );

    }

    /**
     * Constructs the frequency table from the given file(name).
     *
     * @param   filename    The name of the file to compress.
     */
    private static void constructFreqTable( String filename ) {

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

    }

}
