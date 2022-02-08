package ex1;

import java.util.concurrent.CountDownLatch;

//with simulation 1a the time taken for the creation and termination of threads is part of the collected
//executed time, but we want to collect the time spent to executing the tasks.
//this can be done by starting the timer only when a thread calls run and stopping it when it terminate run
//this required that the runnables execute by each thread are monitored: before executing the run method
//they should wait for a signal from the main thread, notifying that the main thread has started. 
//and threads should send a completion signal to the main thread to stop the timer once all completions 
//signals have been received. 
//we can do this with latches. 
public class Ex1bSimulator extends Simulator {

  public static void main(String[] args) throws InterruptedException {
    new Ex1bSimulator().run();
  }

  // TODO: instantiate the required latches. Note that you have access
  // to the protected fields nDevelopers and nGamers in the super class
  private final CountDownLatch startSignal = new CountDownLatch(1); //for the countdown of the number of threads (-1)
  private final CountDownLatch doneSignal = new CountDownLatch(nDevelopers + nGamers);//for reaching 0 and let pass threads

  protected class MonitoredRunnable implements Runnable {
    private final Runnable runnable;

    public MonitoredRunnable(Runnable runnable) {//is a wrapper, and we can implement the logic of the timer 
      //in that way we don't need to modify gamers and developers classes. 
      this.runnable = runnable;
    }

    @Override
    public void run() {
      try {
        // TODO: wait a start signal from the main thread
        startSignal.await(); //we wait for 
        runnable.run();
        // TODO: signal the main thread that the execution completed
        doneSignal.countDown();
      } catch (Exception e) {
        System.out.println("Exception not supported");
        System.exit(1);
      }
    }
  }

  @Override
  protected void startWorkers() {
    // TODO: signal the worker threads that they can proceed
    startSignal.countDown();
  }

  @Override
  protected void waitWorkers() throws InterruptedException {
    // TODO: wait for the workers to complete
    doneSignal.await();
  }

  @Override
  protected void addDeveloper(Developer developer) {
    new Thread(new MonitoredRunnable(developer)).start();//instead of appending to a list, we start immediatly 
    //because thred are monitored and will be controlled with the method start
  }

  @Override
  protected void addGamer(Gamer gamer) {
    new Thread(new MonitoredRunnable(gamer)).start();
  }
}
