package main;

import view.ConsoleView;
import view.GameView;

/**
 * The HumanPlayer represents User in HH or HA mode. (Human-Human / Human-Auto).
 * It is a subclass of Player.
 * 
 * @author Christophe
 * @version 1.0
 */
public class HumanPlayer extends Player {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -1034546960230278047L;

	/**
	 * {@code True} if the user has chosen the graphical user interface, otherwise
	 * {@code false}.
	 */
	private boolean isGraphic;

	/**
	 * Game view, the interface on which the game will be displayed (Console-line,
	 * Graphical).
	 * 
	 * @see GameView
	 */
	private GameView view;

	/**
	 * Initializes a newly created HumanPlayer object that it is a subclass of
	 * Player.
	 * 
	 * @param name      player name
	 * @param gv        the interface on which the game is displayed
	 * @param isGraphic {@code true} if the user choosed the graphical view,
	 *                  otherwise {@code false}
	 */
	public HumanPlayer(String name, GameView gv, boolean isGraphic) {
		super(name);
		if (gv == null)
			throw new IllegalArgumentException(
					"HumanPlayer : HumanPlayer(String, GameView, boolean) : parameter \"gv\" null.");
		else {
			this.view = gv;
			this.isGraphic = isGraphic;
		}
	}

	@Override
	public Move newMove() {
		char[] letters = "ABCDEFGHIJK".toCharArray();
		int[] numbers = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		String answerStart = null;
		String answerFinish = null;
		int xStart = 0;
		int yStart = 0;
		int xFinish = 0;
		int yFinish = 0;
		boolean validFirstLetter = false;
		boolean validMove = false;
		boolean isFilesText = false; // true if the user wrote files and didn't choose to go back

		// Asks and checks the start position.
		while (!validMove) {
			validFirstLetter = false;
			validMove = true;

			// Asks the start position.
			answerStart = this.view.askStartPosition();
			answerStart = answerStart.replaceAll("\\s+", "");

			// Checks if the user asks to see files, rules or history.
			if (answerStart.equalsIgnoreCase("rules") || answerStart.equalsIgnoreCase("regles")
					|| answerStart.equalsIgnoreCase("reglas")) {
				this.view.printRules();
				validMove = false;
			} else if (answerStart.equalsIgnoreCase("files") || answerStart.equalsIgnoreCase("fichiers")
					|| answerStart.equalsIgnoreCase("archivos")) {
				this.view.printFiles();
				if (this.view.getAnswer() == 0)
					validMove = false;
				else {
					isFilesText = true;
					validMove = true;
				}
			} else if (!isGraphic && (answerStart.equalsIgnoreCase("history")
					|| answerStart.equalsIgnoreCase("historique") || answerStart.equalsIgnoreCase("historia"))) {
				((ConsoleView) this.view).printHistory();
				validMove = false;
			}

			// Checks the conformity of the start position.
			if (answerStart.length() != 0) {
				for (int i = 0; i < letters.length; i++) {
					if (Character.toUpperCase(answerStart.charAt(0)) == letters[i]) {
						validFirstLetter = true;
						xStart = i;
					}
				}

				if (!validFirstLetter)
					validMove = false;

				if (validMove && answerStart.length() == 2) {
					if (Character.isDigit(answerStart.charAt(1))) {
						if (Character.getNumericValue(answerStart.charAt(1)) < 1
								|| Character.getNumericValue(answerStart.charAt(1)) > 11)
							validMove = false;
						else {
							for (int i = 0; i < numbers.length; i++) {
								if (Character.getNumericValue(answerStart.charAt(1)) == numbers[i]) {
									yStart = i;
								}
							}
						}
					} else
						validMove = false;
				} else if (validMove && answerStart.length() == 3) {
					if (Character.isDigit(answerStart.charAt(1)) && Character.isDigit(answerStart.charAt(2))) {
						if (Integer
								.parseInt(new String(new char[] { answerStart.charAt(1), answerStart.charAt(2) })) < 1
								|| Integer.parseInt(
										new String(new char[] { answerStart.charAt(1), answerStart.charAt(2) })) > 11)
							validMove = false;
						else {
							for (int i = 0; i < numbers.length; i++) {
								if (Integer.parseInt(new String(
										new char[] { answerStart.charAt(1), answerStart.charAt(2) })) == numbers[i]) {
									yStart = i;
								}
							}
						}
					} else
						validMove = false;
				}
			} else
				validMove = false;
		}

		if (!isFilesText)
			validMove = false;

		// Asks and checks the finish position.
		while (!validMove) {
			validFirstLetter = false;
			validMove = true;

			// Asks the start position.
			answerFinish = this.view.askFinishPosition();
			answerFinish = answerFinish.replaceAll("\\s+", "");

			// Checks the conformity of the finish position.
			if (answerFinish.length() != 0) {
				for (int i = 0; i < letters.length; i++) {
					if (Character.toUpperCase(answerFinish.charAt(0)) == letters[i]) {
						validFirstLetter = true;
						xFinish = i;
					}
				}

				if (!validFirstLetter)
					validMove = false;

				if (validMove && answerFinish.length() == 2) {
					if (Character.isDigit(answerFinish.charAt(1))) {
						if (Character.getNumericValue(answerFinish.charAt(1)) < 1
								|| Character.getNumericValue(answerFinish.charAt(1)) > 11)
							validMove = false;
						else {
							for (int i = 0; i < numbers.length; i++) {
								if (Character.getNumericValue(answerFinish.charAt(1)) == numbers[i]) {
									yFinish = i;
								}
							}
						}
					} else
						validMove = false;
				} else if (validMove && answerFinish.length() == 3) {
					if (Character.isDigit(answerFinish.charAt(1)) && Character.isDigit(answerFinish.charAt(2))) {
						if (Integer
								.parseInt(new String(new char[] { answerFinish.charAt(1), answerFinish.charAt(2) })) < 1
								|| Integer.parseInt(
										new String(new char[] { answerFinish.charAt(1), answerFinish.charAt(2) })) > 11)
							validMove = false;
						else {
							for (int i = 0; i < numbers.length; i++) {
								if (Integer.parseInt(new String(
										new char[] { answerFinish.charAt(1), answerFinish.charAt(2) })) == numbers[i]) {
									yFinish = i;
								}
							}
						}
					} else
						validMove = false;
				}
			} else
				validMove = false;
		}

		Move ret = null;
		if (!isFilesText) {

			// Checks if the user has no moved the pawn.
			ret = new Move(new Position(xStart, yStart), new Position(xFinish, yFinish));
			if (ret.isNoMove())
				ret = this.newMove();
		} else {
			ret = new Move(null, null);
		}

		return ret;
	}
}