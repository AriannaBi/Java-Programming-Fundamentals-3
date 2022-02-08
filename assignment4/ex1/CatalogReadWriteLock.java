package ex1;

import java.util.concurrent.locks.Lock;

public final class CatalogReadWriteLock extends CatalogImpl {


	//avoid races but allowing multiple readers.
	private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

	public CatalogReadWriteLock(int numberOfGames) {
	  super(numberOfGames);
	}

	public void publish(Game newGame) throws InterruptedException {
		w.lock(); 
		try {
			super.publish(newGame);
		} finally {
			w.unlock();
		}
	}

	public void play(int index) throws InterruptedException, GameNotAvailableException {
		r.lock();
		try {
			super.play(index);
		} finally {
			r.unlock();
		}
	}

	//try finally is a strong requirement. the reason is that if a publish leave to an runtime and checked exception
	//we have to ensure that we release the lock, otherwise we go into a deadlock.


	// private final ReentrantReadWriteLock rw = rwl.ReentrantReadWriteLock();

	// public CatalogReadWriteLock(int numberOfGames) {
	//   super(numberOfGames);
	// }

	// public void publish(Game newGame) throws InterruptedException {
	// 	rw.writeLock().lock();
	// 	try {
	// 		super.publish(newGame);
	// 	} finally {
	// 		rw.writeLock().unlock();
	// 	}
	// }

	// public void play(int index) throws InterruptedException, GameNotAvailableException {
	// 	rw.redLock().lock();
	// 	try {
	// 		super.play(index);
	// 	} finally {
	// 		rw.redLock().unlock();
	// 	}
	// }
}
