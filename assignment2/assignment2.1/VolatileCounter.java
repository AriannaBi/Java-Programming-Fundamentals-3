package assignment2;

import java.util.List;
import java.util.LinkedList;

public final class VolatileCounter implements Counter {

	private final int numThreads;
	private volatile int total;
	//the changes made to the total field by one thread are immediatly visible to all others threads,
	//but this not avoid races since total++ is still a read-modify-write operation. 
	//this means that t1 can read the field, then t2 can read modify and write the field so that
	//t1 now modify and write the old field. this is because total++ is not atomic and can encouter races.

	public VolatileCounter(int numThreads) {
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
		public void run() {
			for (int i = low; i < high; i++) {
				if (Palindromic.isPalindromic(nums[i])) {
					VolatileCounter.this.incrementCounter();
				}
			}
		}
	}
}
