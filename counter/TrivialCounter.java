package counter;

// @NotThreadSafe
public class TrivialCounter implements Counter {
	
    private int value;

    @Override
    public void increment() {
        value++;
    }

    @Override
    public int value() {
        return value;
    }
}
