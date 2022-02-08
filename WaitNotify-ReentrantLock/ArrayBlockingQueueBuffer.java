package buffer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * A trivial implementation of a {@link BoundedBuffer} based on wrapping the
 * {@link ArrayBlockingQueue}.
 */
public final class ArrayBlockingQueueBuffer<E> implements BoundedBuffer<E> {

    private final ArrayBlockingQueue<E> elements;

    public ArrayBlockingQueueBuffer(final int capacity) {
        elements = new ArrayBlockingQueue<>(capacity);
    }

    @Override
    public void put(final E element) throws InterruptedException {
        elements.put(element);
    }

    @Override
    public E take() throws InterruptedException {
        return elements.take();
    }

}
