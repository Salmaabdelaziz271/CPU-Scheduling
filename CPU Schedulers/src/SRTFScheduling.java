import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SRTFScheduling extends CPUScheduling{

    public SRTFScheduling(List<Process> allProcesses) {
        super(allProcesses);
        setExecutionOrder();
    }
    public void setExecutionOrder() {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(p -> p.remainingTime));
        List<Process> tempProcesses = new ArrayList<>(allProcesses);
        double currentTime = -1;
        Process currentProcess = null;

        while (finalProcesses.size() != allProcesses.size()) {
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
                } else {
                    currentTime++;
                }
            }
            for (int i = 0; i < tempProcesses.size(); i++) {
                if (tempProcesses.get(i).arrivalTime <= currentTime) {
                    readyQueue.add(tempProcesses.get(i));
                    tempProcesses.remove(i);
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
    }

    @Override
    public void printExecutionOrder() {
        System.out.println("SRTF Scheduling");
        for (Process p : finalProcesses) {
            System.out.println(p.name + " " + p.finishTime);
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
