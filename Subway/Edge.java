public class Edge implements Comparable<Edge> { // Edge class
    private String destId; // destination node
    private int weight; // weight

    Edge(String id, int weight) {
        this.destId = id;
        this.weight = weight;
    }

    public void setDestId(String id) {
        this.destId = id;
    }

    public String getDestId() {
        return this.destId;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(Edge o) { // compares the weight of edges
        return Integer.compare(this.getWeight(), o.getWeight());
    }
}
