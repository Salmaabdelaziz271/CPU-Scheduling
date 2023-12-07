public class Process {
    String name;
    //String color;
    double arrivalTime;
    double burstTime;
    int priorityNum;
    double finishTime;
<<<<<<< HEAD
    double remainingTime;
=======
>>>>>>> 0f70eb9c0060ce41add9745093ab85f6e7b6c50f

    public Process(String name, double arrivalTime, double burstTime, int priorityNum) {
        this.name = name;
        //this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNum = priorityNum;
<<<<<<< HEAD
        remainingTime = burstTime;
=======
>>>>>>> 0f70eb9c0060ce41add9745093ab85f6e7b6c50f
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 0f70eb9c0060ce41add9745093ab85f6e7b6c50f
