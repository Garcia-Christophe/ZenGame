package main;

import java.util.ArrayList;

/**
 * The AutoPlayer represents Artificial Intelligence in HA or AA mode.
 * (Human-Auto / Auto-Auto). It is a subclass of Player.
 * 
 * @author Christophe
 * @version 1.0
 */
public class AutoPlayer extends Player {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -4078875799713589963L;

	/**
	 * The game rules allow the Auto-player to play properly.
	 * 
	 * @see Rules
	 */
	private Rules rules;

	/**
	 * The game board.
	 * 
	 * @see Square
	 */
	private Square[][] squareList;

	/**
	 * Initializes a newly created AutoPlayer object that it is a subclass of
	 * Player.
	 * 
	 * @param name  player name
	 * @param rules the game rules
	 */
	public AutoPlayer(String name, Rules rules) {
		super(name);
		if (rules == null)
			throw new IllegalArgumentException("AutoPlayer : AutoPlayer(String, Rules) : parameter \"rules\" null.");
		else
			this.rules = rules;
	}

	@Override
	public Move newMove() {
		Move ret = null;

		// Takes all positions of auto-player pawns.
		ArrayList<Position> autoPlayerPawnPosition = new ArrayList<Position>();
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (this.squareList[i][j].isBusy(this.getColor()) || this.squareList[i][j].isBusy(ColorPawn.RED))
					autoPlayerPawnPosition.add(new Position(i, j));
			}
		}

		Position start = null;
		Position finish = null;
		ArrayList<Position> possibleFinishPosition = new ArrayList<Position>();
		boolean moveOk = false;

		// Checks if the pawn randomly selected can move somewhere.
		while (!moveOk && !autoPlayerPawnPosition.isEmpty()) {
			int randomPawn = (int) (Math.random() * autoPlayerPawnPosition.size());
			start = autoPlayerPawnPosition.get(randomPawn);
			autoPlayerPawnPosition.remove(start);
			ret = new Move(start, start);

			// Checks if there are free squares from the same team pawns to the left.
			for (int i = start.getX(); i >= 0; i--) {
				if (!this.squareList[i][start.getY()].isBusy(this.getColor()))
					possibleFinishPosition.add(new Position(i, start.getY()));
			}

			// Checks if there are free squares from the same team pawns to the right.
			for (int i = start.getX(); i < 11; i++) {
				if (!this.squareList[i][start.getY()].isBusy(this.getColor()))
					possibleFinishPosition.add(new Position(i, start.getY()));
			}

			// Checks if there are free squares from the same team pawns above.
			for (int i = start.getY(); i >= 0; i--) {
				if (!this.squareList[start.getX()][i].isBusy(this.getColor()))
					possibleFinishPosition.add(new Position(start.getX(), i));
			}

			// Checks if there are free squares from the same team pawns below.
			for (int i = start.getY(); i < 11; i++) {
				if (!this.squareList[start.getX()][i].isBusy(this.getColor()))
					possibleFinishPosition.add(new Position(start.getX(), i));
			}

			while (!moveOk && !possibleFinishPosition.isEmpty()) {
				finish = possibleFinishPosition.get((int) (Math.random() * possibleFinishPosition.size()));
				possibleFinishPosition.remove(finish);
				ret = new Move(start, finish);
				if (this.rules.isMoveable(this.squareList, ret, this.getColor())
						&& this.rules.isNotEmbarassed(this.squareList, ret, this.getColor())
						&& this.rules.isSquareValid(this.squareList, ret)
						&& this.rules.isZenMoveValid(this.squareList, ret))
					moveOk = true;
			}
		}

		return ret;
	}

	public void setSquareList(Square[][] squareList) {
		if (squareList != null)
			this.squareList = squareList;
		else
			throw new IllegalArgumentException(
					"AutoPlayer : setSquareList(Square[][]) : parameter \"squareList\" null.");
	}
}