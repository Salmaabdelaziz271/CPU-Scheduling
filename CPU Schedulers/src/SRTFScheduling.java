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
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(Process::getSTRFStarvationSolver).thenComparingDouble(Process::getArrivalTime));
        List<Process> tempProcesses = new ArrayList<>(allProcesses);
        double currentTime = -1;
        Process currentProcess = null;
        ProcessInterval processInterval = new ProcessInterval();
        int NumOfFinalProcesses = 0;

        while (NumOfFinalProcesses != allProcesses.size()) {
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                currentTime++;
                //check if the process has finished its work
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime;
                    NumOfFinalProcesses++;
                    processInterval.endTime = currentTime;
                    finalProcesses.add(processInterval);
                    processInterval = new ProcessInterval();
                    currentProcess = null;
                    //update processes to avoid starvation problem
                    updateStarvationSolver(readyQueue, currentTime);
                }
            }
            if (currentProcess == null) {
                //allow the process from ready queue(minimum remaining burst time) to use the CPU
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.poll();
                    processInterval.processName = currentProcess.name;
                    processInterval.startTime = currentTime;

                }
                else {
                    currentTime++;
                }
            }
            //add processes that have just arrived to the ready queue
            for (int i = 0; i < tempProcesses.size(); i++) {
                if (tempProcesses.get(i).arrivalTime <= currentTime) {
                    readyQueue.add(tempProcesses.get(i));
                    tempProcesses.remove(i);
                    i--;
                }
            }
            if (currentProcess != null ) {
                //check if there process in ready queue that has remaining time less than the current process
                if (!readyQueue.isEmpty() && readyQueue.peek().remainingTime < currentProcess.remainingTime  && readyQueue.peek().STRFStarvationSolver < currentProcess.STRFStarvationSolver) {
                    processInterval.endTime = currentTime;
                    finalProcesses.add(processInterval);
                    processInterval = new ProcessInterval();
                    readyQueue.add(currentProcess);
                    //update processes to avoid starvation problem
                    updateStarvationSolver(readyQueue, currentTime);
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

    public void updateStarvationSolver( PriorityQueue<Process> readyQueue, double currentTime) {
        double agingFactor = 0.5;
        double starvationTime = 20;
        for(Process process : readyQueue) {
            //check the waiting time of processes in ready queue
            if(currentTime - process.arrivalTime >= starvationTime) {
                process.STRFStarvationSolver -= (currentTime - process.arrivalTime) * agingFactor;
            }
        }

    }





}
