package assignment2;

public final class Seller implements Runnable  {

	private final Customer[] customers;
	private final int low, high;
	private final TicketShop ticketShop;

	public Seller(
		Customer[] customers,
		int low,
		int high,
		TicketShop ticketShop
	) {
		this.customers = customers;
		this.low = low;
		this.high = Math.min(high, customers.length);
		this.ticketShop = ticketShop;
	}

	@Override
	public void run() {
		for (int i = low; i < high; i++) {
			try {
				customers[i].buyTicket(ticketShop);
			} catch(TicketsSoldOutException ex) {
				System.out.println(
					"Cannot buy ticket for customer " + customers[i].getId() +
					". Shop out of stock."
				);
			}
		}			
	}
}
