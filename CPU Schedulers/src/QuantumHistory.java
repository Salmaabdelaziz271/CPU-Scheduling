import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantumHistory {
    int time;
    List<Double> quantum = new ArrayList<>();
    int ProcessesNo;
    String processName;

    public QuantumHistory (int time, List<Double> quantum, int num, String processName){
        this.time = time;
        this.quantum = quantum;
        ProcessesNo = num;
        this.processName = processName;
    }

    public void printQuantumHistory(){
        System.out.print("t = " + time + " Quantum (");
        for (int i = 0; i < ProcessesNo - 1; i++){
            System.out.print((int) Math.ceil(quantum.get(i)) + ",");
        }
        if (Objects.equals(processName, "")){
            System.out.print((int) Math.ceil(quantum.get(ProcessesNo - 1)) + ")");
        }
        else{
            System.out.print((int) Math.ceil(quantum.get(ProcessesNo - 1)) + ") >> " + processName + " running");
        }

        System.out.println();
    }

}
