package assignment3;

import java.util.Queue;
import java.util.LinkedList;

public final class ShopImpl implements Shop {
	private final Queue<Product> warehouse = new LinkedList<Product>();

	public void buy(Product product) throws InterruptedException {
		processOrder();
		warehouse.add(product);
	}

	public Product sell() throws InterruptedException {
		Product productToSell;
		processOrder();
		while(warehouse.size() == 0) {
			// waiting for a product
		}
		productToSell = warehouse.poll();
		processOrder();
		return productToSell;
	}

	private static void processOrder() throws InterruptedException {
		// simulating waiting time
		Thread.sleep(100);
	}
}
