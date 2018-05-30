public class GraphTest {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addNode("810", "암사", "8");
        graph.addNode("811", "천호", "8");
        graph.addNode("820", "복정", "8");
        graph.addEdge("810", "811", 2);
        graph.addEdge("811", "810", 2);
        graph.addEdge("820", "811", 10);
        graph.addEdge("811", "820", 10);
        graph.addNode("120", "암사", "1");
        graph.addNode("121", "AA", "1");
        graph.addEdge("121", "120", 1000);
        graph.addEdge("120", "121", 1001);
        graph.addNode("220", "암사", "2");
        graph.print();
    }
}