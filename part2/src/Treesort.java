/*
 * Bastian Blohm (bablo21@student.sdu.dk)
 * Ian Andersen (iaand21@student.sdu.dk)
 * Valdemar Lorenzen (valor21@student.sdu.dk)
 *
 * Aflevering af delprojekt 2 - Algoritmer og Datastrukturer.
 */

import java.util.Scanner;

public class Treesort {

    public static void main( String[] args ) {

        DictBinTree dict = new DictBinTree();
        Scanner scanner = new Scanner( System.in );

        while ( scanner.hasNextInt() )
            dict.insert( scanner.nextInt() );

        for ( int n : dict.orderedTraversal() )
            System.out.println( n );

        scanner.close();

    }

}

