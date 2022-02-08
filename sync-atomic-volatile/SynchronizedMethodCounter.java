package counter;

// @ThreadSafe
public class SynchronizedMethodCounter implements Counter {
	
    private int value;
    //synchronized keyword to lock and unlock
    //synchronized lock the current object (this) so we will lock the instance that will use the method.

    @Override
    public synchronized void increment() { 
        value++;
    }

    @Override
    public synchronized int value() {
        return value;
    }
    //if a thread A is executing increment(), then a thread B will not be able to execute value().
    //if I removed synchronized from value, only one thread at the same time can call increment(),
    //but still multiple thread can call value() while another thread is calling increment().
    //So if you don't put synch to the value method you will not have thread safety. 
    // the method value() is thread safe even without the keyword synchronized (value is atomic), 
    // but then is not thrade safe compared with the method increment().





    
}
