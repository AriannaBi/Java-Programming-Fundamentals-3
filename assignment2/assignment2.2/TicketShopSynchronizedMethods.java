package assignment2;

public final class TicketShopSynchronizedMethods implements TicketShop {

  private final Ticket[] availableTickets;
	private int nextTicket;

	TicketShopSynchronizedMethods(int numberOfTickets) {
		this.availableTickets = new Ticket[numberOfTickets];

		for (int i = 0; i < numberOfTickets; i++) {
			availableTickets[i] = new Ticket();
		}
	}

	public Ticket buy() throws TicketsSoldOutException { //only put the synchronized keyword in the method
		//so it avoids races when multiple threads try to access nextTicket and availableTickets.
		Ticket ticket = null;

		if (nextTicket < availableTickets.length) {
			ticket = availableTickets[nextTicket];
			nextTicket += 1;
		} else {
			throw new TicketsSoldOutException();
		}
		
		processOrder(ticket);

		return ticket;
	}

	public void processOrder(Ticket ticket) {
		// Simulate ticket elaboration with waiting time
		try {
			Thread.sleep(1);
		} catch (Exception ex) {}
	}

}