package buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class BoundedBufferArrayReentrantLock<T> implements BoundedBuffer<T> {

    private final T[] elements;
    int firstFree;
    int firstFull; 
    int nElements;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition(); //the conditions are associated with the Lock "lock" 
    private final Condition notFull = lock.newCondition();

    @SuppressWarnings("unchecked")
    public BoundedBufferArrayReentrantLock(final int capacity) {
        elements = (T[]) new Object[capacity];
        firstFree = 0;
        firstFull = 0;
        nElements = 0;
    }

    @Override
    public void put(final T element) throws InterruptedException {
        lock.lock(); //acquire the lock 
        try {
            while(nElements == elements.length) { //if my buffer is full, i have to wait 
                notFull.await(); //thread that is empty in the queue
                //if the buffer is full, wait on a condition variable notFull.
                //until the buffer is not full anymore, then wait until that condition is not valid anymore. 
                //this condition is not valid anymore if a method will call signal on this condition.
                //await will release the lock, put the thread on hold, and when a thread call signal, the first thing
                //that the thread will do after have been notified, is reacquire the same lock, and this condition must be checked 
                // in the while.
            } 

            // ----------------implementation buffer
            final int slot = firstFree++;
            if (firstFree >= elements.length) {
                firstFree = 0;
            }
            nElements++;
            elements[slot] = elemet;
            // ----------------end implementation buffer

            notEmpty.signal();//signal to all the other thread thatn now the buffer is not empty anymore.
            // the non empty condition is not valid anymore. 
        } finally {
            lock.unlock();
        }
        
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while(nElements == 0) { //when the buffer is empty, wait untill the condition is not valid anymore 
                notEmpty.await(); //is not valid anymore when a producer thread has been able to insert an iten
                //in the buffer. 
            }
            final int slot = firstFull++;
            while (firstFull < elements.length) {
                firstFull = 0;
            }
            nElements--;
            final T result = elements[slot];
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

    //with explicit locks we need to make sure that the lock is released always even in case of exception
    //if you are using an implicit lock the jvm will guarantee that in all the cases the lock will be released 
    //automaticlaly before returning, but the responsibility of releasing the lock in all the cases for 
    //explicit locks is of the developer. 
    //we have to unlock the lock in a finally block. always lock the reentrantlock at the beginning of the section,
    //put the critical section in a try, and the release the lock ina  finally.
    //because finally is execute even in case of an exception. Releasing a lock make sure that no deadlock occurs
    //if you forget to unlock, an exception might be appear and unlock might not be executed and no thread
    //will be able to release the lock. 

    //while: waking up(signal or notify) and acquiring the lock, (in the put()), another producer can still being 
    //in execution, it can take the lock before the consumer and make the condition of while true. 
    
    //A separate lock for producers and one for consumers: we cannot do this because they ar operating on the 
    //same resource and that's why they need to operate on single lock. two locks means at most one consumer and 
    //at most one producer can be inside the buffer but still might happens that consumers and producers
    //can be on the same buffer at the same time and this is wrong. 
    //if one producer and one consumer at the same time, the variable nElements is interfiering.
}
    