public class Testing {
    public static void main(String[] args) {
        PQHeap pq = new PQHeap();

        //pq.insert(new Element(0, "data1"));
        pq.insert(new Element(-1, "data2"));
        pq.insert(new Element(1, "data3"));
        pq.insert(new Element(-2, "data4"));
        pq.insert(new Element(3, "data5"));
        pq.insert(new Element(0, "data?"));

        System.out.println(pq);

        //pq.extractMin();

        //System.out.println(pq);
    }
}

