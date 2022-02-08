package assignment2;

public final class TicketShopImpl implements TicketShop {

  private final Ticket[] availableTickets;
	private int nextTicket;

	TicketShopImpl(int numberOfTickets) {
		this.availableTickets = new Ticket[numberOfTickets];

		for (int i = 0; i < numberOfTickets; i++) {
			availableTickets[i] = new Ticket();
		}
	}

	public Ticket buy() throws TicketsSoldOutException {
		Ticket ticket = null;

		if (nextTicket < availableTickets.length) { //A		//D
			ticket = availableTickets[nextTicket];  //B		//E
			nextTicket += 1;						//C		//F
		} else {
			throw new TicketsSoldOutException();
		}

		//execution that lead to race error: ADBECF 
		//execution that lead to excpection error: ADBCEF

		//Common question:
		//nextTicket >= availableTickets.length; then ABCDEF cannot throws an exception because D will never 
		//be executed. 
		
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