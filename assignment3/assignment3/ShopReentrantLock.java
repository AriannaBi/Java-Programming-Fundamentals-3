package assignment3;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.ReentrantLock;

public final class ShopReentrantLock implements Shop {
	private final Queue<Product> warehouse = new LinkedList<Product>();

	//reentrantlock has same behaviour and semantic as the implicit monitor lock accessed using synchronized 
	//methods and statements, but with extended capabilities. 
	//this means that we can using reentrantlock instead of synchronized lock. 

	//condition factors out the object monitor methods(wait, notify, notifyall) into distinct objects to give 
	//the effect of having multiple wait-sets per object. 
	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();


	public void buy(Product product) throws InterruptedException {
		processOrder();
		lock.lock();
		try {
			warehouse.add(product); //maybe throws an exception 
			notEmpty.signal(); //is inside because we call it only when our thread is locked (before unlock)
		} finally {
			lock.unlock(); 
		}
	}

	public Product sell() throws InterruptedException { //dequeue
		Product productToSell;
		processOrder();
		lock.lock();
		try { //try finally necessary because lock must be released when await throws exception for the same reason of semaphores. 
			while(warehouse.size() == 0) {
				notEmpty.await();  //can throw exception
			}
			productToSell = warehouse.poll(); //if we have this statement in the finally, is an error because 
			//we might modify an element that is never returned and it get losts. also because if await throws
			//an exception and we poll an element, is not guarantee that the queue is not empty.
		} finally {
			lock.unlock();
		}  
		processOrder();
		return productToSell;
	}

	private static void processOrder() throws InterruptedException {
		// simulating waiting time
		Thread.sleep(100);
	}
}



//in semaphores and reentrantlock and wait/notify:
// try-finally are needed to properly release the lock. 
//the while loop is needed since interrupts and spurious wakeups are possible. 
//is not necessary to invoke signal at the end of method sell, because is not necessary to release the semaphore 
//at the end of sell, or call notify at the end of sell.
//is possible to replace signal with signalall, as notify with notifyall
//
