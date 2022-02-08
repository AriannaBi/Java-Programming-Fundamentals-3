package assignment2;

import java.util.List;
import java.util.LinkedList;

public final class SynchronizedBlocksCounter implements Counter {

	private final int numThreads;
	private int total;

	public SynchronizedBlocksCounter(int numThreads) {
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

	//the solution is synchronized the block total++ so 
	// private void incrementCounter() {
	// 	synchronized(this) {
	// 		total++;
	// 	}
	// }

	//has the same behaviour of putting synchronized after the private because we synchronized all the
	//body of the function with (this).

	// public void run() {
	// 	for (int i = low; i < high; i++) {
	// 		if (Palindromic.isPalindromic(nums[i])) {
	// 			synchronized(SynchronizedMethodsCounter.this) {
	// 				SynchronizedMethodsCounter.this.incrementCounter();
	// 			}
	// 		}
	// 	}
	// }
	//this solution of the other class SynchronizedMethodCounter avoid races but is not correct because 
	// if you call increment counter only from the run method you will not encounter races, but if you call
	//incrementCounter() from outside, you will have. 
	//also this implementation assumes that the caller(run) knows the content of the method incrementCounter()
	//and this is not possible. 
	

	private class PalindromicCounter implements Runnable {
		private final int[] nums;
		private final int low, high;

		private PalindromicCounter(int[] nums, int low, int high) {
			this.nums = nums;
			this.low = low;
			this.high = Math.min(high, nums.length);
		}

		@Override
		public void run() {
			for (int i = low; i < high; i++) {
				if (Palindromic.isPalindromic(nums[i])) {
					SynchronizedBlocksCounter.this.incrementCounter();
				}
			}
		}
	}
}
