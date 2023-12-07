import java.util.List;

public interface CPUScheduling {
    void printExecutionOrder(List<Process> processes);
    double calculateTurnaroundTime(Process process);

    double calculateWaitingTime(Process process);
    double calculateAverageTurnaroundTime(List<Process> processes) ;

    double calculateAverageWaitingTime(List<Process> processes);
}
