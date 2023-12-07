import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduling extends CPUScheduling{
    private PriorityQueue<Process> priorityQueue;
    private PriorityQueue<Process> arrivalPriorityQueue;
    private int cumulative = 0;


    public PriorityScheduling(List<Process> allProcesses) {
        super(allProcesses);
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriorityNum));
        arrivalPriorityQueue  = new PriorityQueue<>(Comparator.comparingDouble(Process::getArrivalTime));
        arrivalPriorityQueue.addAll(allProcesses);
        priority();
    }

    public void priority(){
        Process first = arrivalPriorityQueue.poll();
        priorityQueue.add(first);
        first.finishTime = cumulative + first.burstTime;
        cumulative += first.burstTime;

        Process prev = null;
        while (!arrivalPriorityQueue.isEmpty()){
            prev = arrivalPriorityQueue.poll();
            if(prev.arrivalTime == first.arrivalTime){
                priorityQueue.add(prev);
                prev.finishTime = cumulative + first.burstTime;
                cumulative += first.burstTime;
            }
            else{
                break;
            }
        }
        finalProcesses.addAll(priorityQueue);
        arrivalPriorityQueue.add(prev);
        priorityQueue.clear();

        while (!arrivalPriorityQueue.isEmpty()) {
            Process curr = arrivalPriorityQueue.poll();
            if (curr.arrivalTime < cumulative) {
                priorityQueue.add(curr);
            } else {
                while (!priorityQueue.isEmpty()) {
                    Process p = priorityQueue.poll();
                    finalProcesses.add(p);
                    p.finishTime = cumulative + p.burstTime;
                    cumulative += p.burstTime;
                }
                priorityQueue.add(curr);
            }
        }

        while (!priorityQueue.isEmpty()) {
            Process p = priorityQueue.poll();
            finalProcesses.add(p);
            p.finishTime = cumulative + p.burstTime;
            cumulative += p.burstTime;
        }

    }

    @Override
    public void printExecutionOrder() {
        System.out.println("name" + "       "+"arrivalTime"+"     "+"burstTime"+"     "+"priorityNum"+"   ");
        for(Process p : finalProcesses){
            p.printProcess();
            System.out.println('\n');
        }
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
    public double calculateAverageTurnaroundTime() {
        double sum = 0;
        for(Process p : finalProcesses) {
            sum += calculateTurnaroundTime(p);
        }
        double avg = sum / finalProcesses.size();
        return avg;
    }
    @Override
    public double calculateAverageWaitingTime() {
        double sum = 0;
        for(Process p : finalProcesses) {
            sum += calculateWaitingTime(p);
        }
        double avg = sum / finalProcesses.size();
        return avg;
    }
}