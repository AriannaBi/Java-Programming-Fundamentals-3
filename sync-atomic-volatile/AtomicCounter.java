package counter;

import java.util.concurrent.atomic.AtomicInteger;

// @ThreadSafe
public class AtomicCounter implements Counter {

	private final AtomicInteger value = new AtomicInteger();
	//atomicity on a integer ensure visibility and atomicity.
	//visibility is ensure by the happens-before relationship that exists btw atomic counter incrementandget and the other methods.

	@Override
	public void increment() {
		value.incrementAndGet(); //ensure that incrementing and reading is totally atomic.
	}

	@Override
	public int value() {
		return value.get();
	}
	
}
