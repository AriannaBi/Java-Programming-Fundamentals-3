package assignment2;

import java.util.List;
import java.util.LinkedList;
//import the java.util.concurrent.atomic.AtomicInteger class
// import java.util.concurrent.atomic.AtomicInteger;

public final class AtomicIntegerCounter implements Counter {
	//change the type of the "total" field to AtomicInteger and initialize it to a new AtomicInteger
	private int total; // private AtomicInteger total = new AtomicInteger();
	private final int numThreads;

	public AtomicIntegerCounter(int numThreads) {
		this.numThreads = numThreads;
	}

	public int count(int[] nums) throws Exception {
		//call AtomicInteger.set(int value) to set the current value to 0
		total = 0; //total.set(0);
		
		List<Thread> threads = new LinkedList<Thread>();
		int size = (int) Math.ceil((double) nums.length / numThreads);
		int threadId = 0;
		for (int low = 0; low < nums.length; low += size) {
			PalindromicCounter counter =
						new PalindromicCounter(nums, low, low + size);
			Thread thread = new Thread(counter);
			thread.start();
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.join();
		}
		
		//call AtomicInteger.get() to get the current int value
		return total; //total.get();
	}

	private void incrementCounter() {
		//call AtomicInteger.incrementAndGet() to atomically increment the current value
		total ++; //total.incrementAndGet();
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
		public void run() {
			for (int i = low; i < high; i++) {
				if (Palindromic.isPalindromic(nums[i])) {
					AtomicIntegerCounter.this.incrementCounter();
				}
			}
		}
	}
}
