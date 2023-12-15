import java.util.*;

import static java.lang.Math.ceil;

public class AGScheduling extends CPUScheduling{
    private List<Process> readyQueue = new ArrayList<>();
    private List<Process> processesList = new ArrayList<>();
    private List<QuantumHistory> quantumHistory = new ArrayList<>();
    private int currentTime = 0;
    private boolean flag = false;

    public AGScheduling(List<Process> allProcesses) {
        super(allProcesses);
        addAG();
        AGSchedule();
        printQuantumHistory();
    }

    private int RF(){
        Random random = new Random();
        return random.nextInt(21);
    }

    private double calculateAGFactor(Process currentProcess){
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
                processesList.remove(process);
            }
            sameArrival.clear();
            return miniProcess;
        }
        return null;
    }


    private void addAG(){
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


    private boolean nonPreemptive(Process currentProcess, ProcessInterval finishedProcess){
        double halfQuantum = ceil(currentProcess.quantum / 2);

        addQuantumHistory(currentProcess.name);

        // The Remaining Time is more than half the quantum time
        if (halfQuantum < currentProcess.remainingTime){
            currentTime += halfQuantum;
            currentProcess.remainingTime -= halfQuantum;
            return false;
        }
        // The process will finish in its half quantum time
        else{
            if (currentProcess.remainingTime == 0){
                readyQueue.remove(currentProcess);
                flag = true;
                return true;
            }
            currentTime += currentProcess.remainingTime;
            currentProcess.remainingTime = 0;
            currentProcess.quantum = 0;
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

    private void addQuantumHistory(){
        List<Double> Quantum = new ArrayList<>();
        for (Process process : allProcesses) {
            Quantum.add(process.quantum);
        }
        QuantumHistory q = new QuantumHistory(currentTime, Quantum, allProcesses.size(), "");
        quantumHistory.add(q);
    }

    private void printQuantumHistory(){
        for (QuantumHistory q : quantumHistory){
            q.printQuantumHistory();
        }
    }

    private Process preemptive(Process currentProcess, ProcessInterval finishedProcess, double remainQuantum){

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
                    currentProcess.finishTime = currentTime;;
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
                currentProcess.quantum = 0;
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
                if (process.AGFactor < minProcess.AGFactor && process.remainingTime != 0){
                    minProcess = process;
                }
            }
            if (minProcess != currentProcess){
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
            if (!flag){
                current.finishTime = currentTime;
            }

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

    private void addQuantumHistory(String processName){
        List<Double> Quantum = new ArrayList<>();
        for (Process process : allProcesses) {
            Quantum.add(process.quantum);
        }
        QuantumHistory q = new QuantumHistory(currentTime, Quantum, allProcesses.size(), processName);
        quantumHistory.add(q);
    }

    private void AGSchedule(){
        Process arrivedProcess = isProcessArrived();
        while(arrivedProcess == null){
            currentTime++;
            arrivedProcess = isProcessArrived();
        }
        handleAGSchedule(arrivedProcess);
        addQuantumHistory();

        for (int i = 0; i < finalProcesses.size(); i++){
            if (finalProcesses.get(i).startTime == finalProcesses.get(i).endTime){
                finalProcesses.remove(finalProcesses.get(i));
            }
            if (i != finalProcesses.size() - 1){
                if (Objects.equals(finalProcesses.get(i).processName, finalProcesses.get(i + 1).processName)){
                    finalProcesses.get(i).endTime = finalProcesses.get(i + 1).endTime;
                    finalProcesses.remove(finalProcesses.get(i + 1));
                }
            }
        }
    }



    @Override
    public void printExecutionOrder() {

        System.out.println("\nProcess Name" + "       "+"Start Time"+"         "+"End Time");
        for (ProcessInterval p : finalProcesses) {
            p.printProcessInterval();
        }
    }


}