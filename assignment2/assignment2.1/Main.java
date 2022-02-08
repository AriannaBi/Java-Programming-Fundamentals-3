package assignment2;

public final class Main {

	public static final int LOW = 1;								   	
	public static final int HIGH = 99_999_999;
	private static final int NUM_THREADS = 8;

	public static void main(String[] args) throws Exception  {
		Class<? extends Counter> counterClass = null;

		String counterClassName = args.length > 0
			? args[0]
			: "ThreadPalindromicCounter";
		try {
			counterClass = Class.forName("assignment2." + counterClassName)
												 .asSubclass(Counter.class);
		} catch (ClassNotFoundException ex) {
			System.err.println(
				"- ERROR: the provided command line parameter is not valid. " +
				"Cannot find a class named \"" + counterClassName + "\""
			);
			return;
		}

		Counter counter = counterClass
															.getDeclaredConstructor(int.class)
															.newInstance(NUM_THREADS);
		
		// Generating an integer array with numbers in the range from LOW to HIGH
		int[] numbers = new int[HIGH-LOW];
		for(int i = 0; i < HIGH - LOW; i++) {
			numbers[i] = LOW + i;
		}

		int result = new SequentialPalindromicCounter().count(numbers);
		long startTime = System.nanoTime();
		int count = counter.count(numbers);
		long endTime = System.nanoTime();
		if (count != result) {
			// Informing when the implementation fails
			System.err.printf("- ERROR: Expected: %d; Found: %d\n", result, count);
		} else {
			// Showing the time taken by the implementation	
			System.out.printf("- SUCCESS: Expected: %d; Found: %d"
					+ "; Elapsed time: %d ms\n", result, count,
					((endTime - startTime) / 1000000));
		}
	}

}