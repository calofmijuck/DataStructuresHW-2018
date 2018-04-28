import java.util.Iterator;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre 와 Title 에 따라 내부적으로 정렬된 상태를
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	MyLinkedList<Genre> gnList; // List of Genres

    public MovieDB() {
        gnList = new MyLinkedList<Genre>(); // Initialize
    }

    public void insert(MovieDBItem item) {
    	// Insert the given item, at the correct place
		// First determine the genre
		Genre obj = new Genre(item.getGenre());
		Iterator<Genre> it = gnList.iterator();
		Genre curr;
		boolean exists = false; // checks for existing genre
		int index = 0;
		while(it.hasNext()) {
			curr = it.next();
			int cmp = curr.compareTo(obj);
			if(cmp > 0) {
				break;
			} else if(cmp < 0) {
				index++;
			} else { // same Genre exists
				exists = true;
				break;
			}
		}
		if(!exists) { // When it is a new Genre, add Genre
			gnList.add(index, obj);
		}
		// now determine the title
		curr = gnList.get(index);
		MyLinkedList<String> str = curr.getMvList().getList();
		Iterator<String> it_str = str.iterator();
		String curr_str, title = item.getTitle();
		exists = false; // checks for existing title
		index = 0;
		while(it_str.hasNext()) {
			curr_str = it_str.next();
			int cmp = curr_str.compareTo(title);
			if(cmp > 0) {
				break;
			} else if(cmp < 0) {
				index++;
			} else { // same title exists
				exists = true;
				break;
			}
		}
		if(!exists) { // When it is a new Title, add Title
			str.add(index, title);
		}
    }

    public void delete(MovieDBItem item) {
    	// deletes an item from the DB
		// first search for matching genre
		Iterator<Genre> it = gnList.iterator();
		Genre curr = null;
		while(it.hasNext()) {
			curr = it.next();
			if(curr.getItem().equals(item.getGenre())) {
				// find the wanted Genre
				break;
			}
		}
		MovieList ml;
		if(curr != null) {
			ml = curr.getMvList();
		} else { // the genre was not found
			return; // don't have to delete
		}
		// then search for matching title
		Iterator<String> it_ml = ml.iterator();
		String title;
		while(it_ml.hasNext()) {
			title = it_ml.next();
			if(title.equals(item.getTitle())) {
				// match found, then remove
				it_ml.remove();
				break;
			}
		}
		// If there are no more movies in the same genre, remove the genre too.
		if(ml.getList().size() == 0) {
			it.remove();
		}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
    	// searches for movies that include the given term
		MyLinkedList<MovieDBItem> res = new MyLinkedList<MovieDBItem>(); // place the results

		// First search for the Genre
		Iterator<Genre> it_gen = gnList.iterator();
		// placeholders
		Genre curr_gen;
		MovieList ml_curr;
		String curr_title;
		MovieDBItem item;

		// Iterate for each Genres
		while(it_gen.hasNext()) {
			curr_gen = it_gen.next();
			ml_curr = curr_gen.getMvList();
			// For each Genres, iterate for all movie titles
			Iterator<String> it_ml = ml_curr.iterator();
			while(it_ml.hasNext()) {
				curr_title = it_ml.next();
				if(curr_title.contains(term)) {
					// if the title contains given search term, add
					item = new MovieDBItem(curr_gen.getItem(), curr_title);
					res.add(item);
				}
			}
		}
		if(gnList.isEmpty()) {
			// when searching in an empty DB, return empty list.
			return res;
		}
		return res; // return the list
    }
    
    public MyLinkedList<MovieDBItem> items() {
		// Used for printing items
		MyLinkedList<MovieDBItem> res = new MyLinkedList<MovieDBItem>(); // result
		// First iterate through Genre
		Iterator<Genre> it_gen = gnList.iterator();
		Genre curr_gen;
		// MovieList ml_curr = null;
		MyLinkedList<String> string_list;
		String curr_title;
		MovieDBItem item;
		while(it_gen.hasNext()) {
			curr_gen = it_gen.next();
			string_list = curr_gen.getMvList().getList();
			Iterator<String> it_ml = string_list.iterator();
			// Iterator through movie titles
			while(it_ml.hasNext()) {
				curr_title = it_ml.next();
				item = new MovieDBItem(curr_gen.getItem(), curr_title);
				res.add(item); // add the movie item
			}
		}
		if(gnList.isEmpty()) {
			// when searching in an empty DB, return empty list.
			return res;
		}
		return res; // return the list
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	private MovieList mvList; // list of movies with same genre

	public Genre(String name) {
		super(name);
		mvList = new MovieList(); // initialize
	}
	
	@Override
	public int compareTo(Genre o) { // compares two Genres
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
		return this.getItem().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Genre) { // check if is the same type of object
			Genre obj2 = (Genre) obj;
			return this.getItem().equals(obj2.getItem());
		} else {
			return false;
		}
	}

	public MovieList getMvList() {
		return mvList; // returns mvList
	}
}

class MovieList implements ListInterface<String> {
	private MyLinkedList<String> list; // list of movie titles

	public MovieList() {
		list = new MyLinkedList<String>(); // Initialize
	}

	@Override
	public Iterator<String> iterator() {
		return list.iterator(); // returns iterator
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void add(String item) {
		// Adds items in sorted order
		int index = 0; // To track the index to add at
		String curr;
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			curr = it.next();
			int cmp = curr.compareTo(item); // compare
			if(cmp > 0) {
				break;
			} else if(cmp < 0) {
				index++;
			} else { // same element, don't add
				return;
			}
		} // correct index has been found
		list.add(index, item); // add at the index
	}

	@Override
	public String first() {
		return list.first();
	}

	@Override
	public void removeAll() {
		list.removeAll();
	}

	public MyLinkedList<String> getList() {
		return list; // returns list
	}
}