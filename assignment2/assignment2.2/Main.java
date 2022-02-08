package assignment2;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

public final class Main {

	private static final int NUMBER_OF_SELLERS = 8;
	private static final int NUMBER_OF_TICKETS = 10_000;
	private static final int NUMBER_OF_CUSTOMERS = 10_000;

	private static final int CUSTOMERS_PER_SELLER = (int) Math.ceil(
		(double) NUMBER_OF_CUSTOMERS / NUMBER_OF_SELLERS
	);

	public static void main(String[] args) throws Exception  {
		Class<? extends TicketShop> ticketShopClass = null;

		String ticketShopClassName = args.length > 0
			? args[0] :
			"TicketShopImpl";
		try {
			ticketShopClass = Class.forName("assignment2." + ticketShopClassName)
												 		 .asSubclass(TicketShop.class);
		} catch (ClassNotFoundException ex) {
			System.err.println(
				"- ERROR: the provided command line parameter is not valid. " +
				"Cannot find a class named \"" + ticketShopClassName + "\""
			);
			return;
		}

		Thread[] threads = new Thread[NUMBER_OF_SELLERS];
		TicketShop ticketShop = ticketShopClass
																		.getDeclaredConstructor(int.class)
																		.newInstance(NUMBER_OF_TICKETS);
		Customer[] customers = new Customer[NUMBER_OF_CUSTOMERS];

		for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
			customers[i] = new Customer(i);
		}

		// Sellers work in parallel
		for (int i = 0; i < NUMBER_OF_SELLERS; i++) {
    	int start = i * CUSTOMERS_PER_SELLER;
			int end = Math.min(start + CUSTOMERS_PER_SELLER, customers.length);

			Seller seller = new Seller(customers, start, end, ticketShop);
			threads[i] = new Thread(seller);
		}

		long startTime = System.nanoTime();

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		long stopTime = System.nanoTime();

		// Assert that the same ticket has been sold only once
		Map<Ticket, Integer> duplicates = new HashMap<Ticket, Integer>();
    for (int i = 0; i < customers.length; i++) {
			Customer customer = customers[i];
			Ticket ticket = customer.getTicket();

			if (ticket != null) {
				int numberOfOccurences = duplicates.getOrDefault(ticket, 0);
				duplicates.put(ticket, numberOfOccurences + 1);
			}
    }

		for(Map.Entry<Ticket, Integer> entry : duplicates.entrySet()) {
      Ticket ticket = entry.getKey();
      int times = entry.getValue();
      
			if (times > 1) {
				System.err.println(
					"- ERROR: Unique Ticket number " + ticket.getId() +
					" has been sold " + times + " times."
				);
			}
    }

		System.out.println(
			"\nExecution time: " + (
				TimeUnit.MILLISECONDS.convert(stopTime-startTime, TimeUnit.NANOSECONDS)
			) + " ms"
		);
	}

}