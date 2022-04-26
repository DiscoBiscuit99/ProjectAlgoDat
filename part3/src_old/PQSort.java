import java.util.Scanner;

public class PQSort { 

    public static void main( String[] args ) {

        PQ pq = new PQHeap();
        int n = 0;
        Scanner sc = new Scanner( System.in );

        while ( sc.hasNextInt() ) {

            pq.insert( new Element( sc.nextInt(), null ) );
            n++;

        }

        System.out.println();

        while ( n > 0 ) {

            System.out.println( pq.extractMin().key() );
            n--;

        }

    }

}

