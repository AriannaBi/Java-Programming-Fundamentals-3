package ex1;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public abstract class Simulator {
  protected final int nDevelopers = Parameters.getNumberDevelopers();
  protected final int nGamers = Parameters.getNumberGamers();

  public final void run() throws InterruptedException {
    System.out.println("Simulation: #dev="+nDevelopers+" #gamers="+nGamers);
    Catalog catalog = CatalogFactory.create();
    for (int i = 0; i < nGamers; i++) {
      addGamer(new Gamer(catalog));
    }
    for (int i = 0; i < nDevelopers; i++) {
			addDeveloper(new Developer(catalog));
		}
    long start = System.nanoTime();
    startWorkers();
    waitWorkers();
    long end = System.nanoTime();
    long execTime = MILLISECONDS.convert(end - start, NANOSECONDS);
    System.out.println("Execution time: " + execTime + " ms");
  }

  protected abstract void addDeveloper(Developer developer);
  protected abstract void addGamer(Gamer gamer);

  protected abstract void startWorkers();
  protected abstract void waitWorkers() throws InterruptedException;
}
