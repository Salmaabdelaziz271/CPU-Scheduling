import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CPUScheduling s = new SRTFScheduling();
        List<Process>processes=new ArrayList<>();
        Process p1 = new Process("P1", "Red", 0, 7,2);
        Process p2 = new Process("P2", "Red", 2, 4,2);
        Process p3 = new Process("P3", "Red", 4, 1,2);
        Process p4 = new Process("P4", "Red", 5, 4,2);
        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);
        s.printExecutionOrder(processes);

    }
}