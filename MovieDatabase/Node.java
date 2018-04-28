public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }
    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) {
        this.next = new Node<T>(obj); // create New node
    }
    
    public final void removeNext() {
        try {
            this.next = this.next.next; // link to the next node
        } catch (Exception e) {
            // No next Element
        }
    }

    public boolean hasNext() { // Checks if this node has a next node
        return next != null;
    }
}