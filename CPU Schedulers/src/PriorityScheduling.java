import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduling extends CPUScheduling{

    private PriorityQueue<Process> priorityQueue;

    public PriorityScheduling(List<Process> allProcesses) {
        super(allProcesses);
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriorityNum));
        priorityQueue.addAll(allProcesses);
    }

    public void priority(){
        while (!priorityQueue.isEmpty()) {
            finalProcesses.add(priorityQueue.poll());
        }


    }

    @Override
    public void printExecutionOrder() {
        priority();
        for(Process p : finalProcesses){
            p.printProcess();
            System.out.println('\n');
        }
    }

    //finish-arrival
    @Override
    public double calculateTurnaroundTime(Process process) {
        return 0;
    }

    //turnarround-burst
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
