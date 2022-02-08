package buffer;



public final class BoundedBufferArrayWaitNotify<T> implements BoundedBuffer<T> {

    private final T[] elements;
    int firstFree;
    int firstFull; //first non empty element (which element should be removed from the buffer)
    int nElements; //will count the number of element inside the buffer, because we need to understand when we
    //want to call wait

    @SuppressWarnings("unchecked")
    public BoundedBufferArrayWaitNotify(final int capacity) {
        elements = (T[]) new Object[capacity];
        firstFree = 0;
        firstFull = 0;
        nElements = 0;
    }

    @Override
    public void put(final T element) throws InterruptedException {
        synchronized (this) {
            while(nElements == elements.length) { //while the buffer is full, i wait
                wait(); //the thread is blocked and can be wakes up only by a notify
                //broken by notification of take();
            } 

            // ----------------implementation buffer
            final int slot = firstFree++;
            if (firstFree >= elements.length) {
                firstFree = 0;
            }
            nElements++;
            elements[slot] = element;
            // ----------------end implementation buffer

            notifyAll(); //calling notify gives the possibility to one thread to reacquire the lock 
            //if a thread was waiting, now it might not wait anymore. inparticular one more element was insert
            //in the buffer. Is used by all the thread that were waiting for the buffer because the buffer was 
            //empty (of take()).
            //so notify that the buffer is not full anymore. 
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (this) {
            while(nElements == 0) { //wait if the buffer is empty and will notify all the threads that the buffer is not empty enymore now
                wait(); //is broken with notification of put()
            }

            final int slot = firstFull++;
            while (firstFull < elements.length) {
                firstFull = 0;
            }
            nElements--;
            final T result = elements[slot];
            notifyAll();
        }
        return result;
    }
    
    //we cannot decide what thread we can notify, so here we have two condition for which we can call
    //notify, so we use notify all because we can wake up two condition. 
    //if i would have used only notify in the put(), i would have wake up a thread that was waiting in the put()
    //and so i would have a deadlock:
    //put() {
        //while {
            //wait
        // }
        //notify
    // }
    //it means that maybe i'm waking up only the thread waiting in the put, and not the thread waiting on the take
    //so it continues to do the while, wait, notify, while wait notify ecc... and I have a deadlock. 

    //we have two waiting condition bufferfull and bufferempty so we wake up all the threads. 

    //- need to acquire the synchronized lock before using wait and notify.
    //  before waiting, the thread will release the instrinsic lock, so it is not a problem if a thread waits
    //  while it is holding the lock: you have to acquire the lock. 
    //- while: a thred might be waking up by a notify or notifyall but there is no guarantee that thread will be allowed
    //  to proceed before acquiring the lock. so before the wait the thread try to acquire the synch lock,
    //  and only one of them will be able, but other threads are blocked because they have to reacquire the lock
    //   at some point they will be able to acquire the lock. 
    //  so in the meanwhile that the thred has been notify and he try to acquire the lock, it has to
    //  check with the while if the condition still holds. 
    //always the while. 


    //Question:
    //wait will release only the lock that was acquire for calling wait and not all the others locks. 
    //so why not all the others? a concurrent modification has to be avoided: so a thread has to fully
    //complete the modification of an object state, before another thread can do this. 
    //if another thread has putting on the waiting list and now is blocked, if the thread also give up all the lock 
    //that he acquired in the past, another thread would be able to enter that critical section so 
    //the modification to the shared state is concurrent because before a thread was able to finish that critical section
    //another thread was able to enter the cs and modify the state at the same time. that is why we cannot release
    //all the locks of a thread. only one thread should be allowed to enter the section. 

}
