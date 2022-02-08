package assignment3;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;

public final class ShopSemaphore implements Shop {
	private final Queue<Product> warehouse = new LinkedList<Product>();
	private final Semaphore mutex = new Semaphore(1); //binary semaphore to implement mutex and avoid races
	// means lock the buffer and unlock the buffer

	private final Semaphore notEmpty = new Semaphore(0);//counting semaphore to keep track of numbers of 
	//items in the buffer and allow customers to wait (zero items at the beginning)


	//sellers are the producers while costumers are the consumers (sellers put into buffer, costumers get from the buffer)
	// the buffer is unbounded (queue)
	//the total number of threads is given by the number of sellers and costumers.

	public void buy(Product product) throws InterruptedException { //enqueue
		processOrder();
		mutex.acquire(); //acquire the lock in the critical section when accessing the buffer
		try { //maybe warehous.add throws an exception
			warehouse.add(product);
		} finally {
			mutex.release(); //finally because we want to release the mutex even if add throws an exception
		}
		notEmpty.release(); //we can increment number of items into the semaphore (if throw ex doesn't do that)
	}
	//the try finally is necessary if we want to release the mutex also if we have an exception in the meanwhile.
	//if we don't use it, we throw an exception and never release the mutex so other threads will never acquire it.
	
	//we cannot put the release of the counting semafore in the finally because if we have anexception
	//we don't wanto to increment the queue. This is an error because a customer will try to acquire a product that
	//is actually not available.

	public Product sell() throws InterruptedException { //dequeue only if is not empty 
		Product productToSell;
		processOrder();
		notEmpty.acquire(); //we are consuming an element so we decrease the number of items in the buffer.
		//if mutex.acquire() throws an exception,  we will not consume this item, and so we realise it. 
		try { 
			mutex.acquire();
		} catch(InterruptedException ex) {
			notEmpty.release();
			throw ex;
		}
		productToSell = warehouse.poll(); //this doesn't throw exception so we don't put it in a try catch statement 
		mutex.release();
		//if we put another notEmpty.release(); is wrong because we are invalidate the correct count item 
		//we need to do this when we are increment the queue when the shop buys.
		processOrder();
		return productToSell;
	}

	private static void processOrder() throws InterruptedException {
		// simulating waiting time
		Thread.sleep(100);
	}
}



// Question 4:
//NUMBER OF SELLERS > NUMBER OF COSTUMERS (more enqueue than dequeue)
//the program can crash with out of memory error since the queue can grow indefinitely and consuming all the heap 
//space and never release it. this means that the garbage collector is not able to collect so much not neede data.
 
// Important:
// locking a big or small object require the same amout of time: the lock state is entirely stored in the header o
//f the object. (header contains information that JVM uses and
//the lock state is here and when we lock the object we change a field).


// Question 5:
//NUMBER OF SELLERS < NUMBER OF COSTUMERS (more dequeue than enqueue)
//The exceeding customers will wait indefinitely.
//referencing the solution that exploits wait/notify:
//each of these exceeding consumers that invoke wait() in method shop.sell() will never be woken up
//so customer will not allowed the program to terminate the execution 

//documentation of wait:
//the current threads must own this object's monitor (meaning the wait called must be placed in a synchronized method or block).
//when cal wait, you release the lock associated to the object on wich you call wait. if a customer is executed,
//it calls wait, it releases the lock so that sellers are able to put items into the buffer and then it is able to 
//acquire the lock and continue with the execution. when you call notify the customer is able to reacquire the lock 
//and finish the execution. 
