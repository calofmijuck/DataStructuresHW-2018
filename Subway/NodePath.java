public class NodePath implements Comparable<NodePath> {
    private Node data; // Node data;
    private NodePath prev; // previous node
    private Long length; // length to this Node
    private boolean visited;
    private int transfer; // number of transfer

    NodePath(Node data, NodePath prev, Long length) {
        this.data = data;
        this.prev = prev;
        this.length = length;
        this.transfer = 0;
    }

    NodePath(Node data, Long length) {
        this(data, null, length);
    }

    NodePath(Node data) {
        this(data, null, 0L);
    }

    public void setData(Node data) {
        this.data = data;
    }

    public Node getData() {
        return data;
    }

    public void setPrev(NodePath prev) {
        this.prev = prev;
    }

    public NodePath getPrev() {
        return prev;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getLength() {
        return length;
    }

    public void visited() {
        this.visited = true;
    }

    public void unvisited() {
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public int getTransfer() {
        return this.transfer;
    }

    @Override
    public int compareTo(NodePath o) {
        return this.getLength().compareTo(o.getLength());
    }

    public int hashCode() {
        return data.hashCode();
    }
}
