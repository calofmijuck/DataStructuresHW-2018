import java.io.*;
import java.util.*;

public class Subway {
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws Exception {
        BufferedReader fr = new BufferedReader(new FileReader(args[0]));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // process file into graph data structure

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

    }
}