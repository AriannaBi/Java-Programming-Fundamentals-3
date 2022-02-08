package counter;

// @NotThreadSafe
public class TrivialCounter implements Counter {
	
    private int value;

    //multiple methods can be called by multiple threads and the result is unpredictable:
    //race condition because the output depends on the timing and value can access by multiple threads and we are
    //not putting any lock to synchronized while accessing the shared variable. 

    @Override
    public void increment() {
        value++; //read-modify-write problem: not thread-safe
    }

    @Override
    public int value() { //is not thread safe because I can call increment and then value and at this point is not clear which of the two thread will finish earlier.
        return value;
    }
}
