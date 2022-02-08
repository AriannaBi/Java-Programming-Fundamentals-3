package assignment2;

public final class TicketShopSynchronizedBlocks implements TicketShop {

  private final Ticket[] availableTickets;
	private int nextTicket;

	TicketShopSynchronizedBlocks(int numberOfTickets) {
		this.availableTickets = new Ticket[numberOfTickets];

		for (int i = 0; i < numberOfTickets; i++) {
			availableTickets[i] = new Ticket();
		}
	}

	public Ticket buy() throws TicketsSoldOutException {
		Ticket ticket = null;

		//add a synchronized block to avoid races
		// synchronized (this) {
			if (nextTicket < availableTickets.length) {
				ticket = availableTickets[nextTicket];
				nextTicket += 1;
			} else {
				throw new TicketsSoldOutException();
			}
		// }

		//Compare performance of methods and blocks synchronized: 
		//the synchro blocks perform better in terms of execution time, because 
		//it increase the amout of parallelizable code.
		//not synchronized the processOrder method because it takes a lot of time.
		
		
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