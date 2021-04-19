package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import view.GameView;
import view.Language;

/**
 * ZenGame is the class that will read the options of the game, and will create
 * with them an instance of Game. It is the link between the model part of the
 * program and the visual part.
 * 
 * @author Christophe
 * @version 1.0
 */
public class ZenGame {

	/**
	 * The game mode (HH, HA, AA).
	 * 
	 * @see Mode
	 */
	private Mode mode;

	/**
	 * The game language (English, French, Spanish).
	 * 
	 * @see Language
	 */
	private Language language;

	/**
	 * The game view, the interface on which the game will be displayed
	 * (Console-line, Graphical).
	 * 
	 * @see GameView
	 */
	private GameView gv;

	/**
	 * {@code True} if the user has chosen the Graphical User Interface, otherwise
	 * {@code false}.
	 */
	private boolean isGraphic;

	/**
	 * The first player name.
	 */
	private String playerName1;

	/**
	 * The second player name.
	 */
	private String playerName2;

	/**
	 * The game on which the players are going to compete.
	 * 
	 * @see Game
	 */
	private Game gameplay;

	/**
	 * The game board.
	 * 
	 * @see Square
	 */
	private Square[][] squareList;

	/**
	 * Initializes a newly created ZenGame object that it represents the launch of
	 * the Zen game.
	 * 
	 * @param fileName    pathname of the configuration file
	 * @param playerName1 first player name
	 * @param playerName2 second player name
	 * @param language    the game language
	 * @param mode        the game mode
	 * @param gv          the game view displayed on the user's screen
	 * @param isGraphic   {@code true} if the user choosed the graphical view,
	 *                    otherwise {@code false}
	 * @throws IOException            If there is a Input/Output error (exception)
	 * @throws ClassNotFoundException If the class is not found
	 */
	public ZenGame(String fileName, String playerName1, String playerName2, Language language, Mode mode, GameView gv,
			boolean isGraphic) {
		if (language == null || gv == null) {
			throw new IllegalArgumentException(
					"ZenGame : ZenGame(String, String, String, Language, Mode, GameView, boolean) : parameter null.");
		} else if (fileName == null) {
			this.playerName1 = playerName1;
			this.playerName2 = playerName2;
			this.language = language;
			this.mode = mode;
			this.gv = gv;
			this.isGraphic = isGraphic;
		}

		try {
			this.configure(fileName);

			// Game on.
			this.gameplay.start();
		} catch (ClassNotFoundException e) {
			String message = "ZenGame : configure(String) : the given pathname is incorrect.";
			System.err.println(message + "\nMessage : " + e.getMessage());
		} catch (IOException e) {
			String message = "ZenGame : configure(String) : the given pathname is incorrect.";
			System.err.println(message + "\nMessage : " + e.getMessage());
		}
	}

	/**
	 * Private method which configures the game settings from a configuration file.
	 * 
	 * @param fileName pathname of the configuration file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void configure(String fileName) throws ClassNotFoundException, IOException {
		if (fileName != null) {
			// Retrieves the game previously saved in the file passed given by the user.
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.gameplay = (Game) objectInputStream.readObject();
			objectInputStream.close();
		} else {
			this.squareList = new Square[11][11];

			// Placing all pawns on the gameboard.
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					if ((i == 0 && j == 0) || (i == 4 && j == 1) || (i == 6 && j == 1) || (i == 2 && j == 3)
							|| (i == 8 && j == 3) || (i == 0 && j == 5) || (i == 10 && j == 5) || (i == 2 && j == 7)
							|| (i == 8 && j == 7) || (i == 4 && j == 9) || (i == 6 && j == 9) || (i == 10 && j == 10))
						this.squareList[i][j] = new Square(new Pawn(ColorPawn.WHITE));
					else if ((i == 5 && j == 0) || (i == 10 && j == 0) || (i == 3 && j == 2) || (i == 7 && j == 2)
							|| (i == 1 && j == 4) || (i == 9 && j == 4) || (i == 1 && j == 6) || (i == 9 && j == 6)
							|| (i == 3 && j == 8) || (i == 7 && j == 8) || (i == 0 && j == 10) || (i == 5 && j == 10))
						this.squareList[i][j] = new Square(new Pawn(ColorPawn.BLACK));
					else if (i == 5 && j == 5)
						this.squareList[i][j] = new Square(new Pawn(ColorPawn.RED));
					else
						this.squareList[i][j] = new Square();
				}
			}

			File dir = new File("data/");
			String[] children = dir.list();
			String path = "data/config" + (children.length + 1) + ".txt";

			// Creates a new form of Game after initializing all the necessary data.
			this.gameplay = new Game(this.squareList, path, this.playerName1, this.playerName2, this.mode,
					this.language, this.gv, isGraphic);
		}
	}
}