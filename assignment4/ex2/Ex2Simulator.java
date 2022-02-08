package ex2;

import java.util.concurrent.CyclicBarrier;

import ex1.*;

//the number of gamers that can play in the arena is fixed
//each game should be played and in the provided order and each game should be played only once (sychronized)
//each gamer should wait for all the other gamers to play the ith game
//two gamers must not play  different games at the same time 
public class Ex2Simulator {

  public static void main(String[] args) {
    new Ex2Simulator().run();
  }

  private final int nGamers = Parameters.getNumberGamers();
  private final Game[] games = new Game[Parameters.getNumberGames()];
  // TODO: instantiate a CyclicBarrier instance
  private final CyclicBarrier barrier = new CyclicBarrier(nGamers, new GamersReady());
  //GamersReady is used as a barrier action. it automatically ensure that this runnable is executed exactly once. 

  //in the cyclic barrier is a happen-before relation ensure that the effect of the executed in the barrier action is visible to all
  //the threads which are in the barrier as soon as the barrier action completes. 
  //in this way there is nopossible data race on the availabled boolean field. 
  public Ex2Simulator() {
    for (int i = 0; i < games.length; i++) {
      games[i] = new Game();
      games[i].setUnavailable();
    }
  }

  private class ArenaGamer implements Runnable {
    @Override
    public void run() {
      try {
        for (int i = 0; i < games.length; i++) {
          // TODO: wait the other gamers before playing the i-th game.
          //       The last thread that gets ready should take care
          //       of making the i-th game available
          barrier.await();
          games[i].play();
        }
      } catch (GameNotAvailableException ex) {
        System.err.println("ERROR: Gamer trying to play an unavailable game");
        System.exit(1);
      } catch (Exception e) {
        System.out.println("Exception not supported");
        System.exit(2);
      }
    }
  }

  private class GamersReady implements Runnable { //making each game step by step availabled
    private int currentGame = 0;

    @Override
    public void run() {
      try {
        Thread.sleep(10);
      } catch (InterruptedException ex) {
        System.out.println("InterruptedException not supported");
        System.exit(1);
      }
      games[currentGame++].setAvailable();
      System.out.println("Gamers are ready to play the game #" + currentGame);
    }
  }

  public void run() {
    for (int i = 0; i < nGamers; i++) {
      new Thread(new ArenaGamer()).start();
    }
  }
}
