public class Pair<M, N> {
    private final M first;
    private final N second;

    public Pair(M first, N second) {
        this.first = first;
        this.second = second;
    }

    public M getFirst() {
        return this.first;
    }

    public N getSecond() {
        return this.second;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
