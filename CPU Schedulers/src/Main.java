import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Process process;
        List<Process> allProcesses = new ArrayList<>();
        int numOfProcesses , timeQuantum , contextSwitch;
        String name;
        int arrivalTime , burstTime , priority;

        System.out.println("Hello in CPU Scheduler Simulatur ^_^");
        System.out.println("\n");

        System.out.print("Enter Number of processes: ");
        numOfProcesses = in.nextInt();

        System.out.print("\nEnter Round Robin Time Quantum: ");
        timeQuantum = in.nextInt();

        System.out.print("\nEnter Context switching: ");
        contextSwitch = in.nextInt();

        for(int i=0 ;i<numOfProcesses ; i++){
            System.out.print("\n\n");
            System.out.print(i+1);
            System.out.print(".\n");
            System.out.print("Enter process name: ");
            name = in.next();
            System.out.print("\nEnter process Arrival Time: ");
            arrivalTime = in.nextInt();
            System.out.print("\nEnter process Burst Time: ");
            burstTime = in.nextInt();
            System.out.print("\nEnter process Priority Number: ");
            priority = in.nextInt();

            process = new Process(name , arrivalTime , burstTime , priority);
            allProcesses.add(process);
        }
        System.out.println("\n");
        CPUScheduling cpu = new PriorityScheduling(allProcesses);


        //Priority Scheduling
        cpu.printExecutionOrder();

        System.out.println("\n\n print Turnaround Time for all processes");
        for(Process p : allProcesses){
            System.out.println(p.name + "   "+cpu.calculateTurnaroundTime(p));
        }

        System.out.println("\n\n print waiting Time for all processes");

        for(Process p : allProcesses){
            System.out.println(p.name + "   "+cpu.calculateWaitingTime(p));
        }

        System.out.println("\n\n print average Turnaround Time ");
        System.out.println(cpu.calculateAverageTurnaroundTime());

        System.out.println("\n\n print average waiting Time ");
        System.out.println(cpu.calculateAverageWaitingTime());




    }
}