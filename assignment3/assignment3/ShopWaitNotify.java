package assignment3;

import java.util.Queue;
import java.util.LinkedList;

public final class ShopWaitNotify implements Shop {
	private final Queue<Product> warehouse = new LinkedList<Product>();

	//synchronized block are used to avoid races, while wait and notify to avoid inefficent busy waiting

	public void buy(Product product) throws InterruptedException {//enqueue -> need to release to increment queue
		processOrder();
		synchronized(warehouse) { //performe the same as the mutex
			warehouse.add(product);
			warehouse.notify(); //enqueue element, if throws ex doen't increment 
		}

		//we can use notifyAll() to wake up all the sleeping threads, but is useless because only one thread
		//will be able to consume the product that has been added. But is inefficient because we don't need to wake
		//up all the threads. 
	}

	public Product sell() throws InterruptedException { //dequeue -> need to acquire to decrement queue
		Product productToSell;
		processOrder();
		synchronized(warehouse) {
			while(warehouse.size() == 0) { //while is necessary since thread safety is not guaratee without 
				//the using of a loop. Interrupts and spurius are wakeups are possible so we need to check
				//every time with a loop. while loop is like a sanity check.
				//threads that call wait() do not continuously check the condition, employing the CPU
				warehouse.wait(); //waiting for a product 
			} 
			productToSell = warehouse.poll();
			//if we cann a notify here, is not needed because calling notify after that a customer has consumed one item
			//is not needed. and also customers are woken up by sellers if necessary, and not by customers. 
		}
		processOrder();
		return productToSell;
	}

	private static void processOrder() throws InterruptedException {
		// simulating waiting time
		Thread.sleep(100);
	}
}
