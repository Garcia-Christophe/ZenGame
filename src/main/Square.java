package main;

import java.io.Serializable;

/**
 * Squares make up the board of the game. It's empty or busy by a pawn.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Square implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 2605142487005145956L;

	/**
	 * The pawn which occupies the square.
	 * 
	 * @see Pawn
	 */
	private Pawn pawn;

	/**
	 * Initializes a newly created Square object that it represents a square of the
	 * board of the game. This one is empty and therefore does not possess a pawn.
	 */
	public Square() {
	}

	/**
	 * Initializes a newly created Square object that it represents a square of the
	 * board of the game. This one is busy and therefore owns a pawn.
	 * 
	 * @param currentPawn the pawn that occupies the square
	 */
	public Square(Pawn currentPawn) {
		this.setPawn(currentPawn);
	}

	/**
	 * If the square is busy, then get the current pawn that occupies the Square.
	 * 
	 * @return the occupying pawn
	 * @see #setPawn(Pawn)
	 */
	public Pawn getPawn() {
		return this.pawn;
	}

	/**
	 * The new pawn passed as a parameter occupies now the square. This method is
	 * genrally used when there is a move made in the game. The square can be busy
	 * or empty after calling this method.
	 * 
	 * @param newPawn the new occupying pawn
	 */
	public void setPawn(Pawn newPawn) {

		// The null pawn is allowed, the square may not have a pawn.
		this.pawn = newPawn;
	}

	/**
	 * Check if there is a pawn which occupies the current square.
	 * 
	 * @return {@code true} if {@link #getPawn()} isn't null, otherwise
	 *         {@code false}
	 */
	public boolean isBusy() {
		return (this.getPawn() != null);
	}

	/**
	 * Check if there is a pawn with a particular color which occupies the current
	 * square.
	 * 
	 * @param color the potential color of the pawn
	 * 
	 * @return {@code true} if {@link #getPawn()} isn't null and if the color passed
	 *         as a parameter is the same as the color of the pawn, otherwise
	 *         {@code false}
	 */
	public boolean isBusy(ColorPawn color) {
		boolean ret = false;

		if (color == null) {
			throw new IllegalArgumentException("Square : isBusy(ColorPawn) : parameter \"color\" null.");
		} else {
			if (this.isBusy() && this.getPawn().getColor() == color)
				ret = true;
		}

		return ret;
	}

}