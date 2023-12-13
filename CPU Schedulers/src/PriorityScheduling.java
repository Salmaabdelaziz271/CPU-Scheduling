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
        List<Process> temp = new ArrayList<>();
        Process first = arrivalPriorityQueue.poll();
        priorityQueue.add(first);
        while (!arrivalPriorityQueue.isEmpty()){
            if(arrivalPriorityQueue.peek().arrivalTime == first.arrivalTime){
                priorityQueue.add(arrivalPriorityQueue.poll());
            }
            else if(priorityQueue.size() > 1){
                while (!priorityQueue.isEmpty()){
                    Process curr = priorityQueue.peek();
                    curr.finishTime = cumulative + curr.burstTime;
                    cumulative += curr.burstTime;
                    double startTime = cumulative - curr.burstTime;
                    temp.add(priorityQueue.poll());
                    finalProcesses.add(new ProcessInterval(curr.name, startTime,curr.finishTime));
                }
            }
            else if(priorityQueue.size() == 1){
                Process single = priorityQueue.poll();
                temp.add(single);
                single.finishTime = cumulative + single.burstTime;
                cumulative += single.burstTime;
                double startTime = cumulative - single.burstTime;
                finalProcesses.add(new ProcessInterval(single.name, startTime,single.finishTime));
                while (!arrivalPriorityQueue.isEmpty()){
                    Process p = arrivalPriorityQueue.peek();
                    if(p.arrivalTime < cumulative){
                        priorityQueue.add(p);
                        arrivalPriorityQueue.poll();
                    }
                    else {
                        break;
                    }
                }

                while (!priorityQueue.isEmpty()){
                    Process curr = priorityQueue.peek();
                    curr.finishTime = cumulative + curr.burstTime;
                    cumulative += curr.burstTime;
                    temp.add(priorityQueue.poll());
                    startTime = cumulative - curr.burstTime;
                    finalProcesses.add(new ProcessInterval(curr.name, startTime,curr.finishTime));
                }

            }
            else{
                priorityQueue.add(arrivalPriorityQueue.poll());
            }
        }
        while (!priorityQueue.isEmpty()){
            Process curr = priorityQueue.peek();
            curr.finishTime = cumulative + curr.burstTime;
            cumulative += curr.burstTime;
            temp.add(priorityQueue.poll());
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


}