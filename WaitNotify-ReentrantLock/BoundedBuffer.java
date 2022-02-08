package buffer;

public interface BoundedBuffer<T> {

    /**
     * Retrieves and removes the first element of this buffer (in FIFO order),
     * waiting if necessary until an element becomes available.
     *
     * @return the first element from the buffer
     * @throws InterruptedException
     *         if interrupted while waiting
     */
    T take() throws InterruptedException;


    /**
     * Inserts the specified element into this buffer, waiting if necessary for
     * space to become available.
     *
     * @param element
     *        the element to add
     * @throws InterruptedException
     *         if interrupted while waiting
     * @throws ClassCastException
     *         if the class of the specified element prevents it from being
     *         added to this queue
     * @throws NullPointerException
     *         if the specified element is null
     * @throws IllegalArgumentException
     *         if some property of the specified element prevents it from being
     *         added to this buffer
     */
    void put(T element) throws InterruptedException;

}
