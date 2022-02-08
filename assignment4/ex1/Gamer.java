package ex1;

public class Gamer implements Runnable {
  private final Catalog catalog;

  public Gamer(Catalog catalog) {
    this.catalog = catalog;
  }

  @Override
  public void run() {
    try {
      catalog.play((int) (Math.random() * catalog.size()));
    } catch (GameNotAvailableException ex) {
      System.err.println("- ERROR: Gamer trying to play an unavailable game");
      System.exit(1);
    } catch (InterruptedException ex){
      System.err.println("InterruptedException not supported");
      System.exit(2);
    }
  }
}
