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
        ProcessInterval processInterval = new ProcessInterval();
        int NumOfFinalProcesses = 0;
        while (NumOfFinalProcesses != allProcesses.size()) {
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                currentTime++;
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime;
                    NumOfFinalProcesses++;
                    processInterval.endTime = currentTime;
                    finalProcesses.add(processInterval);
                    processInterval = new ProcessInterval();
                    currentProcess = null;
                }
            }
            if (currentProcess == null) {
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll();
                    processInterval.processName = currentProcess.name;
                    processInterval.startTime = currentTime;

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
                    processInterval.endTime = currentTime;
                    finalProcesses.add(processInterval);
                    processInterval = new ProcessInterval();
                    readyQueue.add(currentProcess);
                    currentProcess = readyQueue.poll();
                    processInterval.processName = currentProcess.name;
                    processInterval.startTime = currentTime;
                }
            }
        }
    }

    @Override
    public void printExecutionOrder() {
        System.out.println("Process Name" + "       "+"Start Time"+"         "+"End Time");
        for (ProcessInterval p : finalProcesses) {
           p.printProcessInterval();
        }

    }



}
