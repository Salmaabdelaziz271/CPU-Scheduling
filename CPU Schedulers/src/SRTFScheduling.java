import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SRTFScheduling implements CPUScheduling{
    @Override
    public void printExecutionOrder(List<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.burstTime < o2.burstTime) {
                    return -1;
                } else if (o1.burstTime > o2.burstTime) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        List<Process> finishedProcesses = new ArrayList<>();
        double currentTime = 0;
        while (finishedProcesses.size() < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !finishedProcesses.contains(p)) {
                    readyQueue.add(p);
                }
            }
            Process currentProcess = readyQueue.poll();
            currentProcess.finishTime = currentTime + currentProcess.burstTime;
            currentTime = currentProcess.finishTime;
            finishedProcesses.add(currentProcess);
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
