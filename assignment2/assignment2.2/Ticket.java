package assignment2;

public final class Ticket {

	private static long count = 0;
	private final long id;

	public Ticket() {
		this.id = count++;
	}

	public long getId() {
		return id;
	}

}