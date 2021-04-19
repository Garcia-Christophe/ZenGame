package main;

/**
 * The interface represents what a Game should contain. Specifically, the Game
 * must have a {@link #start()} method and a {@link #endOfGame()} method.
 * 
 * @author Christophe
 * @version 1.0
 */
public interface IGame {

	/**
	 * The loop of the game. It is the method that calls the others in order to make
	 * the game evolve.
	 */
	public void start();

	/**
	 * Ends the game, and asks to restart or not a game.
	 */
	public void endOfGame();
}