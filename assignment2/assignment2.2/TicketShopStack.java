package assignment2;

// TODO 1 : import the java.util.EmptyStackException class
// TODO 2 : import the java.util.Stack class

public final class TicketShopStack implements TicketShop {
	// TODO 3 : Replace the following fields with a single Stack<Ticket> 
  private final Ticket[] availableTickets;
	private int nextTicket;

	TicketShopStack(int numberOfTickets) {
		// TODO 4 : Replace the following initializations
		//					with the one of the Stack
		this.availableTickets = new Ticket[numberOfTickets];

		// TODO 5 : push tickets onto the top of the Stack
		//					calling Stack.push(Ticket ticket)
		for (int i = 0; i < numberOfTickets; i++) {
			availableTickets[i] = new Ticket();
		}
	}

	public Ticket buy() throws TicketsSoldOutException {
		Ticket ticket = null;

		// TODO 6 : Replace the following logic calling Stack.pop()
		//					to remove and return the ticket at the top of the stack,
		//					assigning it to the "ticket" variable.
		//					You would need to catch the EmptyStackException.
		//					Please do not change the behavior of the program,
		//					if no ticket is available raise a
		//					"TicketsSoldOutException" exception.
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


//stack.pop remove and return the object. and throws an empty stack exception, so we have to catch it and 
//throw a ticketsoldoutexception.

//Implementation:
//if (availableTickets.empty()) {
//	stack.pop
// } else {
	// throw exception sold out
// } NOT avoid races because btw empty and pop we can have an interlieved of threads. and not handle the 
// empty exception error with a catch.

//stack extends vector, and vector is synchronized. While executing pop w don't encouter races
//If we doesn't want a thread-safe implementation, we better use ArrayList.