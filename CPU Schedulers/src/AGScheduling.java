import java.util.*;

import static java.lang.Math.ceil;

public class AGScheduling extends CPUScheduling{
    private List<Process> readyQueue = new ArrayList<>();
    private List<Process> processesList = new ArrayList<>();
    private int currentTime = 0;

    public AGScheduling(List<Process> allProcesses) {
        super(allProcesses);
        addAG();
        AGSchedule();
    }

    int RF(){
        Random random = new Random();
        return random.nextInt(21);
    }

    public double calculateAGFactor(Process currentProcess){
        int random = RF();
        if (random < 10){
            return (random + currentProcess.arrivalTime + currentProcess.burstTime);
        }
        else if (random == 10){
            return (currentProcess.priorityNum + currentProcess.arrivalTime + currentProcess.burstTime);
        }
        else{
            return (10 + currentProcess.arrivalTime + currentProcess.burstTime);
        }
    }

    private Process isProcessArrived(){
        List<Process> sameArrival = new ArrayList<>();
        for (Process process: processesList){
            if (currentTime >= process.arrivalTime){
                sameArrival.add(process);
            }
        }

        // Choose the minimum AG-factor process
        if (!sameArrival.isEmpty()) {
            Process miniProcess = sameArrival.get(0);
            for (Process process : sameArrival) {
                if (process.AGFactor < miniProcess.AGFactor && process.arrivalTime <= miniProcess.arrivalTime) {
                    miniProcess = process;
                }
            }
            sameArrival.remove(miniProcess);
            processesList.remove(miniProcess);

            for (Process process : sameArrival) {
                readyQueue.add(process);
                sameArrival.remove(process);
                processesList.remove(process);
            }
            return miniProcess;
        }
        return null;
    }


    public void addAG(){
//        allProcesses.get(0).AGFactor = 20;
//        allProcesses.get(1).AGFactor = 17;
//        allProcesses.get(2).AGFactor = 16;
//        allProcesses.get(3).AGFactor = 43;


        for (Process process: allProcesses) {
            process.AGFactor = calculateAGFactor(process);
            process.remainingTime = process.burstTime;
            processesList.add(process);
        }

    }


    public boolean nonPreemptive(Process currentProcess, ProcessInterval finishedProcess){
        double halfQuantum = ceil(currentProcess.quantum / 2);

        // The Remaining Time is more than half the quantum time
        if (halfQuantum < currentProcess.remainingTime){
            currentTime += halfQuantum;
            currentProcess.remainingTime -= halfQuantum;
            return false;
        }
        // The process will finish in its half quantum time
        else{
            currentTime += currentProcess.remainingTime;
            currentProcess.remainingTime = 0;
            finishedProcess.endTime = currentTime;
            finalProcesses.add(finishedProcess);
            readyQueue.remove(currentProcess);
            return true;
        }
    }

    private double getQuantumMean(Process currentProcess){
        double mean = currentProcess.quantum;
        for (Process process: readyQueue){
            mean += process.quantum;
        }
        mean /= readyQueue.size() + 1;
        return mean;
    }

    public Process preemptive(Process currentProcess, ProcessInterval finishedProcess, double remainQuantum){

        double counter = currentProcess.quantum - remainQuantum;
        while (true){

            // Finished its quantum time
            if(counter == currentProcess.quantum){
                // But didn't finish its job
                if (currentProcess.remainingTime != 0){
                    double value = ceil(0.1 * getQuantumMean(currentProcess));
                    currentProcess.quantum += value;
                    readyQueue.add(currentProcess);
                }
                // And finished its job
                else{
                    currentProcess.quantum = 0;
                    currentProcess.finishTime = currentTime;
                }
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                if (readyQueue.isEmpty() && processesList.isEmpty()){
                    return null;
                }
                if (!readyQueue.isEmpty()){
                    currentProcess = readyQueue.get(0);
                    readyQueue.remove(currentProcess);
                }
                break;
            }

            // Finished its Job
            if (currentProcess.remainingTime == 0){
                currentProcess.finishTime = currentTime;
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                if (readyQueue.isEmpty() && processesList.isEmpty()){
                    return null;
                }
                if (!readyQueue.isEmpty()){
                    currentProcess = readyQueue.get(0);
                    readyQueue.remove(currentProcess);
                }
                break;
            }


            // Check if a new process arrived
            Process arrivedProcess = isProcessArrived();
            if (arrivedProcess != null){
                // Continue working on the current process with the smaller AG factor
                if (currentProcess.AGFactor < arrivedProcess.AGFactor) {
                    readyQueue.add(arrivedProcess);
                    processesList.remove(arrivedProcess);
                }
                // Switch to the arrived process with the smaller AG factor
                else {
                    currentProcess.quantum += remainQuantum;
                    readyQueue.add(currentProcess);
                    finishedProcess.endTime = currentTime;
                    finalProcesses.add(finishedProcess);
                    finishedProcess = new ProcessInterval(arrivedProcess.name, currentTime, -1);
                    currentProcess = arrivedProcess;
                    remainQuantum = arrivedProcess.quantum;
                    return currentProcess;
                }
            }


            // Check if there is a process in the ready queue that has smaller AG factor
            Process minProcess = currentProcess;
            for (Process process: readyQueue){
                if (process.AGFactor < minProcess.AGFactor){
                    minProcess = process;
                }
            }
            if (minProcess != currentProcess){
                readyQueue.remove(minProcess);
                currentProcess.quantum += remainQuantum;
                readyQueue.add(currentProcess);
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                finishedProcess = new ProcessInterval(minProcess.name, currentTime, -1);
                currentProcess = minProcess;
                remainQuantum = minProcess.quantum;
                return currentProcess;
            }


            currentProcess.remainingTime--;
            remainQuantum--;
            currentTime++;
            counter++;
        }
        return currentProcess;
    }

    private void handleAGSchedule(Process current){
        if (current == null){
            return;
        }

        double halfQuantum = ceil(current.quantum / 2);
        ProcessInterval finishedProcess = new ProcessInterval(current.name, currentTime, -1);
        if (!nonPreemptive(current, finishedProcess)){
            Process process = preemptive(current, finishedProcess, current.quantum - halfQuantum);
            processesList.remove(process);
            handleAGSchedule(process);
        }
        else{
            current.finishTime = currentTime;
            if (!readyQueue.isEmpty()){
                Process process = readyQueue.get(0);
                readyQueue.remove(process);
                handleAGSchedule(process);
            }
            else{
                // Check if a new process has arrived
                if (!processesList.isEmpty()){
                    Process arrivedProcess = isProcessArrived();
                    if (arrivedProcess != null){
                        handleAGSchedule(arrivedProcess);
                    }
                }
            }

        }
    }

    public void AGSchedule(){

        Process arrivedProcess = isProcessArrived();
        if (arrivedProcess != null){
            handleAGSchedule(arrivedProcess);
        }
    }



    @Override
    public void printExecutionOrder() {
        System.out.println("Process Name" + "       "+"Start Time"+"         "+"End Time");
        for (ProcessInterval p : finalProcesses) {
            p.printProcessInterval();
        }
    }

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