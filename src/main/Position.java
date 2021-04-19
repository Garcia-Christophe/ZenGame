package main;

import java.io.Serializable;

/**
 * A position is a pair of coordinates (x, y) that represents the column and row
 * of the Square array of the game.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Position implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 4612989369233069753L;

	/**
	 * X coordinate on the game board.
	 */
	private int x;

	/**
	 * Y coordinate on the game board.
	 */
	private int y;

	/**
	 * Initializes a newly created Position object that it represents one vertical
	 * and one horizontal coordinate in the Game's array of Square.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public Position(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Get the current column number of the Square array of the Game.
	 * 
	 * @return horizontal coordinate
	 * @see #setX(int)
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * The x coordinate represents the horizontal position of the Square array of
	 * the Game. It is between 0 inclusive and 11 inclusive.
	 * 
	 * @param x horizontal coordinate
	 */
	public void setX(int x) {
		if (x >= 0 && x < 11)
			this.x = x;
		else
			throw new IllegalArgumentException("Position : setX(int) : invalid x coordinate.");
	}

	/**
	 * Get the current row number of the Square array of the Game.
	 * 
	 * @return vertical coordinate
	 * @see #setY(int)
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * The y coordinate represents the vertical position of the Square array of the
	 * Game It is between 0 inclusive and 11 inclusive.
	 * 
	 * @param y vertical coordinate
	 */
	public void setY(int y) {
		if (y >= 0 && y < 11)
			this.y = y;
		else
			throw new IllegalArgumentException("Position : setY(int) : invalid y coordinate.");
	}

	/**
	 * Compares the current position with the one passed as a parameter.
	 * 
	 * @param position position to be compared with this position
	 * 
	 * @return {@code true} if {@link #getX()} and {@link #getY()} of the two
	 *         positions are the same, otherwise {@code false}
	 */
	public boolean isEquals(Position position) {
		boolean ret = false;
		if (position != null)
			ret = (this.getX() == position.getX() && this.getY() == position.getY());
		else
			throw new IllegalArgumentException("Position : isEquals(Position) : parameter \"position\" null.");
		return ret;
	}

}