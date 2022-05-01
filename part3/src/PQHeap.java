
import java.util.ArrayList;

public class PQHeap implements PQ {

    private ArrayList<Element> queue;

    /**
     * Returns a new priority queue.
     */
    public PQHeap() {

        queue = new ArrayList<>();

    }

    /**
     * Returns the index of the left child of the node at the given index.
     */
    private int leftIdx( int i ) {

        return 2 * i + 1;

    }

    /**
     * Returns the index of the right child of the node at the given index.
     */
    private int rightIdx( int i ) {

        return 2 * i + 2;

    }

    /**
     * Returns the index of the parent to the node at the given index.
     */
    private int parentIdx( int i ) {

        return ( i - 1 ) / 2;

    }

    /**
     * Returns whether the parent is greater than the node at the given index.
     * Pre: the given index cannot be greater than the length of the queue.
     */
    private boolean parentGreater( int i ) {

        return queue.get( parentIdx( i ) ).key() >
            queue.get( i ).key();

    }

    /**
     * Exchanges the two given elements.
     * Pre: the given indeces cannot be greater than the length of the queue.
     */
    private void exchange( int i, int j ) {

        Element tmpE = queue.get( i );
        queue.set( i, queue.get( j ) );
        queue.set( j, tmpE );

    }

    /**
     * Inserts the given element at its proper place in the priority queue.
     */
    private void minHeapInsert( Element e ) {

        queue.add( e );

        int i = queue.size() - 1;
        while ( !this.isEmpty() && parentGreater( i ) ) {

            exchange( i, parentIdx( i ) );
            i = parentIdx( i );

        }

    }

    /**
     * Structure the piority queue correctly.
     */
    private void minHeapify( int i ) {

        int l = leftIdx( i );
        int r = rightIdx( i );

        int smallest;

        if ( l < queue.size() 
                && queue.get( l ).key() <= queue.get( i ).key() )
            smallest = l;
        else 
            smallest = i;

        if ( r < queue.size() 
                && queue.get( r ).key() <= queue.get( smallest ).key() )
            smallest = r;

        if ( smallest != i ) {

            exchange( i, smallest );
            minHeapify( smallest );

        }

    }

    /**
     * Returns whether the queue is empty or not.
     */
    public boolean isEmpty() {

        return ( queue.size() == 0 );

    }

    /**
     * Inserts the given element into the priority queue.
     */
    public void insert( Element e ) {

        minHeapInsert( e );

    }

    /**
     * Removes the element of least priority and returns it.
     * Pre: the priority queue cannot be empty.
     */
    public Element extractMin() {

        Element min;

        if ( queue.size() > 1 ) {

            min = queue.get( 0 );
            queue.set( 0, queue.remove( queue.size() - 1 ) );
            minHeapify( 0 );

        } else min = queue.remove( 0 );

        return min;

    }

    /**
     * Returns the size of the queue.
     */
    public int size() {
        return queue.size();
    }

    /**
     * Returns a textual representation of the priority queue.
     */
    public String toString() {
        return ( queue.size() > 0 ? subtreeString( 0, 0, false ) : "" );
    }

    /**
     * Returns a textual representation of the subtree at the given index.
     */
    public String subtreeString( int index, int indent, boolean isLeftChild ) {

        String leftOrRight = isLeftChild ? "l" : "r";

        String indentation = "";
        for ( int i = 0; i < indent; i++ )
            indentation += "|   ";

        Element e = queue.get( index );

        String subtree = indentation + leftOrRight + ": ( key: " + e.key() + ", data: " + e.data() + " )\n";

        if ( leftIdx( index ) < queue.size() )
            subtree += subtreeString( leftIdx( index ), indent + 1, true );

        if ( rightIdx( index ) < queue.size() )
            subtree += subtreeString( rightIdx( index ), indent + 1, false );

        return subtree;

    }

}
