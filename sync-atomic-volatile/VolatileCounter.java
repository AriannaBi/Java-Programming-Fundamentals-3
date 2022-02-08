package counter;

// @NotThreadSafe
public class VolatileCounter implements Counter {
	
    private volatile int value; 
    //The class is not thread safe, because volatile means visible (memory consistency)
    // but not atomicity. It means that when a thread modify the value, it doesn't go into the local cache but it goes
    //directly into memory so every thread can se that and fetch that. 

    //writing an integer in memory is NOT volatile by default, but any writes to a non atomic integer IS atomic. 

    //volatile solved the memory consistency error but not the atomicity problem: it doesn't garantee that accesses to this value
    //is synchronized.
    @Override
    public void increment() {
        value++;
    }

    @Override
    public int value() {
        return value;
    }
}
