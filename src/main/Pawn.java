package main;

import java.io.Serializable;

/**
 * A pawn is the only piece in the game. There are the white pawns, the black
 * pawns and the red pawn which is the Zen. They are moved according to the
 * choice of the player holding them.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Pawn implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -7291393103798698376L;

	/**
	 * The color of the pawn (White, Black, Red).
	 * 
	 * @see ColorPawn
	 */
	private ColorPawn color;

	/**
	 * Initializes a newly created Pawn object that it represents the piece to be
	 * moved and grouped with the others.
	 * 
	 * @param color the pawn color
	 */
	public Pawn(ColorPawn color) {
		if (color == null)
			throw new IllegalArgumentException("Pawn : Pawn(ColorPawn) : parameter \"color\" null.");
		else
			this.color = color;
	}

	/**
	 * Get the color pawn : WHITE, BLACK or RED which represents the Zen pawn.
	 * 
	 * @return the color pawn
	 */
	public ColorPawn getColor() {
		return this.color;
	}
}