# CPU Schedulers Simulator
Scheduling is a fundamental operating-system function. Almost all computer resources are scheduled before use. The CPU is, of course, one of the primary computer resources. Thus, its scheduling is central to operating-system design. CPU scheduling determines which processes run when there are multiple run-able processes. CPU scheduling is important because it can have a big effect on resource utilization and the overall performance of the system.   

This is a **`Java`** application that **`Simulates some types of CPU Schedulers`**.  
## Simulated CPU Scheduler
### 1. Non-Preemptive Shortest-Job First (SJF) Scheduler:
*  Selects the process with the **`Shortest Burst Time`** to execute **`First`**. Once a process starts executing, it is allowed to run to completion **`without interruption`**.
*  Doestn't ignore the **`Context Switching`**.
*  The Burst Time of each process is associated with it in the program input.

### 2. Shortest-Remaining Time First (SRTF) Scheduler:
* Selects the process with the **`Shortest-Remaining Burst Time`** for execution. It is a **`Preemptive`** variant of the SJF scheduling algorithm, meaning that it can **`Interrupt` the execution of a process if a new process with a shorter burst time arrives**.
* Solves the **`Starvation Problem`**.

### 3. Non-Preemptive Priority Scheduler:
*  Selects the process with the **`Highest Priority`** for execution. Once a process starts running, it continues until completion **`without interruption`**.
*  Solves the **`Starvation Problem`**.
*  The Priority is represented as a **positive integer** that is associated with each process in the program intput.

### 4. AG Scheduler:
* Its main idea is based on the **`Round Robin (RR) CPU scheduling`** algorithm that gives **`Equal Time Quantum`** to all processes.
* The **`Initial Quantum`** is provided by the user at the beginning of the program.
* A **New Factor** called **`AG-Factor`** is associated with each submitted process in our AG scheduling algorithm. This factor **sums** the effects of all **Three Basic Factors:**  
  - **`Random_Function(0,20)`** or **`10`** or **`Priority`**.
  -  **`Arrival Time`**.
  -  **`Burst Time`**.   
  The equation is: **`AG-Factor = (Priority or 10 or (random_function (0,20)) + Arrival Time + Burst Time`**
* The **`Random Function (RF)`** generates a number between (0,20) and this number is attached with each submitted process in our AG scheduling algorithm. This **RF can update the AG-Factor based on the random number**:
  - If(**`RF() < 10`**), then **`AG-Factor = RF() + Arrival Time + Burst Time`**.
  - If(**`RF() > 10`**), then **`AG-Factor = 10 + Arrival Time + Burst Time`**.
  - If(**`RF() = 10`**), then **`AG-Factor = Priority + Arrival Time + Burst Time`**.
* Once a process is executed for given time period, it’s called **`Non-preemptive AG till the finishing of (ceil (50%)) of its Quantum time`**, **after that it’s converted to `Preemptive AG`:**
  - In Preemptive AG, **the processes will always run until they `Complete` or a `New Process arrives with a Smaller AG-Factor`**.
* We have **3 scenarios of the Running Process:**
  - The running process **`Used All its Quantum Time and it Still Have Job to Do`**, then it will be **`Added to the End of the Ready Queue`**, then **`Increases its Quantum Time by (ceil(10% of the (mean of Quantum)))`**.
  - The running process **`Didn’t use all its Quantum Time, but a new process arrived with Smaller AG-Factor`**, This process will be **`Added to the End of the Ready Queue`**, then **`Increase its Quantum time by its Remaining Unused Quantum Time`**.
  - The running process **`Finished its job`**, then **`its Quantum Time will be Zero`** and it will be **`Removed from the Ready Queue`**.
 
## Program Input
* Number of processes.
* Round Robin Time Quantum.
* Sontext Switching Time.
* For Each Process enter:
  - Process Name.
  - Process Color (Graphical Representation).
  - Process Arrival Time.
  - Process Burst Time.
  - Process Priority Number.

## Program Output
The Program Outputs For Each Scheduler:
* Processes Execution Order.
* Waiting Time for Each Process.
* Turnaround Time for Each Process.
* Average Waiting Time.
* Average Turnaround Time.
* All History Update of Quantum Time for Each Process when using AG Scheduler.
<br>

## Project Authors
* [Salma Abdelaziz](https://github.com/Salmaabdelaziz271)
* [Roaa EmadEldin](https://github.com/RoaaEmadEldin)
* [Rana Emara](https://github.com/RanaImara22)
* [Aya Ashraf](https://github.com/AyaAshraf21)
* [Sama Ahmed](https://github.com/SamaAhmedS)
