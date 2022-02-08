package ex1;

public class CatalogImpl implements Catalog {
	private final Game[] games;
	private int gameToReplace = 0;

	public CatalogImpl(int numberOfGames) {
		games = new Game[numberOfGames];
		for (int i = 0; i < games.length; i++) {
			games[i] = new Game();
		}
	}

  public int size() {
    return games.length;
  }

    //this is not thread-safe because we can have an out of bound exception. 
    //also if one thread executes publish and another executes play, the second can play a game which has been
    //settled as unavailabled: game not availabled exception.
  public void publish(Game newGame) throws InterruptedException {
    games[gameToReplace].setUnavailable();
    Thread.sleep(10);
    games[gameToReplace] = newGame;
    Thread.sleep(10);
    gameToReplace++;
    Thread.sleep(10);
    if (gameToReplace >= games.length) {
      gameToReplace = 0;
    }
    newGame.setAvailable();
	}

	public void play(int index) throws InterruptedException,
      GameNotAvailableException {
		games[index].play();
	}
}
