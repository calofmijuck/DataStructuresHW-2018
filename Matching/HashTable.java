import java.util.ArrayList;

public class HashTable<K, V> {
    private transient ArrayList<V> table; // data
    private int size;
    private double loadFactor;

    public HashTable(int initCap, double loadFactor) {
        if(initCap < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + initCap);
        }
        if(loadFactor <= 0) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.table = new ArrayList<>();
        for(int i = 0; i < initCap; ++i) {
            this.table.add(null);
        }
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public HashTable(int initCap) {
        this(initCap, 0.75);
    }

    // return the number of keys
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public V get(K key) {
        int hash = key.hashCode();
        return table.get(hash);
    }

    public void put(K key, V value) {
        if(value == null) {
            throw new NullPointerException();
        }
        int hash = key.hashCode();
        table.set(hash, value);
        ++size;
    }

    public void remove(K key) {
        int hash = key.hashCode();
        table.set(hash, null);
        --size;
    }

    public V at(int idx) {
        return table.get(idx);
    }
}
