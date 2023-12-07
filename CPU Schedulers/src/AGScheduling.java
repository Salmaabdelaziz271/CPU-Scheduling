import java.util.List;

public class AGScheduling implements CPUScheduling{
    @Override
    public void printExecutionOrder(List<Process> processes) {

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
