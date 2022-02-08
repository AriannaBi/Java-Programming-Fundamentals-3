package counter;

// @NotThreadSafe
public class VolatileCounter implements Counter {
	
    private volatile int value;

    @Override
    public void increment() {
        value++;
    }

    @Override
    public int value() {
        return value;
    }
}
