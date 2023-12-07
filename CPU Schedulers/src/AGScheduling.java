import java.util.ArrayList;
import java.util.List;

public class AGScheduling extends CPUScheduling{

    public AGScheduling(List<Process> allProcesses) {
        super(allProcesses);
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
    public double calculateAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public double calculateAverageWaitingTime() {
        return 0;
    }
}
