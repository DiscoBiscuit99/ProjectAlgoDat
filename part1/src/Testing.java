import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PQHeap pq = new PQHeap();

        pq.insert( new Element( 10, "data: 10" ) );
        pq.insert( new Element( 5, "data: 5" ) );
        pq.insert( new Element( 2, "data: 2" ) );

        System.out.println( pq );

        //while (scanner.hasNextInt()) {
            //int nextInt = scanner.nextInt();
            //pq.insert(new Element(nextInt, "data: " + nextInt));
        //}

        //if (!pq.isEmpty()) {
            //System.out.println("---------");
            //System.out.println(pq);

            //System.out.println("Extracting minimum value:");
            //System.out.println(pq.extractMin());
            //System.out.println("---------");
            //System.out.println("New PQ:");
            //System.out.println(pq);
        //}

        //while (!pq.isEmpty())
            //System.out.println(pq.extractMin().getData());
    }
}

