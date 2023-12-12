import java.util.*;

import static java.lang.Math.ceil;

public class AGScheduling extends CPUScheduling{
    private List<Process> readyQueue = new ArrayList<>();
    private int currentTime = 0;

    public AGScheduling(List<Process> allProcesses) {
        super(allProcesses);
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


    public void addAG(){
        for (Process process: allProcesses) {
            process.AGFactor = calculateAGFactor(process);
            process.remainingTime = process.burstTime;
        }
    }


    public boolean nonPreemptive(Process currentProcess, ProcessInterval finishedProcess, double halfQuantum){
        if (halfQuantum < currentProcess.remainingTime){
            currentTime += halfQuantum;
            currentProcess.remainingTime -= halfQuantum;
            return false;
        }
        else{
            currentTime += currentProcess.remainingTime;
            currentProcess.remainingTime = 0;
            finishedProcess.endTime = currentTime;
            finalProcesses.add(finishedProcess);
            return true;
        }
    }

    public Process preemptive(Process currentProcess, ProcessInterval finishedProcess, double remainQuantum){

        double counter = currentProcess.quantum - remainQuantum;
        while (true){
            // Finished its quantum time
            if(counter == currentProcess.quantum){
                if (currentProcess.remainingTime != 0){
                    readyQueue.add(currentProcess);
                }
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                break;
            }

            if (currentProcess.remainingTime == 0){
                if (readyQueue.isEmpty() && allProcesses.isEmpty()){
                    return null;
                }
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                break;
            }


            // Check if a new process arrived
            for (Process process: allProcesses) {
                if (process.arrivalTime <= currentTime) {
                    if (currentProcess.AGFactor < process.AGFactor) {
                        readyQueue.add(process);
                    }
                    else {
                        currentProcess.quantum += remainQuantum;
                        readyQueue.add(currentProcess);
                        finishedProcess.endTime = currentTime;
                        finalProcesses.add(finishedProcess);
                        finishedProcess = new ProcessInterval(process.name, currentTime, -1);
                        currentProcess = process;
                        remainQuantum = process.quantum;
                        return currentProcess;
                    }
                }
            }

            // Check if there is a process in the ready queue that has smaller AG factor
            Process miniProcess = currentProcess;
            for (Process process: readyQueue){
                if (process.AGFactor < miniProcess.AGFactor){
                    miniProcess = process;
                }
            }
            if (miniProcess != currentProcess){
                currentProcess.quantum += remainQuantum;
                readyQueue.add(currentProcess);
                finishedProcess.endTime = currentTime;
                finalProcesses.add(finishedProcess);
                finishedProcess = new ProcessInterval(miniProcess.name, currentTime, -1);
                currentProcess = miniProcess;
                remainQuantum = miniProcess.quantum;
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
        if (!nonPreemptive(current, finishedProcess, halfQuantum)){
            Process process = preemptive(current, finishedProcess, current.quantum - halfQuantum);
            allProcesses.remove(process);
            handleAGSchedule(process);
        }
        else{
            Process miniProcess = readyQueue.get(0);
            for (Process process: readyQueue){
                if (process.AGFactor < miniProcess.AGFactor){
                    miniProcess = process;
                }
            }
            readyQueue.remove(miniProcess);
            handleAGSchedule(miniProcess);
        }
    }

    public void AGSchedule(){

        // Get all the processes with the same arrival time
        List<Process> sameArrival = new ArrayList<>();
        for (Process process: allProcesses){
            if (currentTime >= process.arrivalTime){
                sameArrival.add(process);
            }
        }

        Process miniProcess = sameArrival.get(0);
        for (Process process: sameArrival){
            if (process.AGFactor < miniProcess.AGFactor && process.arrivalTime <= miniProcess.arrivalTime){
                miniProcess = process;
            }
        }
        sameArrival.remove(miniProcess);
        allProcesses.remove(miniProcess);
        for (Process process: sameArrival){
            readyQueue.add(process);
            sameArrival.remove(process);
            allProcesses.remove(process);
        }

        double halfQuantum = ceil(miniProcess.quantum / 2);
        ProcessInterval finishedProcess = new ProcessInterval(miniProcess.name, currentTime, -1);
        if (!nonPreemptive(miniProcess, finishedProcess, halfQuantum)){
            Process process = preemptive(miniProcess, finishedProcess, miniProcess.quantum - halfQuantum);
            if (process != null){
                allProcesses.remove(process);
                handleAGSchedule(process);
            }

        }
    }



    @Override
    public void printExecutionOrder() {
        addAG();
        AGSchedule();
    }

    @Override
    public double calculateTurnaroundTime(Process process) {
        return 0;
    }

    @Override
    public double calculateWaitingTime(Process process) {
        return 0;
    }

    @Override
    public double calculateAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public double calculateAverageWaitingTime() {
        return 0;
    }


    // HANDLE REMOVING FROM READY QUEUE
    // HALF QUANTUM UPDATING
    // REMAINING CASES --> EDITING QUANTUM VALUES
}