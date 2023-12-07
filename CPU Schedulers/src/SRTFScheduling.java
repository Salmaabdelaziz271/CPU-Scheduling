import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling implements CPUScheduling{
    List<Process> allProcesses = new ArrayList<>();

    public SRTFScheduling(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
    }
    @Override
    public void printExecutionOrder() {

    }
    @Override
    public double calculateTurnaroundTime(Process process) {
        double turnaroundTime = process.finishTime - process.arrivalTime;
        return turnaroundTime;
    }
    @Override
    public double calculateWaitingTime(Process process) {
        double waitingTime = calculateTurnaroundTime(process) - process.burstTime;
        return waitingTime;
    }
    @Override
    public double calculateAverageTurnaroundTime(List<Process> processes) {
        double sum = 0;
        for(Process p : processes) {
            sum += calculateTurnaroundTime(p);
        }
        double avg = sum / processes.size();
        return avg;
    }
    @Override
    public double calculateAverageWaitingTime(List<Process> processes) {
        double sum = 0;
        for(Process p : processes) {
            sum += calculateWaitingTime(p);
        }
        double avg = sum / processes.size();
        return avg;
    }


}
