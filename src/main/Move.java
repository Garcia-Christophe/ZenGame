package main;

import java.io.Serializable;

/**
 * A move consists of 2 positions: the starting position and the finishing
 * position. It represents the move of the piece on the board.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Move implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 5908332410040817267L;

	/**
	 * The start position of the move.
	 * 
	 * @see Position
	 */
	private Position start;

	/**
	 * The finish position of the move.
	 * 
	 * @see Position
	 */
	private Position finish;

	/**
	 * The number of squares to reach the finish square on the horizontal axis.
	 */
	private int moveX;

	/**
	 * The number of squares to reach the finish square on the vertical axis.
	 */
	private int moveY;

	/**
	 * Initializes a newly created Move object that it represents the move between
	 * the starting position and the end position.
	 * 
	 * @param start  starting position
	 * @param finish finishing position
	 */
	public Move(Position start, Position finish) {
		this.setStartPosition(start);
		this.setFinishPosition(finish);
		if (start != null && finish != null) {
			this.setMoveX(this.finish.getX() - this.start.getX());
			this.setMoveY(this.finish.getY() - this.start.getY());
		}
	}

	/**
	 * Get the starting position of the move.
	 * 
	 * @return the starting position
	 * @see #setStartPosition(Position)
	 */
	public Position getStartPosition() {
		return this.start;
	}

	/**
	 * Set the starting position (the column and the line of the first position of
	 * the move).
	 * 
	 * @param start starting position
	 */
	public void setStartPosition(Position start) {
		this.start = start;
	}

	/**
	 * Get the finishing position of the move.
	 * 
	 * @return the finishin position
	 * @see #setFinishPosition(Position)
	 */
	public Position getFinishPosition() {
		return this.finish;
	}

	/**
	 * Set the finishing position (the column and the line of the second position of
	 * the move).
	 * 
	 * @param finish finishin position
	 */
	public void setFinishPosition(Position finish) {
		this.finish = finish;
	}

	/**
	 * Get the number of columns of difference between the 2 positions.
	 * 
	 * @return the movement on the horizontal axis
	 * @see #setMoveX(int)
	 */
	public int getMoveX() {
		return this.moveX;
	}

	/**
	 * The method is automatically called by the constructor in order to know the
	 * number of move columns.
	 * 
	 * @param move the movement on the horizontal axis
	 */
	private void setMoveX(int move) {
		if (Math.abs(move) <= 10)
			this.moveX = move;
		else
			throw new IllegalArgumentException("Move : setMoveX(int) : invalid parameter.");
	}

	/**
	 * Get the number of lines of difference between the 2 positions.
	 * 
	 * @return the movement on the vertical axis
	 * @see #setMoveY(int)
	 */
	public int getMoveY() {
		return this.moveY;
	}

	/**
	 * The method is automatically called by the constructor in order to know the
	 * number of move lines.
	 * 
	 * @param move the movement on the vertical axis
	 */
	private void setMoveY(int move) {
		if (Math.abs(move) <= 10)
			this.moveY = move;
		else
			throw new IllegalArgumentException("Move : setMoveY(int) : invalid parameter.");
	}

	/**
	 * Compares the starting position with the finishing position.
	 * 
	 * @return {@code true} if {@link #getStartPosition()} and
	 *         {@link #getFinishPosition()} are the same, otherwise {@code false}
	 */
	public boolean isNoMove() {
		return (this.getMoveX() == 0 && this.getMoveY() == 0);
	}

	/**
	 * Returns a string representation of the Move.
	 * 
	 * @return a string that "textually represents" this move
	 */
	public String toString() {
		char[] letters = "ABCDEFGHIJK".toCharArray();
		String[] numbers = new String[] { "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1" };
		char letterStart = 0;
		char letterFinish = 0;
		String numberStart = numbers[this.getStartPosition().getY()];
		String numberFinish = numbers[this.getFinishPosition().getY()];
		for (int i = 0; i < letters.length; i++) {
			if (this.getStartPosition().getX() == i)
				letterStart = letters[i];
			if (this.getFinishPosition().getX() == i)
				letterFinish = letters[i];
		}

		return letterStart + numberStart + " -> " + letterFinish + numberFinish;
	}
}