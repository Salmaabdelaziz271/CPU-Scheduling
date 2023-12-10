import java.util.ArrayList;
import java.util.List;

public abstract class CPUScheduling {
    List<Process> allProcesses = new ArrayList<>();
    List<ProcessInterval> finalProcesses = new ArrayList<>();
    public CPUScheduling(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
    }
     abstract void printExecutionOrder();
    abstract double calculateTurnaroundTime(Process process);

    abstract double calculateWaitingTime(Process process);
    abstract double calculateAverageTurnaroundTime() ;

    abstract double calculateAverageWaitingTime();
}
