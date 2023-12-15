public class Process {
    String name;
    //String color;
    double arrivalTime;
    double burstTime;
    int priorityNum;
    double finishTime;
    double remainingTime;

    double AGFactor;
    double quantum;
    double STRFStarvationSolver;

    public Process(String name, double arrivalTime, double burstTime, int priorityNum) {
        this.name = name;
        //this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNum = priorityNum;
        remainingTime = STRFStarvationSolver = burstTime;
    }

    public int getPriorityNum() {
        return priorityNum;
    }

    public void printProcess(){
        System.out.print(name + "        "+arrivalTime+"          "+burstTime+"           "+priorityNum+"   ");

    }
    public double getBurstTime() {
        return burstTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getSTRFStarvationSolver() {
        return STRFStarvationSolver;
    }
}
