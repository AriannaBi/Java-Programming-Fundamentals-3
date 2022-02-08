package assignment3;

public final class Seller implements Runnable {
	private final long id;
	private final Shop shop;

	public Seller(long id, Shop shop) {
		this.id = id;
		this.shop = shop;
	}

	@Override
	public void run() {
		try {
			shop.buy(new Product());
		} catch(InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}
