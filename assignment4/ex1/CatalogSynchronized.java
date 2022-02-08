package ex1;

public class CatalogSynchronized extends CatalogImpl {

	public CatalogSynchronized(int numberOfGames) {
	  super(numberOfGames);
	}


	//we can solve by synchronizing the two methods:
	public synchronized void publish(Game newGame) throws InterruptedException {
	  super.publish(newGame);
	}

	public synchronized void play(int index) throws InterruptedException,
      GameNotAvailableException {
    super.play(index);
	}

	//but we have a poor performance because synchronized is based on a mutual-exclusion lock
	//and prevents red/write, write/write but also read/read. so we use the reentrantreadwritelock 
}
