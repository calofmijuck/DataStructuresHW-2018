
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements ListInterface<T> {
	// dummy head
	Node<T> head;
	int numItems;

	public MyLinkedList() {
		head = new Node<T>(null);
	}

    public final Iterator<T> iterator() {
    	return new MyLinkedListIterator<T>(this);
    }

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) { // adds items in unsorted order
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public void removeAll() {
		head.setNext(null);
		numItems = 0;
	}

	public T get(int index) { // gets the item at the index
		try {
			T res;
			Node<T> curr = head;
			for (int i = 0; i <= index; i++) {
				curr = curr.getNext();
			}
			res = curr.getItem();
			return res;
		} catch (Exception e) {
			// IndexOutOfBoundsException, NullPointerException
			// Will try not to call out of index bounds
			return null;
		}
	}

	public void add(int index, T item) { // adds item at given index
		Node<T> last = head;
		for(int i = 0; i < index; i++) {
			last = last.getNext();
		}
		if(last.hasNext()) { // adding in between elements
			Node<T> back = last.getNext();
			last.insertNext(item);
			last.getNext().setNext(back);
		} else { // adding at the end
			last.insertNext(item);
		}
		numItems += 1; // increment length
	}
}

class MyLinkedListIterator<T> implements Iterator<T> {
	// FIXME implement this
	// Implement the iterator for MyLinkedList.
	// You have to maintain the current position of the iterator.
	private MyLinkedList<T> list;
	private Node<T> curr;
	private Node<T> prev;

	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();
		prev = curr;
		curr = curr.getNext();
		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}