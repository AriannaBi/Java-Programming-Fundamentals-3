package ex1;

import java.util.concurrent.ExecutorService;

//the simulation implementations used so far created a thread for each task (either developer or gamer) to be execute
//but this is not fine, because if the simulation need to execute many tasks, it becomes less respondive
//up to the point that the system reactivity can be undetermined (go over the capacity of the system out of memory exception)

//we solve this issue by executing threads in a thread pool, instead of spawing one thread for each task. 

public class Ex1cSimulator extends Ex1bSimulator {

  public static void main(String[] args) throws InterruptedException {
    new Ex1cSimulator().run();
  }

  // TODO: get the number of available processors (N) in your system and
  // instantiate two thread pools, one for developers and one for
  // gamers, both of them with N threads
  private final int N_THREADS = Runtime.getRuntime().availableProcessors(); //obtained by runtime class 
  private final ExecutorService threadPoolDev = Executors.newFixedThreadPool(N_THREADS);
  private final ExecutorService threadPoolGam = Executors.newFixedThreadPool(N_THREADS);

  @Override
  protected void addDeveloper(Developer developer) {
    // Runnable runnable = new MonitoredRunnable(developer);
    // TODO: execute the runnable in the developers pool
    threadPoolDev.execute(new MonitoredRunnable(developer));
    //using the submit you can take a callable or runnable and turns into a future because callable instead of runnable
    //can return value.  (if you have callable you have to use submit and future)
    //while executes takes only the runnable, and is a void function. 
    //if you have runnable you can choose to use the executes or submit (better executes)
    
  }

  @Override
  protected void addGamer(Gamer gamer) {
    // Runnable runnable = new MonitoredRunnable(gamer);
    // TODO: execute the runnable in the gamers pool
    threadPoolGam.execute(new MonitoredRunnable(gamer));
    
  }

  @Override
  protected void waitWorkers() throws InterruptedException {
    // TODO: shutdown the thread pools
    threadPoolDev.shutdown(); //all the tasks already submitted will be executed 
    threadPoolGam.shutdown();
    super.waitWorkers();
  }
}
