import java.util.ArrayList;
import java.util.List;

public class SJFScheduling implements CPUScheduling{
    List<Process> allProcesses = new ArrayList<>();

    public SJFScheduling(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
    }

    @Override
    public void printExecutionOrder() {

    }

    @Override
    public double calculateTurnaroundTime(Process process) {
        return 0;
    }

    @Override
    public double calculateWaitingTime(Process process) {
        return 0;
    }

    @Override
    public double calculateAverageTurnaroundTime(List<Process> processes) {
        return 0;
    }

    @Override
    public double calculateAverageWaitingTime(List<Process> processes) {
        return 0;
    }
}
