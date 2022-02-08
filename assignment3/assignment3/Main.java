package assignment3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

public final class Main {
	private static final int NUMBER_OF_SELLERS = 1000;
	private static final int NUMBER_OF_CUSTOMERS = 1000;

	public static void main(String[] args) throws Exception  {
		if (NUMBER_OF_SELLERS < NUMBER_OF_CUSTOMERS) {
			System.err.println(
				"- ERROR: NUMBER_OF_SELLERS must be equal to" +
				"or greater than NUMBER_OF_CUSTOMERS."
			);
			System.exit(1);
		}

		Class<? extends Shop> shopClass = null;

		String shopClassName = args.length > 0
			? args[0]
			: "ShopImpl";
		try {
			shopClass = Class.forName("assignment3." + shopClassName)
												 		 .asSubclass(Shop.class);
		} catch (ClassNotFoundException ex) {
			System.err.println(
				"- ERROR: the provided command line parameter is not valid. " +
				"Cannot find a class named \"" + shopClassName + "\""
			);
			System.exit(2);
		}

		List<Thread> threads = new ArrayList<Thread>(
			NUMBER_OF_SELLERS + NUMBER_OF_CUSTOMERS
		);
		Shop shop = shopClass
											.getDeclaredConstructor()
											.newInstance();
		Seller[] sellers = new Seller[NUMBER_OF_SELLERS];
		Customer[] customers = new Customer[NUMBER_OF_CUSTOMERS];

		for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
			customers[i] = new Customer(i, shop);
			threads.add(new Thread(customers[i]));
		}

		for (int i = 0; i < NUMBER_OF_SELLERS; i++) {
			sellers[i] = new Seller(i, shop);
			threads.add(new Thread(sellers[i]));
		}

		Collections.shuffle(threads);

		long startTime = System.nanoTime();
		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}
		long endTime = System.nanoTime();

		// assert that the same product has been sold only once
		Map<Product, Integer> duplicates = new HashMap<Product, Integer>();
    for (int i = 0; i < customers.length; i++) {
			Customer customer = customers[i];
			Product product = customer.getProduct();

			if (product != null) {
				int numberOfOccurences = duplicates.getOrDefault(product, 0);
				duplicates.put(product, numberOfOccurences + 1);
			}
    }

		boolean errors = false;
		for(Map.Entry<Product, Integer> entry : duplicates.entrySet()) {
      Product product = entry.getKey();
      int times = entry.getValue();
      
			if (times > 1) {
				errors = true;
				System.err.println(
					"- ERROR: Unique product number " + product.getId() +
					" has been sold " + times + " times."
				);
			}
    }

		System.out.println(
			"\nNumber of products sold: " + duplicates.size() +
			"\nExecution time: " + (
				TimeUnit.MILLISECONDS.convert(endTime-startTime, TimeUnit.NANOSECONDS)
			) + " ms"
		);

		if (errors) {
			System.exit(3);
		}
	}
}