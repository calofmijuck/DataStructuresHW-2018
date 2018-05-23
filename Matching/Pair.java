public class Pair<M extends Comparable<M>, N extends Comparable<N>> {
    private final M first;
    private final N second;
    private String data;

    public Pair(M first, N second, String data) {
        this.first = first;
        this.second = second;
        this.data = data;
    }

    public Pair(M first, N second) {
        this(first, second, "");
    }

    public M getFirst() {
        return this.first;
    }

    public N getSecond() {
        return this.second;
    }

    public String getData() {
        return this.data;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
