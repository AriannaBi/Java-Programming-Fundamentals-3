package ex1;

public final class Game {
	private boolean available = true;

	public void setAvailable() {
		this.available = true;
	}

	public void setUnavailable() {
		this.available = false;
	}

	public void play() throws InterruptedException, GameNotAvailableException {
		if (!available) {
      throw new GameNotAvailableException();
		}
    Thread.sleep(10); // Simulate playing time
	}
}
