package main;

import java.io.Serializable;

import view.Language;

/**
 * This class represents all rules of the game. There are 4 rules known, then
 * there is a boolean method for each of them in order to verify them.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Rules implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -5450351303817490981L;

	/**
	 * The last position of Zen.
	 * 
	 * @see Position
	 */
	private Position zenPosition;

	/**
	 * {@code True} if Zen has moved just before, otherwise {@code false}.
	 */
	private boolean hasZenMoved = false;

	/**
	 * The current language chosen by the user.
	 * 
	 * @see Language
	 */
	private Language language;

	/**
	 * This method represents the first rule of the game. The rule is this: pawns
	 * move in a straight line in any direction. Every pawn must always move as many
	 * squares as there are pawns on the moving line selected (Horizontal / Vertical
	 * / Diagonal). All counters are taken into account y including the moved pawn
	 * as well as "Zen".
	 * 
	 * @param squareList  the game board
	 * @param move        the move that moves the pawn on the board
	 * @param colorPlayer the current player color
	 * 
	 * @return {@code true} if the pawn is moveable according to the move and the
	 *         list of Square passed as parameters, otherwise {@code false}
	 */
	public boolean isMoveable(Square[][] squareList, Move move, ColorPawn colorPlayer) {
		boolean ret = false;

		if (squareList == null || move == null || colorPlayer == null)
			throw new IllegalArgumentException("Rules : isMoveable(Square[][], Move, ColorPawn) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException(
								"Rules : isMoveable(Square[][], Move, ColorPawn) : a Square (" + i + "," + j
										+ ") in the \"squareList\" array is null.");
				}
			}

			// Checks first if the pawn moved owns to the current player.
			if (colorPlayer == squareList[move.getStartPosition().getX()][move.getStartPosition().getY()].getPawn()
					.getColor()
					|| squareList[move.getStartPosition().getX()][move.getStartPosition().getY()].getPawn()
							.getColor() == ColorPawn.RED) {
				int squareBusyCounter = 0;

				// Checks whether the move is diagonal.
				if (Math.abs(move.getMoveX()) - Math.abs(move.getMoveY()) == 0) {

					// Counts all the pawns in front of the current pawn and itself.
					int incI = (move.getMoveX() > 0 ? 1 : -1);
					int incJ = (move.getMoveY() > 0 ? 1 : -1);
					for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (i >= 0 && i < 11)
							&& (j >= 0 && j < 11); i = i + incI, j = j + incJ)
						if (squareList[i][j].isBusy())
							squareBusyCounter++;

					// Counts all the pawns behind the current pawn.
					incI = (move.getMoveX() > 0 ? -1 : 1);
					incJ = (move.getMoveY() > 0 ? -1 : 1);
					for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (i >= 0 && i < 11)
							&& (j >= 0 && j < 11); i = i + incI, j = j + incJ)
						if ((i != move.getStartPosition().getX() || j != move.getStartPosition().getY())
								&& squareList[i][j].isBusy())
							squareBusyCounter++;
				}

				// Checks whether the move is straight.
				else if ((move.getMoveX() == 0 || move.getMoveY() == 0)) {
					boolean isVertical = move.getMoveX() == 0;

					// If the move is vertical.
					if (isVertical) {

						// Counts all the pawns in front of the current pawn and itself.
						int incJ = (move.getMoveY() > 0 ? 1 : -1);
						for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (j >= 0
								&& j < 11); j = j + incJ)
							if (squareList[i][j].isBusy())
								squareBusyCounter++;

						// Counts all the pawns behind the current pawn.
						incJ = (move.getMoveY() > 0 ? -1 : 1);
						for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (j >= 0
								&& j < 11); j = j + incJ)
							if (j != move.getStartPosition().getY() && squareList[i][j].isBusy())
								squareBusyCounter++;
					}

					// If the move is horizontal.
					else {

						// Counts all the pawns in front of the current pawn and itself.
						int incI = (move.getMoveX() > 0 ? 1 : -1);
						for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (i >= 0
								&& i < 11); i = i + incI)
							if (squareList[i][j].isBusy())
								squareBusyCounter++;

						// Counts all the pawns behind the current pawn.
						incI = (move.getMoveX() > 0 ? -1 : 1);
						for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY(); (i >= 0
								&& i < 11); i = i + incI)
							if (i != move.getStartPosition().getX() && squareList[i][j].isBusy())
								squareBusyCounter++;
					}
				}

				// Checks if there are the right number of checkers on the moving line.
				if (squareBusyCounter != 0
						&& squareBusyCounter == (Math.abs(move.getMoveX()) >= Math.abs(move.getMoveY())
								? Math.abs(move.getMoveX())
								: Math.abs(move.getMoveY())))
					ret = true;
			}
		}

		return ret;
	}

	/**
	 * This method represents the second rule of the game. The rule is this: any
	 * pawn may pass over one or more pawns of its color and "Zen", but never over
	 * his opponent's.
	 * 
	 * @param squareList  the game board
	 * @param move        the move that moves the pawn on the board
	 * @param colorPlayer the current player color
	 * 
	 * @return {@code true} if the pawn isn't embarassed according to the move and
	 *         the list of Square passed as parameters, otherwise {@code false}
	 */
	public boolean isNotEmbarassed(Square[][] squareList, Move move, ColorPawn colorPlayer) {
		boolean ret = true;

		if (squareList == null || move == null || colorPlayer == null)
			throw new IllegalArgumentException(
					"Rules : isNotEmbarassed(Square[][], Move, ColorPawn) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException(
								"Rules : isNotEmbarassed(Square[][], Move, ColorPawn) : a Square (" + i + "," + j
										+ ") in the \"squareList\" array is null.");
				}
			}

			ColorPawn colorOpponentPawn = null;
			if (colorPlayer == ColorPawn.WHITE)
				colorOpponentPawn = ColorPawn.BLACK;
			else
				colorOpponentPawn = ColorPawn.WHITE;

			// If the user moves his pawn zero or one square, then no need to check if a
			// pawn of the same colour is in the way.
			if (!((move.getMoveX() == 0 || Math.abs(move.getMoveX()) == 1)
					&& (move.getMoveY() == 0 || Math.abs(move.getMoveY()) == 1))) {

				// Checks whether the move is diagonal.
				if (Math.abs(move.getMoveX()) - Math.abs(move.getMoveY()) == 0) {

					// Checks if there is a pawn of the same color on the way to the current pawn.
					int incI = (move.getMoveX() > 0 ? 1 : -1);
					int incJ = (move.getMoveY() > 0 ? 1 : -1);
					for (int i = move.getStartPosition().getX() + incI, j = move.getStartPosition().getY()
							+ incJ; (i != move.getFinishPosition().getX())
									&& (j != move.getFinishPosition().getY()); i = i + incI, j = j + incJ)
						if (squareList[i][j].isBusy(colorOpponentPawn))
							ret = false;
				}

				// Checks whether the move is straight.
				else if ((move.getMoveX() == 0 || move.getMoveY() == 0)) {
					boolean isVertical = move.getMoveX() == 0;

					// If the move is vertical.
					if (isVertical) {

						// Checks if there is a pawn of the same color on the way to the current pawn.
						int incJ = (move.getMoveY() > 0 ? 1 : -1);
						for (int i = move.getStartPosition().getX(), j = move.getStartPosition().getY()
								+ incJ; j != move.getFinishPosition().getY(); j = j + incJ)
							if (squareList[i][j].isBusy(colorOpponentPawn))
								ret = false;
					}

					// If the move is horizontal.
					else {

						// Checks if there is a pawn of the same color on the way to the current pawn.
						int incI = (move.getMoveX() > 0 ? 1 : -1);
						for (int i = move.getStartPosition().getX() + incI, j = move.getStartPosition()
								.getY(); i != move.getFinishPosition().getX(); i = i + incI)
							if (squareList[i][j].isBusy(colorOpponentPawn))
								ret = false;
					}
				}
			}
		}

		return ret;
	}

	/**
	 * This method represents the third rule of the game. The rule is this: any pawn
	 * can capture an opponent's pawn by placing itself on the square occupied by
	 * the taken one, while respecting Rule 1. Taking is not mandatory. It is
	 * forbidden to take his own pawns.
	 * 
	 * @param squareList the game board
	 * @param move       the move that moves the pawn on the board
	 * 
	 * @return {@code true} if the square is valid according to the move and the
	 *         list of Square passed as parameters, otherwise {@code false}
	 */
	public boolean isSquareValid(Square[][] squareList, Move move) {
		boolean ret = true;

		if (squareList == null || move == null)
			throw new IllegalArgumentException("Rules : isSquareValid(Square[][], Move) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException("Rules : isSquareValid(Square[][], Move) : a Square (" + i
								+ "," + j + ") in the \"squareList\" array is null.");
				}
			}

			ColorPawn colorPawn = squareList[move.getStartPosition().getX()][move.getStartPosition().getY()].getPawn()
					.getColor();

			if (squareList[move.getFinishPosition().getX()][move.getFinishPosition().getY()].isBusy(colorPawn))
				ret = false;
		}

		return ret;
	}

	/**
	 * This method represents the fourth rule of the game. The rule is this: at each
	 * move, the "Zen" pawn, common to all players, can be either white or black
	 * (friend/enemy) depending on the interest of the player. The "Zen" can thus be
	 * captured by a player, he is then removed from the game. When it is moved by a
	 * player, his opponent does not have the right to put it back on the same
	 * square on the next move. It is forbidden to move it, if it is not in contact
	 * with at least one pawn (white or black).
	 * 
	 * @param squareList the game board
	 * @param move       the move that moves the pawn on the board
	 * 
	 * @return {@code true} if the Zen move is valid according to the move and the
	 *         list of Square passed as parameters, otherwise {@code false}
	 */
	public boolean isZenMoveValid(Square[][] squareList, Move move) {
		boolean ret = false;

		if (squareList == null || move == null)
			throw new IllegalArgumentException("Rules : isZenMoveValid(Square[][], Move) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException("Rules : isZenMoveValid(Square[][], Move) : a Square (" + i
								+ "," + j + ") in the \"squareList\" array is null.");
				}
			}

			// Checks if the pawn is Zen.
			if (squareList[move.getStartPosition().getX()][move.getStartPosition().getY()].getPawn()
					.getColor() == ColorPawn.RED) {

				// If the player places Zen on the same square from where it was just before.
				if (hasZenMoved && (this.zenPosition.getX() == move.getFinishPosition().getX()
						&& this.zenPosition.getY() == move.getFinishPosition().getY()))
					ret = false;

				// Checks if Zen is alone.
				else {

					// Checks if there are pawns above Zen.
					boolean aboveOk = false;
					if (move.getFinishPosition().getY() - 1 >= 0) {
						aboveOk = true;

						// Above.
						if (squareList[move.getFinishPosition().getX()][move.getFinishPosition().getY() - 1].isBusy())
							ret = true;
					}

					// Checks if there are pawns to the left of Zen.
					boolean leftOk = false;
					if (move.getFinishPosition().getX() - 1 >= 0) {
						leftOk = true;

						// Left.
						if (squareList[move.getFinishPosition().getX() - 1][move.getFinishPosition().getY()].isBusy())
							ret = true;

						// Top left.
						if (aboveOk
								&& squareList[move.getFinishPosition().getX() - 1][move.getFinishPosition().getY() - 1]
										.isBusy())
							ret = true;
					}

					// Checks if there are pawns to the right of Zen.
					boolean rightOk = false;
					if (move.getFinishPosition().getX() + 1 < 11) {
						rightOk = true;

						// Right.
						if (squareList[move.getFinishPosition().getX() + 1][move.getFinishPosition().getY()].isBusy())
							ret = true;

						// Top right.
						if (aboveOk
								&& squareList[move.getFinishPosition().getX() + 1][move.getFinishPosition().getY() - 1]
										.isBusy())
							ret = true;
					}

					// Checks if there are pawns below Zen.
					if (move.getFinishPosition().getY() + 1 < 11) {

						// Below.
						if (squareList[move.getFinishPosition().getX()][move.getFinishPosition().getY() + 1].isBusy())
							ret = true;

						// Bottom left.
						if (leftOk
								&& squareList[move.getFinishPosition().getX() - 1][move.getFinishPosition().getY() + 1]
										.isBusy())
							ret = true;

						// Bottom right.
						if (rightOk
								&& squareList[move.getFinishPosition().getX() + 1][move.getFinishPosition().getY() + 1]
										.isBusy())
							ret = true;
					}
				}

				// Updates the Zen move.
				if (ret) {
					this.hasZenMoved = true;
					this.zenPosition = new Position(move.getStartPosition().getX(), move.getStartPosition().getY());
				}
			} else {
				ret = true;
				this.hasZenMoved = false;
			}
		}

		return ret;
	}

	/**
	 * The language is the one chosen by the user. The default language is English.
	 * 
	 * @param lang the game language
	 */
	public void setlanguage(Language lang) {
		if (lang == null)
			throw new IllegalArgumentException("Rules : setLanguage(Language) : parameter \"lang\" null.");
		else
			this.language = lang;
	}

	/**
	 * Returns a string representation of the Rules.
	 * 
	 * @return a string that "textually represents" all rules
	 */
	public String toString() {
		String r = null, r1 = null, r1Text = null, r2 = null, r2Text = null, r3 = null, r3Text = null, r4 = null,
				r4Text = null;
		if (this.language == Language.ENGLISH) {
			r = "\n                                               RULES:                                                ";

			r1 = "\nRule 1:\r\n";
			r1Text = "\tPawns move in a straight line in any direction. Every pawn must always move as many\r\n"
					+ "\tsquares as there are pawns on the moving line selected (Horizontal / Vertical / Diagonal).\r\n"
					+ "\tAll counters are taken into account y including the moved pawn as well as “Zen”.\r\n";

			r2 = "\nRule 2:\r\n";
			r2Text = "\tAny pawn may pass over one or more pawns of its color and “Zen”, but never over his\r\n"
					+ "\topponent's.\r\n";

			r3 = "\nRule 3:\r\n";
			r3Text = "\tAny pawn can capture an opponent's pawn by placing itself on the square occupied by the\r\n"
					+ "\ttaken one, while respecting Rule 1. Taking is not mandatory. It is forbidden to take his\r\n"
					+ "\town pawns.\r\n";

			r4 = "\nRule 4:\r\n";
			r4Text = "\tAt each move, the “Zen” pawn, common to all players, can be either white or black (friend/\r\n"
					+ "\tenemy) depending on the interest of the player. The “Zen” can thus be captured by a player,\r\n"
					+ "\the is then removed from the game. When it is moved by a player, his opponent does not have\r\n"
					+ "\tthe right to put it back on the same square on the next move. It is forbidden to move it,\r\n"
					+ "\tif it is not in contact with at least one pawn (white or black).\r\n";
		} else if (this.language == Language.FRENCH) {
			r = "\n                                               RÈGLES:                                               ";

			r1 = "\nRègle 1:\r\n";
			r1Text = "\tLes pions se déplacent en ligne droite dans n'importe quelle direction. Chaque pion doit \r\n"
					+ "\ttoujours se déplacer d'autant de cases qu'il y a de pions sur la ligne de déplacement \r\n"
					+ "\tsélectionnée (Horizontale / Verticale / Diagonale). Tous les pions sont pris en compte y \r\n"
					+ "\tcompris le pion déplacé ainsi que le “Zen”.\r\n";

			r2 = "\nRègle 2:\r\n";
			r2Text = "\tTout pion peut passer sur un ou plusieurs pions de sa couleur et le “Zen”, mais jamais sur \r\n"
					+ "\tcelui de son adversaire.\r\n";

			r3 = "\nRègle 3:\r\n";
			r3Text = "\tTout pion peut capturer un pion adverse en se plaçant sur la case occupée par celui qui \r\n"
					+ "\test pris, tout en respectant la règle 1. La prise n'est pas obligatoire. Il est interdit \r\n"
					+ "\tde prendre ses propres pions.\r\n";

			r4 = "\nRègle 4:\r\n";
			r4Text = "\tA chaque coup, le pion “Zen”, commun à tous les joueurs, peut être soit blanc soit noir \r\n"
					+ "\t(ami/ennemi) selon l'intérêt du joueur. Le “Zen” peut donc être capturé par un joueur, \r\n"
					+ "\til est alors retiré du jeu. Lorsqu'il est déplacé par un joueur, son adversaire n'a pas \r\n"
					+ "\tle droit de le remettre sur la même case au prochain coup. Il est interdit de le déplacer\r\n"
					+ "\ts'il n'est pas en contact avec au moins un pion (blanc ou noir).\r\n";
		} else {
			r = "\n                                               REGLAS:                                               ";

			r1 = "\nRegla 1:\r\n";
			r1Text = "\tLos peones se mueven en línea recta en cualquier dirección. Cada peón siempre debe mover \r\n"
					+ "\ttantas casillas como peones haya en la línea de movimiento seleccionada (Horizontal / \r\n"
					+ "\tVertical / Diagonal). Todas las fichas se tienen en cuenta e incluyen el peón movido así \r\n"
					+ "\tcomo el “Zen”.\r\n";

			r2 = "\nRegla 2:\r\n";
			r2Text = "\tCualquier peón puede pasar por encima de uno o más peones de su color y “Zen”, pero nunca\r\n"
					+ "\tpor encima de su oponente.\r\n";

			r3 = "\nRegla 3:\r\n";
			r3Text = "\tCualquier peón puede capturar el peón de un oponente colocándose en la casilla ocupada por\r\n"
					+ "\tel tomado, respetando la regla 1. La toma no es obligatoria. Está prohibido tomar sus \r\n"
					+ "\tpropios peones.\r\n";

			r4 = "\nRegla 4:\r\n";
			r4Text = "\tEn cada movimiento, el peón “Zen”, común a todos los jugadores, puede ser blanco o negro \r\n"
					+ "\t(amigo/enemigo) dependiendo del interés del jugador. El “Zen” puede así ser capturado por\r\n"
					+ "\tun jugador, y luego es retirado del juego. Cuando es movido por un jugador, su oponente \r\n"
					+ "\tno tiene derecho a ponerlo de nuevo en la misma casilla en el siguiente movimiento. Está \r\n"
					+ "\tprohibido moverlo, si no está en contacto con al menos un peón (blanco o negro).\r\n";
		}
		return r + r1 + r1Text + r2 + r2Text + r3 + r3Text + r4 + r4Text;
	}

}