package ex1;

public interface Catalog {
	void publish(Game newGame) throws InterruptedException;
	void play(int index) throws InterruptedException, GameNotAvailableException;
	int size();
}
