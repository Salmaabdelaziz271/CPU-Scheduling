public class ProcessInterval {
    String processName;
    double startTime;
    double endTime;

    ProcessInterval(){

    }
    ProcessInterval(String processName, double startTime, double endTime){
        this.processName = processName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void printProcessInterval(){
        System.out.println(processName + "                   " + startTime + "               " + endTime);
    }
}
