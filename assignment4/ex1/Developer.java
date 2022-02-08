package ex1;

public final class Developer implements Runnable {
	private final Catalog catalog;

	public Developer(Catalog catalog) {
		this.catalog = catalog;
	}

	@Override
	public void run() {
		try {
      Thread.sleep(10);
      catalog.publish(new Game());
		} catch (ArrayIndexOutOfBoundsException ex) {
      System.err.println("- ERROR: Race between developers");
      System.exit(1);
    } catch (InterruptedException ex){
      System.err.println("InterruptedException not supported");
      System.exit(2);
    }
	}
}
