public class Process {
    String name;
    //String color;
    double arrivalTime;
    double burstTime;
    int priorityNum;
    double finishTime;
    double remainingTime;

    double AGFactor;
    int quantum;

    public Process(String name, double arrivalTime, double burstTime, int priorityNum) {
        this.name = name;
        //this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNum = priorityNum;
        remainingTime = burstTime;
    }

    public int getPriorityNum() {
        return priorityNum;
    }

    public void printProcess(){
        System.out.print(name + "        "+arrivalTime+"          "+burstTime+"           "+priorityNum+"   ");

    }

    public double getArrivalTime() {
        return arrivalTime;
    }
}
