import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJFScheduling extends CPUScheduling{
    private double contextSwitch;
    public SJFScheduling(List<Process> allProcesses,double contextSwitch) {
        super(allProcesses);
        this.contextSwitch = contextSwitch;
        setExecutionOrder();
    }
    public void setExecutionOrder() {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(Process::getBurstTime).thenComparingDouble(Process::getArrivalTime));
        List<Process> tempProcesses = new ArrayList<>(allProcesses);
        int unProcessed = allProcesses.size();
        Process currentProcess = null;
        double currentTime =-1;
        ProcessInterval processInterval = new ProcessInterval();
        while(unProcessed > 0){
           for (int i = 0; i < tempProcesses.size(); i++) {
               if (tempProcesses.get(i).arrivalTime <= currentTime) {
                   readyQueue.add(tempProcesses.get(i));
                   tempProcesses.remove(i);
                   i--;
               }
           }
           if (currentProcess == null) {
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll();
                    processInterval.processName = currentProcess.name;
                    //if this was first process we would not increase current time by context switch time
                    if(unProcessed != allProcesses.size()) {
                        currentTime += contextSwitch;
                        processInterval.startTime = currentTime;

                    }else
                        processInterval.startTime = currentTime;
                } else {
                    currentTime++;
                }
           }
           else{
               if(currentProcess.remainingTime == 0){
                   currentProcess.finishTime = currentTime;
                   unProcessed--;
                   processInterval.endTime = currentTime;
                   finalProcesses.add(processInterval);
                   processInterval = new ProcessInterval();
                   currentProcess = null;
               }else{
                   currentProcess.remainingTime--;
                   currentTime++;
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
