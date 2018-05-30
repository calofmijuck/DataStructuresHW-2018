import java.util.*;

public class Graph {
    private HashMap<String, Node> nodes; // key : id, value : node
    private HashMap<String, LinkedList<String>> names; // key: name, value: list that contains id of nodes with name

    Graph() {
        nodes = new HashMap<>(10000, 0.75f);
        names = new HashMap<>(10000, 0.75f);
    } // initCap... :thinking:

    // add Node
    public void addNode(String id, String name, String line) {
        Node newNode = new Node(id, name, line);
        nodes.put(id, newNode);
        if(!names.containsKey(name)) { // if this is a new Node
            LinkedList<String> list = new LinkedList<>();
            list.add(id);
            names.put(name, list);
        } else { // this node is transferable
            LinkedList<String> list =  names.get(name);
            for(String str: list) { // iterate through all Nodes and add edges with weight 5
                Node original = nodes.get(str);
                addEdge(id, original.getId(), 5);
                addEdge(original.getId(), id, 5);
            }
            list.add(id); // add current Node's id to the list
            names.put(name, list); // update the list
        }
    }

    public void addEdge(String srcId, String destId, int weight) {
        Node srcNode = nodes.get(srcId); // get source node
        srcNode.addEdge(new Edge(destId, weight)); // add edge from source to destination with weight
    }

    // print graph's adjacency list of each node:
    public void print() {
        Iterator it = nodes.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Node curr = (Node) pair.getValue();
            PriorityQueue<Edge> list = curr.getAdjList();
            System.out.print("Node: " + curr.getId() + " " + curr.getName() + " " + curr.getLine() + ", ");
            Iterator iter = list.iterator();
            while(iter.hasNext()) {
                Edge e = (Edge) iter.next();
                System.out.print("(" + e.getDestId() + ", " + e.getWeight() + ") ");
            }
            System.out.println();
        }
    }
}