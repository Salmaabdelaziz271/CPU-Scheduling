import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SRTFScheduling extends CPUScheduling{

    public SRTFScheduling(List<Process> allProcesses) {
        super(allProcesses);
    }

    @Override
    public void printExecutionOrder() {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(p -> p.remainingTime));
        double currentTime = 0;
        Process currentProcess = null;

        while (!allProcesses.isEmpty() || !readyQueue.isEmpty()) {
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                currentTime++;
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime;
                    finalProcesses.add(currentProcess);
                    currentProcess = null;
                }
            }
            if (currentProcess == null) {
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll();
                }
                else {
                    currentTime++;
                }
            }
            for (int i = 0; i < allProcesses.size(); i++) {
                if (allProcesses.get(i).arrivalTime <= currentTime) {
                    readyQueue.add(allProcesses.get(i));
                    allProcesses.remove(i);
                    i--;
                }
            }
            if (currentProcess != null) {
                if (!readyQueue.isEmpty() && readyQueue.peek().remainingTime < currentProcess.remainingTime) {
                    readyQueue.add(currentProcess);
                    currentProcess = readyQueue.poll();
                }
            }
        }

//        for (Process p : finalProcesses) {
//            System.out.println(p.name + " " + p.finishTime);
//            System.out.println('\n');
//        }
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
        for(Process p : allProcesses) {
            sum += calculateTurnaroundTime(p);
        }
        double avg = sum / allProcesses.size();
        return avg;
    }
    @Override
    public double calculateAverageWaitingTime() {
        double sum = 0;
        for(Process p : allProcesses) {
            sum += calculateWaitingTime(p);
        }
        double avg = sum / allProcesses.size();
        return avg;
    }


}