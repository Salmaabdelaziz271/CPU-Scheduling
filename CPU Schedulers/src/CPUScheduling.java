import java.util.ArrayList;
import java.util.List;

public abstract class CPUScheduling {
    List<Process> allProcesses = new ArrayList<>();
    List<ProcessInterval> finalProcesses = new ArrayList<>();
    public CPUScheduling(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
    }
     abstract void printExecutionOrder();
    public double calculateTurnaroundTime(Process process) {
        double turnaroundTime = process.finishTime - process.arrivalTime;
        return turnaroundTime;
    }

    public double calculateWaitingTime(Process process) {
        double waitingTime = calculateTurnaroundTime(process) - process.burstTime;
        return waitingTime;
    }

    public double calculateAverageTurnaroundTime() {
        double sum = 0;
        for(Process p : allProcesses) {
            sum += calculateTurnaroundTime(p);
        }
        double avg = sum / allProcesses.size();
        return avg;
    }

    public double calculateAverageWaitingTime() {
        double sum = 0;
        for(Process p : allProcesses) {
            sum += calculateWaitingTime(p);
        }
        double avg = sum / allProcesses.size();
        return avg;
    }
}
