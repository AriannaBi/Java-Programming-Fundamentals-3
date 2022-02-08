package assignment3;

public final class Customer implements Runnable {
	private final long id;
	private final Shop shop;
	private Product product;

	public Customer(long id, Shop shop) {
		this.id = id;
		this.shop = shop;
	}

	@Override
	public void run() {
		try {
			this.product = shop.sell();
		} catch(InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	public long getId() {
		return this.id;
	}

	public Product getProduct() {
		return this.product;
	}	
}