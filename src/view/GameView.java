package view;

import java.io.Serializable;

import javax.swing.DefaultListModel;

import main.Player;
import main.Rules;
import main.Square;

/**
 * The GameView represents the User Interface. User can choose which interface,
 * between the text interface and the graphical interface, will display the game
 * before playing.
 * 
 * @author Christophe
 * @version 1.0
 */
public abstract class GameView implements Serializable {

	private static final long serialVersionUID = -2960779974671651086L;

	private int answer;
	private ColorBoard colorBoard;
	private Language language;
	protected Rules rules;
	protected String pathname;
	protected String namePlayer1;
	protected String namePlayer2;
	protected DefaultListModel<String> modelHistoryMoves;

	/**
	 * Get the current color of the board displayed on the user's screen.
	 * 
	 * @return the current board color
	 * @see #setColorBoard(ColorBoard)
	 */
	public ColorBoard getColorBoard() {
		return this.colorBoard;
	}

	/**
	 * The color of the board est choisie par le joueur. It provides a visually
	 * pleasing environment in which to play.The language is the one chosen by the
	 * user. The default language is English.
	 * 
	 * @param cb the color of the board
	 */
	public void setColorBoard(ColorBoard cb) {
		if (cb != null)
			this.colorBoard = cb;
		else
			throw new IllegalArgumentException("GameView : setColorBoard(ColorBoard) : parameter \"cb\" null.");
	}

	/**
	 * Get the current language used by the software to display texts in the
	 * player's language.
	 * 
	 * @return the current board color
	 * @see #setLanguage(Language)
	 */
	public Language getLanguage() {
		return this.language;
	}

	/**
	 * The language is the one chosen by the user. The default language is English.
	 * 
	 * @param lang the game language
	 */
	public void setLanguage(Language lang) {
		if (lang != null)
			this.language = lang;
		else
			throw new IllegalArgumentException("GameView : setLanguage(Language) : parameter \"lang\" null.");
	}

	/**
	 * The rules allows to play correctly.
	 * 
	 * @param rules the game rules
	 */
	public void setRules(Rules rules) {
		if (rules != null)
			this.rules = rules;
		else
			throw new IllegalArgumentException("GameView : setRules(Rules) : parameter \"rules\" null.");
	}

	/**
	 * Sets the data null.
	 */
	public void setDataNull() {
		this.pathname = null;
		this.namePlayer1 = null;
		this.namePlayer2 = null;
	}

	/**
	 * Displays the home page on the same interface.
	 */
	public abstract void printHome();

	/**
	 * Displays the settings on the same interface.
	 */
	public abstract void printSettings();

	/**
	 * Asks for the number of human player in the game. Asks for the name(s) of the
	 * player(s) according to the chosen mode.
	 */
	public abstract void printRetrievingInformations();

	/**
	 * Displays the game board on the same interface.
	 * 
	 * @param squareList the game board
	 * @param current    the current player
	 * @param opponent   the opponent player
	 * @param listMoves  the list of moves history
	 * @param nbMove     the game rounds counter
	 */
	public abstract void printBoard(Square[][] squareList, Player current, Player opponent,
			DefaultListModel<String> listMoves, int nbMove);

	/**
	 * Displays the game board on the same interface.
	 * 
	 * @param playerName1 first player name
	 * @param playerName2 second player name
	 * @param isThereATie {@code true} if both players have won, otherwise
	 *                    {@code false}.
	 */
	public abstract void printEnd(String playerName1, String playerName2, boolean isThereATie);

	/**
	 * Displays the game rules.
	 */
	public abstract void printRules();

	/**
	 * Displays the game options files.
	 */
	public abstract void printFiles();

	/**
	 * Get the choice made by the user according to the interface displayed on his
	 * screen.
	 * 
	 * @return user's answer
	 * @see #setAnswer(int)
	 */
	public int getAnswer() {
		return this.answer;
	}

	/**
	 * Set the user's choice as an answer in order to display another interface.
	 * 
	 * @param ans the choice made by the user
	 */
	public void setAnswer(int ans) {
		this.answer = ans;
	}

	/**
	 * Asks the player to give the start position of the move.
	 * 
	 * @return the start position of the pawn.
	 */
	public abstract String askStartPosition();

	/**
	 * Asks the player to give the finish position of the move.
	 * 
	 * @return the finish position of the pawn.
	 */
	public abstract String askFinishPosition();

	/**
	 * Asks the user to enter the pathname to save the game.
	 * 
	 * @return the pathname of the backup file.
	 */
	public abstract String askPathname();
}