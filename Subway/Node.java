import java.util.PriorityQueue;

public class Node {
    private String id; // id of Node
    private String name; // name of Node
    private String line; // line number

    // Not sure if this has to be a priority queue...
    private PriorityQueue<Edge> adjList; // adjacency list of edges, sorted by weight

    Node(String id, String name, String line) {
        this.id = id;
        this.name = name;
        this.line = line;
        adjList = new PriorityQueue<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void addEdge(String destId, Long weight) {
        adjList.add(new Edge(destId, weight));
    }

    public void addEdge(Edge e) {
        adjList.add(e);
    }

    public PriorityQueue<Edge> getAdjList() {
        return adjList;
    }

    public int hashCode() {
        return id.hashCode();
    }
}
