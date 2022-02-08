package ex1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Ex1aSimulator extends Simulator {

  public static void main(String[] args) throws InterruptedException {
    new Ex1aSimulator().run();
  }

  private final List<Thread> threads = new LinkedList<>();

  @Override
  public void addDeveloper(Developer developer) {
    threads.add(new Thread(developer));
  }

  @Override
  public void addGamer(Gamer gamer) {
    threads.add(new Thread(gamer));
  }

  @Override
  protected void startWorkers() {
    Collections.shuffle(threads);
    for(Thread thread : threads) {
      thread.start();
    }
  }

  @Override
  protected void waitWorkers() throws InterruptedException {
    for(Thread thread : threads) {
      thread.join();
    }
  }
}

//
