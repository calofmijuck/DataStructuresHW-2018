import java.util.Comparator;
import java.util.PriorityQueue;

public class Heap {
    public static void main(String[] args) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(10);
        heap.add(9);
        heap.add(1);
        heap.add(111);
        heap.add(144);
        heap.add(-1);
        heap.add(-124);
        heap.add(0);
        System.out.println(heap.toString());
    }
}
