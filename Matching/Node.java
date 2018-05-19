public class Node<T> { // Node for LinkedList
    private T item;
    private Node<T> next;

    public Node() {
        item = null;
        next = null;
    }

    public Node(T item) {
        this.item = item;
        next = null;
    }

    public Node(T item, Node<T> next) {
        this.item = item;
        this.next = next;
    }

    public T getItem() {
        return item;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
