public class Pair<M extends Comparable<M>, N extends Comparable<N>> {
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

    public Pair<Integer, Integer> compareTo(Pair<M, N> o) {
        return new Pair<Integer, Integer>(this.getFirst().compareTo(o.getFirst()), this.getSecond().compareTo(o.getSecond()));
    }

    public boolean equals(Pair<M, N> o) {
        return (this.first.equals(o.getFirst())) && (this.second.equals(o.getSecond()));
    }
}
