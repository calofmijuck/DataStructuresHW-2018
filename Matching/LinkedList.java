import java.util.Iterator;

public class LinkedList<T> implements Iterable { // LinkedList with Iterator
    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private Node<T> find(int index) {
        // return reference to the Node at the index
        Node<T> curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr;
    }

    public void add(T item) {
        add(this.size(), item);
    }

    public void add(int index, T item) throws IndexOutOfBoundsException {
        if(index < 0 && index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            if(index == 0) {
                head = new Node<T>(item, head);
            } else {
                Node<T> prev = find(index - 1);
                Node<T> newNode = new Node<T>(item, prev.getNext());
                prev.setNext(newNode);
            }
            size++;
        }
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        if(index < 0 && index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            if(index == 0) {
                head = head.getNext();
            } else {
                Node<T> prev = find(index - 1);
                Node<T> curr = prev.getNext();
                prev.setNext(curr.getNext());
            }
            size--;
        }
    }

    public T removeLast() {
        T res;
        Node<T> prev = find(this.size - 1);
        res = prev.getNext().getItem();
        prev.setNext(null);
        return res;
    }

    public T get(int index) {
        if(index >= 0 && index <= size && !isEmpty()) {
            Node<T> curr = find(index);
            return curr.getItem();
        } else {
            return null;
        }
    }

    public void removeAll() {
        head = null;
        size = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> curr = head;
        for(int i = 0; i < size - 1; i++) {
            sb.append(curr.getItem());
            sb.append(" ");
            curr = curr.getNext();
        }
        sb.append(curr.getItem());
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> curr = head;
            private int nextIndex = 0;

            @Override
            public boolean hasNext() {
                return nextIndex < size;
            }

            @Override
            public T next() {
                T res = curr.getItem();
                nextIndex++;
                curr = curr.getNext();
                return res;
            }
        };
    }
}
