import java.io.*;
import java.util.*;

public class Subway {
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader fr;
    static Graph Subway;
    public static void main(String[] args) throws Exception {
        fr = new BufferedReader(new FileReader(args[0]));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // process file into graph data structure

        processFile();

        // input
        while(true) {
            try {
                String input = br.readLine();
                if(input.compareTo("QUIT") == 0) {
                    break;
                }
                command(input);
            } catch (IOException ioe) {
                System.out.print("Wrong Input Format: ");
                ioe.printStackTrace();
            }
        }
    }

    private static void command(String input) throws Exception {
        // Subway.print(); for testing
        String[] station = input.split(" "); // [0]: src, [1]: dest
        HashMap<Node, NodePath> map = null;
        if(station.length == 2) {
            map = dijkstra(station[0], station[1]);
        } else {
            map = dijkstraMin(station[0], station[1]);
        }

        LinkedList<String> destList = Subway.getNames().get(station[1]);
        Long res = Long.MAX_VALUE; // resulting min path length
        NodePath resNode = null;
        for (String str : destList) {
            Node dest = Subway.getNodes().get(str); // get destination node
            NodePath destNodePath = map.get(dest);
            if (destNodePath.getLength() < res) {
                resNode = destNodePath;
                res = destNodePath.getLength();
            }
        }
        // Ok
        LinkedList<String> minPath = new LinkedList<String>();
        while (resNode.getPrev() != null) {
            // check transfer by same Node name
            if (resNode.getPrev().getData().getName().equals(resNode.getData().getName())) {
                minPath.addFirst("[" + resNode.getData().getName() + "]");
                resNode = resNode.getPrev().getPrev();
            } else {
                minPath.addFirst(resNode.getData().getName());
                resNode = resNode.getPrev();
            }
        }
        minPath.addFirst(resNode.getData().getName());
        print(minPath);
        bw.write(res.toString());
        bw.write("\n");
        bw.flush();
    }

    private static HashMap<Node, NodePath> dijkstra(String src, String finDest) { // shortest path from src to dest
        // shortestPath stores the shortest path for each node
        HashMap<Node, NodePath> shortestPath = new HashMap<>(100000, 0.75f);

        // set of unvisited Nodes, min weight
        PriorityQueue<NodePath> unvisited = new PriorityQueue<>();
        LinkedList<String> srcList = Subway.getNames().get(src); // list of start nodes

        for(String node: srcList) { // Initialize starting nodes to distant 0
            Node n = Subway.getNodes().get(node);
            NodePath np = new NodePath(n, 0L);
            np.visited(); // mark starting node visited
            shortestPath.put(n, np); // add
            unvisited.add(np); // add to unvisited
        }

        while(!unvisited.isEmpty()) {
            NodePath np = unvisited.poll(); // choose a node with minimum path length
            np.visited(); // mark np visited
            for(Edge e: np.getData().getAdjList()) { //  for nodes adjacent to the starting node, for all edges
                Node dest = Subway.getNodes().get(e.getDestId()); // find the destination node
                if(shortestPath.containsKey(dest)) { // already computed value exists
                    NodePath currMin = shortestPath.get(dest); // get the node
                    if(currMin.getLength() > np.getLength() + e.getWeight()) { // if the current path is smaller
                        currMin.setLength(np.getLength() + e.getWeight()); // change the minimum path length
                        currMin.setPrev(np); // change the previous nodePath
                        shortestPath.put(dest, currMin); // update < -- necessary?
                        unvisited.add(currMin);
                    } // else don't do anything
                } else { // not a starting node - mark unvisited
                    NodePath newNode = new NodePath(dest, np, np.getLength() + e.getWeight()); // set new Node
                    shortestPath.put(dest, newNode); // put it to shortestPath
                    unvisited.add(newNode); // add to unvisited
                }
            }
            if(np.getData().getName().equals(finDest)){
                break;
            }
        }
        return shortestPath;
    }

