public class Process {
    String name;
    //String color;
    double arrivalTime;
    double burstTime;
    int priorityNum;
    double finishTime;

    public Process(String name, double arrivalTime, double burstTime, int priorityNum) {
        this.name = name;
        //this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNum = priorityNum;
    }
}
