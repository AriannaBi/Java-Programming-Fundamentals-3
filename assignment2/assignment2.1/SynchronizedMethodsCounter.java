package assignment2;

import java.util.List;
import java.util.LinkedList;

public final class SynchronizedMethodsCounter implements Counter {

	private final int numThreads;
	private int total;

	public SynchronizedMethodsCounter(int numThreads) {
		this.numThreads = numThreads;
	}

	public int count(int[] nums) throws Exception {
		total = 0;
		
		List<Thread> threads = new LinkedList<Thread>();
		int size = (int) Math.ceil((double) nums.length / numThreads);
		int threadId = 0;
		for (int low = 0; low < nums.length; low += size) {
			PalindromicCounter counter = new PalindromicCounter(nums, low, low + size);
			Thread thread = new Thread(counter);
			thread.start();
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.join();
		}
		
		return total;
	}

	private void incrementCounter() {
		total++;
	}

	private class PalindromicCounter implements Runnable {
		private final int[] nums;
		private final int low, high;

		private PalindromicCounter(int[] nums, int low, int high) {
			this.nums = nums;
			this.low = low;
			this.high = Math.min(high, nums.length);
		}

		@Override
		public synchronized void run() {
			for (int i = low; i < high; i++) {
				if (Palindromic.isPalindromic(nums[i])) {
					SynchronizedMethodsCounter.this.incrementCounter();
				}
			}
		}
		//the synchronized keyword lock the PalindromicCounter instance (innerclass instance) 
		//on which the method run is invoked, but for example given two threads, it is stil possible for both 
		//to concurrent read and update the field total, leading to wrong results.

		//Common question:
		//- the run method is called only once by each thread, even though this, it is still possible to 
		//	encounter races because the run invokations are not executed sequencially. In fact we call
		// 	threads.start();
		
		//- the entire executions starts from the run method invoking.

		//- the synchronized keyword lock the PalindromicCounter instance during the execution of the run method
		// 	and NOT the run method. 

		//- two threads (two PalindromicCounter instances) can execute in parallel the method run because the lock
		//	is on the instances and not on the method run. 

		//- the synchronized keyword acquire the lock of a region (in this case the method run) and if a thread object 
		// 	invoke this method run, and another thread of the same object invokes run, then it needs to wait fo the
		//	the same lock that now is held by the first thread. 

		//run cannot be access by multiple thread that lock the same object: if t1 acquire access the reagion, then hold
		//the lock of PalindromicCounter instance. if another t2 of the same object access the region, then it need to acquire the
		//lock of the same object. since the lock can be held by only one thread, t2 need to wait that t1 terminates. 
		//since we have multiple instances, then it means that multiple threads are trying to acquire the lock of the object, so
		//they executed in parallel the method run. 

		//if we synchronized run, then it means that in method run we only have access to one thread at a time
		//and so we don't need to synchronized method inside run because they are accessed by one thred at a time. 
		

		//REMEMBER: in run we are trying to lock every time a different object, so the synchronized in run is useless


		// public void run() {
		// 	for (int i = low; i < high; i++) {
		// 		if (Palindromic.isPalindromic(nums[i])) {
		// 			synchronized(SynchronizedMethodsCounter.this) {
		// 				SynchronizedMethodsCounter.this.incrementCounter();
		// 			}
		// 		}
		// 	}
		// }

		//here synchronized takes as parameter the outerclass 
		//access to the region is always the same instance of the outerclass, so when executes the count method, 
		//the parameter of synchronized is always the same. 

		//this avoid races because thread that want to access the synchronized region are all of the same objets
		//and so they have to wait the previous to hold the lock. 
		
		

		//--------------------------
		//to solve the races, we need to synchronized only the method incrementCounter(). 
		//The example above do that but with synchronized blocks. 



		//AT THE END:
		//synchronized keyword doesn not synchronized methods but objects, and if we pass always the same
		//object then each threads need to wait for the prevoius to finish, otherwise if we pass
		//other objects threads can execute in parallel (since we pass and lock different instances)
	}
}


