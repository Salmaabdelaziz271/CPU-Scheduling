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
        while (!arrivalPriorityQueue.isEmpty()){
            if(arrivalPriorityQueue.peek().arrivalTime == first.arrivalTime){
                priorityQueue.add(arrivalPriorityQueue.poll());
            }
            else if(priorityQueue.size() > 1){
                while (!priorityQueue.isEmpty()){
                    Process curr = priorityQueue.poll();
                    curr.finishTime = cumulative + curr.burstTime;
                    cumulative += curr.burstTime;
                    double startTime = cumulative - curr.burstTime;
                    finalProcesses.add(new ProcessInterval(curr.name, startTime,curr.finishTime));
                    updateStarvationSolver(priorityQueue, cumulative);
                    while (!arrivalPriorityQueue.isEmpty()){
                        Process p = arrivalPriorityQueue.peek();
                        if(p.arrivalTime <= cumulative){
                            priorityQueue.add(p);
                            arrivalPriorityQueue.poll();
                        }
                        else {
                            break;
                        }
                    }
                    

                }
            }
            else if(priorityQueue.size() == 1){
                Process single = priorityQueue.poll();
                single.finishTime = cumulative + single.burstTime;
                cumulative += single.burstTime;
                double startTime = cumulative - single.burstTime;
                finalProcesses.add(new ProcessInterval(single.name, startTime,single.finishTime));
                updateStarvationSolver(priorityQueue, cumulative);
                while (!arrivalPriorityQueue.isEmpty()){
                    Process p = arrivalPriorityQueue.peek();
                    if(p.arrivalTime <= cumulative){
                        priorityQueue.add(p);
                        arrivalPriorityQueue.poll();
                    }
                    else {
                        break;
                    }
                }
                

                while (!priorityQueue.isEmpty()){
                    Process curr = priorityQueue.poll();
                    curr.finishTime = cumulative + curr.burstTime;
                    cumulative += curr.burstTime;
                    startTime = cumulative - curr.burstTime;
                    finalProcesses.add(new ProcessInterval(curr.name, startTime,curr.finishTime));
                    updateStarvationSolver(priorityQueue, cumulative);
                    while (!arrivalPriorityQueue.isEmpty()) {
                        Process p = arrivalPriorityQueue.peek();
                        if(p.arrivalTime <= cumulative){
                            priorityQueue.add(p);
                            arrivalPriorityQueue.poll();
                        }
                        else {
                            break;
                        }
                    }

                }

            }
            else{
                priorityQueue.add(arrivalPriorityQueue.poll());
            }

        }
        while (!priorityQueue.isEmpty()){
            Process curr = priorityQueue.poll();
            curr.finishTime = cumulative + curr.burstTime;
            cumulative += curr.burstTime;
            double startTime = cumulative - curr.burstTime;
            finalProcesses.add(new ProcessInterval(curr.name, startTime,curr.finishTime));

        }

    }



    @Override
    public void printExecutionOrder() {
        System.out.println("Process Name" + "       "+"Start Time"+"         "+"End Time");
        for (ProcessInterval p : finalProcesses) {
            p.printProcessInterval();
        }

    }

    public void updateStarvationSolver(PriorityQueue<Process> readyQueue, double currentTime) {
        double starvationTime = 20;
        for(Process process : readyQueue){
            //check the waiting time of processes in ready queue
            if(currentTime - process.arrivalTime >= starvationTime) {
                process.priorityNum--;
            }
        }

    }


}
