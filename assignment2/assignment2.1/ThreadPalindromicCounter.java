package assignment2;

import java.util.List;
import java.util.LinkedList;

public final class ThreadPalindromicCounter implements Counter {

	private final int numThreads;
	private int total;

	public ThreadPalindromicCounter(int numThreads) {
		this.numThreads = numThreads;
	}

	public int count(int[] nums) throws Exception {
		total = 0;
		
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
		
		return total;
	}

	private void incrementCounter() {
		total++; // is not an atomic operation: this is an example of read-modify-write operation in which the 
				//result is derived from the previous state.
				//read, incremented and the store the result. 
				//without proper synchronization , the result unpredictably depends on scheduling or interleaving the threads
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
					ThreadPalindromicCounter.this.incrementCounter();
				}
			}
		}
	}
}
