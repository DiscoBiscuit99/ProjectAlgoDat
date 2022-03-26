import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PQHeap pq = new PQHeap();

        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            pq.insert(new Element(nextInt, "data: " + nextInt));
        }

        //pq.insert(new Element(0, "data1"));
        //pq.insert(new Element(-1, "data2"));
        //pq.insert(new Element(1, "data3"));
        //pq.insert(new Element(-2, "data4"));
        //pq.insert(new Element(3, "data5"));
        //pq.insert(new Element(0, "data?"));
        System.out.println(pq.extractMin().getKey());
        System.out.println("---------");
        System.out.println(pq);

        //pq.extractMin();

        //System.out.println(pq);
    }
}

