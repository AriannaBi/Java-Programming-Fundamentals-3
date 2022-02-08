package assignment3;

import java.util.concurrent.atomic.AtomicLong;

public final class Product {
	private static final AtomicLong currentId = new AtomicLong();
	private final long id;

	public Product() {
		this.id = currentId.getAndIncrement();
	}

	public long getId() {
		return id;
	}
}