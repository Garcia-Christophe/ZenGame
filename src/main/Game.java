package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import view.ConsoleView;
import view.GameView;
import view.Language;

/**
 * The Game class is the one that will contain the game loop. It launches the
 * party, finishes it and can save it.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Game implements IGame, Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -3058613038998287100L;

	/**
	 * The game board.
	 * 
	 * @see Square
	 */
	private Square[][] squareList;

	/**
	 * The first player participating to the game.
	 * 
	 * @see Player
	 */
	private Player captain;

	/**
	 * The second player participating to the game.
	 * 
	 * @see Player
	 */
	private Player auto;

	/**
	 * The player who's playing.
	 * 
	 * @see Player
	 */
	private Player current;

	/**
	 * Game view, the interface on which the game will be displayed (Console-line,
	 * Graphical).
	 * 
	 * @see GameView
	 */
	private GameView view;

	/**
	 * The move which is currently being made.
	 * 
	 * @see Move
	 */
	private Move currentMove;

	/**
	 * The game rules.
	 * 
	 * @see Rules
	 */
	private Rules rules;

	/**
	 * {@code True} if the user has chosen the graphical user interface, otherwise
	 * {@code false}.
	 */
	private boolean isGraphic;

	/**
	 * The number of hours spent on this game.
	 */
	private int hours;

	/**
	 * The number of minutes spent (reset after 60) on this game.
	 */
	private int minutes;

	/**
	 * The number of seconds spent (reset after 60) on this game.
	 */
	private int seconds;

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
	 * The name of the path of the access of the backup file.
	 */
	private String pathname;

	/**
	 * All moves are saved in order to see it later if the user wants it.
	 */
	private DefaultListModel<String> modelHistoryMoves;

	/**
	 * All moves are saved, the board at the move time too.
	 */
	private ArrayList<String> historyBoard;

	/**
	 * All positions of the pawns of the players.
	 * 
	 * @see Position
	 */
	private ArrayList<Position> positionsPawnsListPlayer;

	/**
	 * All positions already checked in a recursive method in order to know the
	 * pawns neighbors (i.e. the current player chain pawns length).
	 * 
	 * @see Position
	 */
	private ArrayList<Position> positionsCountedListPlayer;

	/**
	 * The color of the pawn which is being checked.
	 * 
	 * @see ColorPawn
	 */
	private ColorPawn colorPawnNeighbor;

	/**
	 * The largest chain pawns length of the player.
	 */
	private int largestChainLengthPlayer;

	/**
	 * The counter of the chain pawns length of the player. If it's more than
	 * {@link #largestChainLengthPlayer}, then {@link #largestChainLengthPlayer}
	 * becomes the counterChainLengthPlayer.
	 */
	private int counterChainLengthPlayer;

	/**
	 * {@code True} if the user has chosen to save his game by writing "files",
	 * otherwise {@code false}.
	 */
	private boolean hasChosenFiles;

	/**
	 * Initializes a newly created Game object that it represents the gameplay of
	 * Zen game.
	 * 
	 * @param squareList  the game board
	 * @param pathname    the backup file of the game.
	 * @param playerName1 first player name
	 * @param playerName2 second player name
	 * @param mode        the game mode
	 * @param language    the game language
	 * @param gv          the game view displayed on the user's screen
	 * @param isGraphic   {@code true} if the user choosed the graphical view,
	 *                    otherwise {@code false}
	 */
	public Game(Square[][] squareList, String pathname, String playerName1, String playerName2, Mode mode,
			Language language, GameView gv, boolean isGraphic) {
		if (squareList == null || squareList.length != 11 || squareList[0].length != 11 || pathname == null
				|| playerName1 == null || playerName2 == null || mode == null || language == null || gv == null)
			throw new IllegalArgumentException(
					"Game : Game(Square[][], String, String, String, Mode, Language, GameView, boolean) : invalid parameter.");
		else {
			this.squareList = new Square[squareList.length][squareList[0].length];
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] != null)
						this.squareList[i][j] = squareList[i][j];
					else
						throw new IllegalArgumentException(
								"Game : Game(Square[][], String, String, String, Mode, Language, boolean) : a Square ("
										+ i + "," + j + ") in the \"squareList\" array is null.");
				}
			}

			this.pathname = pathname;
			this.language = language;
			this.isGraphic = isGraphic;
			this.rules = new Rules();
			this.rules.setlanguage(this.language);
			this.view = gv;
			this.view.setRules(this.rules);
			this.modelHistoryMoves = new DefaultListModel<String>();
			this.historyBoard = new ArrayList<String>();
			this.positionsPawnsListPlayer = new ArrayList<Position>();
			this.positionsCountedListPlayer = new ArrayList<Position>();

			// Initializing players.
			this.mode = mode;
			if (this.mode == Mode.HH) {
				this.captain = new HumanPlayer(playerName1, this.view, isGraphic);
				this.auto = new HumanPlayer(playerName2, this.view, isGraphic);
			} else if (this.mode == Mode.AA) {
				this.captain = new AutoPlayer(playerName1, this.rules);
				this.auto = new AutoPlayer(playerName2, this.rules);
			} else {
				this.captain = new HumanPlayer(playerName1, this.view, isGraphic);
				this.auto = new AutoPlayer(playerName2, this.rules);
			}
			this.captain.setNbPawns(13);
			this.auto.setNbPawns(13);

			// The player who starts is the one who has the white pawns, randomly assigned.
			int random = (int) (Math.random() * 2);
			if (random == 0) {
				this.captain.setColor(ColorPawn.WHITE);
				this.auto.setColor(ColorPawn.BLACK);
				this.current = this.captain;
			} else {
				this.captain.setColor(ColorPawn.BLACK);
				this.auto.setColor(ColorPawn.WHITE);
				this.current = this.auto;
			}
		}
	}

	@Override
	public void start() {

		// Shows first the game description and its rules.
		System.out.println(this);
		int nbMove = 0;

		// Displays the game board for the first time.
		this.view.printBoard(this.squareList, this.current, (this.current == this.captain) ? this.auto : this.captain,
				this.modelHistoryMoves, nbMove);

		// While the current player didn't form a continuous chain, the game continues.
		while (!this.hasCurrentPlayerWon()) {
			this.hasChosenFiles = false;

			// Asks and analises the current move.
			if (this.current instanceof AutoPlayer)
				((AutoPlayer) this.current).setSquareList(this.squareList);
			this.currentMove = this.readMove(this.current);
			this.analyzeMove(this.currentMove);

			// Counts the round.
			if (!this.hasChosenFiles && this.current == this.auto)
				nbMove++;

			// Saves the current move, so that the players can see it again later.
			if (!this.hasChosenFiles)
				this.modelHistoryMoves.addElement(this.currentMove.toString());

			// Change current player only if he didn't win.
			if (!this.hasChosenFiles && !this.hasCurrentPlayerWon())
				this.changeCurrent();

			// Displays the game board each round.
			this.view.printBoard(this.squareList, this.current,
					(this.current == this.captain) ? this.auto : this.captain, this.modelHistoryMoves, nbMove);
			if (!this.hasChosenFiles && !this.isGraphic) {
				this.historyBoard.add(((ConsoleView) this.view).gameBoardToString(this.squareList));
				((ConsoleView) this.view).setHistoryBoard(this.historyBoard);
			}
		}

		// Ends this game.
		this.endOfGame();
	}

	/**
	 * Change the current player.
	 */
	private void changeCurrent() {
		this.current = (this.current == this.captain) ? this.auto : this.captain;
	}

	/**
	 * Analyzes the move of the pawn chosen by the current player.
	 * 
	 * @param move the move of the pawn
	 */
	public void analyzeMove(Move move) {
		if (move == null)
			throw new IllegalArgumentException("Game : analyzeMove(Move) : parameter \"move\" null.");
		else {
			if (this.hasChosenFiles) {
				if (this.view.getAnswer() == 1 || this.view.getAnswer() == 3)
					this.endOfGame();
				else if (this.view.getAnswer() == 2) {
					this.saveGame();
					System.out.println("Rentre dans save Game");
				}
			} else {
				Player opponent = this.current == this.captain ? this.auto : this.captain;
				Pawn pawnCaptured = this.squareList[move.getFinishPosition().getX()][move.getFinishPosition().getY()]
						.getPawn();
				Pawn pawnMoved = this.squareList[move.getStartPosition().getX()][move.getStartPosition().getY()]
						.getPawn();

				// Moves the pawn played.
				this.squareList[move.getFinishPosition().getX()][move.getFinishPosition().getY()].setPawn(pawnMoved);
				this.squareList[move.getStartPosition().getX()][move.getStartPosition().getY()].setPawn(null);

				// Updates the number of pawns of each other according to the pawn captured.
				if (pawnCaptured != null) {
					if (pawnCaptured.getColor() == this.current.getColor() || pawnCaptured.getColor() == ColorPawn.RED)
						this.current.setNbPawns(this.current.getNbPawns() - 1);
					if (pawnCaptured.getColor() == opponent.getColor() || pawnCaptured.getColor() == ColorPawn.RED)
						opponent.setNbPawns(opponent.getNbPawns() - 1);
				}

				// Updates the chain length of pawns of each other.
				this.largestChainLengthPlayer = 0;
				this.colorPawnNeighbor = this.current.getColor();
				for (int i = 0; i < 11; i++) {
					for (int j = 0; j < 11; j++) {
						if (this.squareList[i][j].isBusy(this.current.getColor())
								|| this.squareList[i][j].isBusy(ColorPawn.RED))
							this.positionsPawnsListPlayer.add(new Position(i, j));
					}
				}
				while (!this.positionsPawnsListPlayer.isEmpty()) {
					this.counterChainLengthPlayer = 0;
					this.setChainLengthPlayer(this.positionsPawnsListPlayer.get(0));

					// Updates the largest pawns chain length
					if (this.counterChainLengthPlayer >= this.largestChainLengthPlayer)
						this.largestChainLengthPlayer = this.counterChainLengthPlayer;
				}
				while (!this.positionsCountedListPlayer.isEmpty())
					this.positionsCountedListPlayer.remove(0);

				// Sets the largest pawns chain length of the current player.
				this.current.setChainLength(this.largestChainLengthPlayer);

				this.largestChainLengthPlayer = 0;
				this.colorPawnNeighbor = opponent.getColor();
				for (int i = 0; i < 11; i++) {
					for (int j = 0; j < 11; j++) {
						if (this.squareList[i][j].isBusy(opponent.getColor())
								|| this.squareList[i][j].isBusy(ColorPawn.RED))
							this.positionsPawnsListPlayer.add(new Position(i, j));
					}
				}
				while (!this.positionsPawnsListPlayer.isEmpty()) {
					this.counterChainLengthPlayer = 0;
					this.setChainLengthPlayer(this.positionsPawnsListPlayer.get(0));

					// Updates the largest pawns chain length
					if (this.counterChainLengthPlayer >= this.largestChainLengthPlayer)
						this.largestChainLengthPlayer = this.counterChainLengthPlayer;
				}
				while (!this.positionsCountedListPlayer.isEmpty())
					this.positionsCountedListPlayer.remove(0);

				// Sets the largest pawns chain length of the opponent player.
				opponent.setChainLength(this.largestChainLengthPlayer);
			}
		}
	}

	/**
	 * Calculates the number of neighbors the pawn at the passed in parameter
	 * position has.
	 * 
	 * @param position the position where is the pawn which is in the currently
	 *                 calculated chain
	 */
	private void setChainLengthPlayer(Position position) {
		if (position == null) {
			throw new IllegalArgumentException("Game : setChainLengthPlayer(Position) : parameter \"position\" null.");
		} else {
			int x = 0;
			int y = 0;
			boolean isAlreadyChecked = false;
			this.counterChainLengthPlayer++;
			this.positionsPawnsListPlayer.remove(position);

			// Checks if there's a neighbor above the pawn.
			boolean aboveOk = false;
			if (position.getY() - 1 >= 0) {
				aboveOk = true;

				// Above.
				if (squareList[position.getX()][position.getY() - 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX()][position.getY() - 1].isBusy(ColorPawn.RED)) {
					x = position.getX();
					y = position.getY() - 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;
			}

			// Checks if there's a neighbor to the left of the pawn.
			boolean leftOk = false;
			if (position.getX() - 1 >= 0) {
				leftOk = true;

				// Left.
				if (squareList[position.getX() - 1][position.getY()].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() - 1][position.getY()].isBusy(ColorPawn.RED)) {
					x = position.getX() - 1;
					y = position.getY();
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;

				// Top left.
				if (aboveOk && (squareList[position.getX() - 1][position.getY() - 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() - 1][position.getY() - 1].isBusy(ColorPawn.RED))) {
					x = position.getX() - 1;
					y = position.getY() - 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;
			}

			// Checks if there's a neighbor to the right of the pawn.
			boolean rightOk = false;
			if (position.getX() + 1 < 11) {
				rightOk = true;

				// Right.
				if (squareList[position.getX() + 1][position.getY()].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() + 1][position.getY()].isBusy(ColorPawn.RED)) {
					x = position.getX() + 1;
					y = position.getY();
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;

				// Top right.
				if (aboveOk && (squareList[position.getX() + 1][position.getY() - 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() + 1][position.getY() - 1].isBusy(ColorPawn.RED))) {
					x = position.getX() + 1;
					y = position.getY() - 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;
			}

			// Checks if there's a neighbor below the pawn.
			if (position.getY() + 1 < 11) {

				// Below.
				if (squareList[position.getX()][position.getY() + 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX()][position.getY() + 1].isBusy(ColorPawn.RED)) {
					x = position.getX();
					y = position.getY() + 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;

				// Bottom left.
				if (leftOk && (squareList[position.getX() - 1][position.getY() + 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() - 1][position.getY() + 1].isBusy(ColorPawn.RED))) {
					x = position.getX() - 1;
					y = position.getY() + 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;

				// Bottom right.
				if (rightOk && (squareList[position.getX() + 1][position.getY() + 1].isBusy(this.colorPawnNeighbor)
						|| squareList[position.getX() + 1][position.getY() + 1].isBusy(ColorPawn.RED))) {
					x = position.getX() + 1;
					y = position.getY() + 1;
					for (Position pos : this.positionsCountedListPlayer)
						if (pos.getX() == x && pos.getY() == y)
							isAlreadyChecked = true;
					if (!isAlreadyChecked) {
						this.positionsCountedListPlayer.add(position);
						this.setChainLengthPlayer(new Position(x, y));
					}
				}
				isAlreadyChecked = false;
			}
		}
	}

	/**
	 * Ask the player passed in parameter to move a pawn only if the interface is on
	 * the console, otherwise the player can directly enter his new move on his
	 * screen.
	 * 
	 * @param player the player to be asked to move a pawn
	 * 
	 * @return the move made by the user
	 */
	public Move readMove(Player player) {
		Move ret = null;
		if (player != null) {
			boolean allRulesRespected = false;

			// As long as rules arn't respected.
			while (!allRulesRespected) {
				ret = player.newMove();

				if (ret.getStartPosition() == null && ret.getFinishPosition() == null) {
					this.hasChosenFiles = true;
					allRulesRespected = true;
				} else {

					// Checks first if there is a pawn.
					if (this.squareList[ret.getStartPosition().getX()][ret.getStartPosition().getY()].isBusy()) {

						// Checks if the first rule is respected.
						if (this.rules.isMoveable(this.squareList, ret, this.current.getColor())) {

							// Checks if the second rule is respected.
							if (this.rules.isNotEmbarassed(this.squareList, ret, this.current.getColor())) {

								// Checks if the third rule is respected
								if (this.rules.isSquareValid(this.squareList, ret)) {

									// Checks if the fourth rule is respected.
									if (this.rules.isZenMoveValid(this.squareList, ret)) {
										allRulesRespected = true;
									} else {
										String alertText = (this.language == Language.ENGLISH)
												? "Rule 4 not complied with."
												: (this.language == Language.FRENCH) ? "Règle 4 non respectée"
														: "La regla 4 no se ha cumplido";
										if (isGraphic)
											JOptionPane.showMessageDialog(null, alertText);
										else
											System.out.println(alertText);
									}
								} else {
									String alertText = (this.language == Language.ENGLISH) ? "Rule 3 not complied with."
											: (this.language == Language.FRENCH) ? "Règle 3 non respectée"
													: "La regla 3 no se ha cumplido";
									if (isGraphic)
										JOptionPane.showMessageDialog(null, alertText);
									else
										System.out.println(alertText);
								}
							} else {
								String alertText = (this.language == Language.ENGLISH) ? "Rule 2 not complied with."
										: (this.language == Language.FRENCH) ? "Règle 2 non respectée"
												: "La regla 2 no se ha cumplido";
								if (isGraphic)
									JOptionPane.showMessageDialog(null, alertText);
								else
									System.out.println(alertText);
							}
						} else {
							String alertText = (this.language == Language.ENGLISH) ? "Rule 1 not complied with."
									: (this.language == Language.FRENCH) ? "Règle 1 non respectée"
											: "La regla 1 no se ha cumplido";
							if (isGraphic)
								JOptionPane.showMessageDialog(null, alertText);
							else
								System.out.println(alertText);
						}
					}
				}
			}
		} else
			throw new IllegalArgumentException("Game : readMove(Player) : parameter \"player\" null.");

		return ret;
	}

	/**
	 * Get the move that is currently being made
	 * 
	 * @return the pawn move that is being made
	 */
	public Move getCurrentMove() {
		return this.currentMove;
	}

	/**
	 * Get the player who is currently playing.
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return this.current;
	}

	/**
	 * Save the game, with all its data in configuration files so that it can be
	 * resumed later.
	 */
	public void saveGame() {
		if (this.pathname == null)
			this.view.askPathname();
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.pathname));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the mode of the game.
	 * 
	 * @return the game mode
	 */
	public Mode getMode() {
		return this.mode;
	}

	/**
	 * Get the number of hours spent on this game.
	 * 
	 * @return number of hours
	 * @see #setHours(int)
	 */
	public int getHours() {
		return this.hours;
	}

	/**
	 * Set the new number of hours spent on this game.
	 * 
	 * @param hours the number of hours updated
	 */
	public void setHours(int hours) {
		if (hours >= 0)
			this.hours = hours;
		else
			throw new IllegalArgumentException("Game : setHours(int) : invalid parameter.");
	}

	/**
	 * Get the number of minutes spent on this game, after updating the number of
	 * hours. This number is between 0 and 59 inclusive.
	 * 
	 * @return number of minutes
	 * @see #setMinutes(int)
	 */
	public int getMinutes() {
		return this.minutes;
	}

	/**
	 * Set the new number of minutes spent on this game, after updating the number
	 * of hours. This number is between 0 and 59 inclusive.
	 * 
	 * @param minutes the number of minutes updated (after the hours update)
	 */
	public void setMinutes(int minutes) {
		if (minutes >= 0 && minutes < 60)
			this.minutes = minutes;
		else
			throw new IllegalArgumentException("Game : setMinutes(int) : invalid parameter.");
	}

	/**
	 * Get the number of seconds spent on this game, after updating the number of
	 * minutes. This number is between 0 and 59 inclusive.
	 * 
	 * @return number of seconds
	 * @see #setSeconds(int)
	 */
	public int getSeconds() {
		return this.seconds;
	}

	/**
	 * Set the new number of seconds spent on this game, after updating the number
	 * of minutes. This number is between 0 and 59 inclusive.
	 * 
	 * @param seconds the number of seconds updated (after the minutes update)
	 */
	public void setSeconds(int seconds) {
		if (seconds >= 0 && seconds < 60)
			this.seconds = seconds;
		else
			throw new IllegalArgumentException("Game : setSeconds(int) : invalid parameter.");
	}

	/**
	 * Checks if one of the players has won. If that's the case, then call
	 * {@link #endOfGame()} to end the game.
	 * 
	 * @return {@code true} if the current player won, otherwise {@code false}
	 */
	public boolean hasCurrentPlayerWon() {
		return current.getChainLength() == current.getNbPawns();
	}

	@Override
	public void endOfGame() {

		// Saves the game even if it's over.
		this.saveGame();

		// Congratulates players only if someone won.
		if (this.hasCurrentPlayerWon()) {

			// Checks if there is a tie.
			this.changeCurrent();
			boolean isThereATie = this.hasCurrentPlayerWon();
			this.changeCurrent();
			Player opponent = this.current == this.captain ? this.auto : this.captain;

			// Display the congratulations.
			this.view.printEnd(this.current.getName(), opponent.getName(), isThereATie);
		}

		// If the interface is graphical, closes the game board frame.
		if (this.isGraphic) {
			// Chercher comment fermer this.view.printBoard() ici si Graphical (je pense)
		}

		// Back to home page.
		this.view.setDataNull();
		this.view.printHome();
	}

	/**
	 * Returns a string representation of the Game.
	 * 
	 * @return a string that "textually represents" this game
	 */
	public String toString() {
		String l1 = null, l2 = null, l3 = null, l4 = null, l5 = null, l6 = null, l7 = null, l8 = null;
		if (this.language == Language.ENGLISH) {
			l1 = " ___________________________________________________________________________________________________\n";
			l2 = "|                                               GAME:                                               |\n";
			l3 = "| The game is played on this 11x11 square board. Each player has a colour, white or black, randomly |\n";
			l4 = "| selected. Pawns are already placed on the board before the game begins. The winner is the first   |\n";
			l5 = "| who succeeds in forming a continuous chain with all of their pawns still remaining on the board,  |\n";
			l6 = "| including the “Z” pawn if it is still on the board.                                               |\n";
			l7 = "|                                                                                                   |\n";
			l8 = "|_____________________________________ Good luck and have fun ! ____________________________________|\n";
		} else if (this.language == Language.FRENCH) {
			l1 = " ___________________________________________________________________________________________________\n";
			l2 = "|                                               JEU :                                               |\n";
			l3 = "| Le jeu se joue sur ce plateau carré de 11x11. Chaque joueur a une couleur, blanche ou noire,      |\n";
			l4 = "| choisie au hasard. Les pions sont déjà placés sur le plateau de jeu avant le début de la partie.  |\n";
			l5 = "| Le gagnant est le premier qui réussit à former une chaîne continue avec tous ses pions restant sur|\n";
			l6 = "| le plateau, y compris le pion “Z” s'il est encore sur le plateau.                                 |\n";
			l7 = "|                                                                                                   |\n";
			l8 = "|________________________________ Bonne chance et amusez-vous bien ! _______________________________|\n";
		} else {
			l1 = " ___________________________________________________________________________________________________\n";
			l2 = "|                                               JUEGO:                                              |\n";
			l3 = "| El juego se juega en este tablero cuadrado de 11x11. Cada jugador tiene un color, blanco o negro, |\n";
			l4 = "| seleccionado al azar. Los peones ya están colocados en el tablero antes de que comience el juego. |\n";
			l5 = "| El ganador es el primero que logre formar una cadena continua con todos sus peones que permanezcan|\n";
			l6 = "| en el tablero, incluyendo el peón “Z” si todavía está en el tablero.                              |\n";
			l7 = "|                                                                                                   |\n";
			l8 = "|________________________________ ¡Buena suerte y que te diviertas! ________________________________|\n";
		}

		return l1 + l2 + l3 + l4 + l5 + l6 + l7 + l8 + this.rules.toString();
	}
}