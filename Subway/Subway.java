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

    private static void command(String input) {
        // Subway.print(); for testing
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
            Subway.addEdge(input[0], input[1], Integer.parseInt(input[2]));
        }
    }
}
