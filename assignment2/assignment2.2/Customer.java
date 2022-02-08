package assignment2;

public final class Customer {
	private final long id;
	private Ticket ticket;

	public Customer(long id) {
		this.id = id;
	}

	public void buyTicket(TicketShop ticketShop)
	throws TicketsSoldOutException {
		this.ticket = ticketShop.buy();
	}
	
	public long getId() {
		return id;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
}