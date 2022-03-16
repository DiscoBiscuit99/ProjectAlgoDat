public class Testing {
    public static void main(String[] args) {
        PQHeap pq = new PQHeap();

        pq.insert(0, "data1");
        pq.insert(1, "data2");
        pq.insert(-1, "data3");

        System.out.println(pq);
    }
}

