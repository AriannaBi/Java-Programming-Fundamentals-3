package counter;

// @ThreadSafe
public class SynchronizedBlockCounter implements Counter {
	
	private int value;

	@Override
	public void increment() {
		synchronized (this) {
			value++;
		}
	}

	@Override
	public int value() {
		synchronized (this) {
			return value;
		}
	}
	
	//synchronized on this block or on method is equivalent 
}
