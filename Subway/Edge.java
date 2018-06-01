public class Edge implements Comparable<Edge> { // Edge class
    private String destId; // destination node
    private Long weight; // weight

    Edge(String id, Long weight) {
        this.destId = id;
        this.weight = weight;
    }

    public void setDestId(String id) {
        this.destId = id;
    }

    public String getDestId() {
        return this.destId;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(Edge o) { // compares the weight of edges
        return Long.compare(this.getWeight(), o.getWeight());
    }
}