    private static HashMap<Node, NodePath> dijkstraMin(String src, String finDest) { // dijkstra with minimum transfer
        // shortestPath stores the shortest path for each node
        HashMap<Node, NodePath> shortestPath = new HashMap<>(100000, 0.75f);

        // set of unvisited Nodes, min transfer. If same transfers, min weight
        PriorityQueue<NodePath> unvisited = new PriorityQueue<>(new Comparator<NodePath>() {
            @Override
            public int compare(NodePath o1, NodePath o2) {
                if(o1.getTransfer() < o2.getTransfer()) {
                    return -1;
                } else if(o1.getTransfer() > o2.getTransfer()) {
                    return 1;
                } else {
                    return Long.compare(o1.getLength(), o2.getLength());
                }
            }
        });
        LinkedList<String> srcList = Subway.getNames().get(src); // list of start nodes

        for(String node: srcList) { // Initialize starting nodes to distant 0
            Node n = Subway.getNodes().get(node);
            NodePath np = new NodePath(n, 0L);
            np.visited(); // mark starting node visited
            shortestPath.put(n, np); // add
            unvisited.add(np); // add to unvisited
        }

        while(!unvisited.isEmpty()) {
            NodePath np = unvisited.poll(); // choose a node with minimum path length
            np.visited(); // mark np visited
            for(Edge e: np.getData().getAdjList()) { //  for nodes adjacent to the starting node, for all edges
                Node dest = Subway.getNodes().get(e.getDestId()); // find the destination node
                if(shortestPath.containsKey(dest)) { // already computed value exists
                    NodePath currMin = shortestPath.get(dest); // get the node
                    int flag = 0;
                    if(np.getData().getName().equals(dest.getName())) { // same station; transfer attempted
                        flag = 1;
                    }
                        if(currMin.getTransfer() < np.getTransfer() + flag) { // not minimum transfer
                            // do nothing
                        } else if(currMin.getTransfer() == np.getTransfer() + flag) { // same number of transfer then compare weight
                            if(currMin.getLength() > np.getLength() + e.getWeight()) { // if the current path is smaller
                                currMin.setLength(np.getLength() + e.getWeight()); // change the minimum path length
                                currMin.setPrev(np); // change the previous nodePath
                                shortestPath.put(dest, currMin); // update < -- necessary?
                                unvisited.add(currMin);
                            } // else don't do anything
                        } else { // minimum transfer
                            currMin.setLength(np.getLength() + e.getWeight()); // change the minimum path length
                            currMin.setPrev(np);
                            currMin.setTransfer(np.getTransfer() + flag);
                            shortestPath.put(dest, currMin);
                            unvisited.add(currMin);
                        }
                } else { // not a starting node - mark unvisited
                    NodePath newNode = new NodePath(dest, np, np.getLength() + e.getWeight()); // set new Node
                    if(np.getData().getName().equals(dest.getName())) {
                        newNode.setTransfer(np.getTransfer() + 1);
                    } else {
                        newNode.setTransfer(np.getTransfer());
                    }
                    shortestPath.put(dest, newNode); // put it to shortestPath
                    unvisited.add(newNode); // add to unvisited
                }
            }
            if(np.getData().getName().equals(finDest)){
                break;
            }
        }
        return shortestPath;
    }

    private static void print(LinkedList<String> path) throws Exception {
        Iterator it = path.iterator();
        for(int i = 0; i < path.size() - 1; ++i) {
            bw.write((String) it.next());
            bw.write(" ");
            bw.flush();
        }
        bw.write((String) it.next());
        bw.write("\n");
        bw.flush();
    }

    private static void processFile() throws Exception {
        Subway = new Graph();
        String[] input;
        String stationInput;
        while(!(stationInput = fr.readLine()).equals("")) { // add nodes(station)
            input = stationInput.split(" ");
            Subway.addNode(input[0], input[1], input[2]);
        }
        String weightInput;
        while((weightInput = fr.readLine()) != null) { // assign weighted edges
            input = weightInput.split(" ");
            Subway.addEdge(input[0], input[1], Long.parseLong(input[2]));
        }
    }
}