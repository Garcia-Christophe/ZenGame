package main;

import java.io.Serializable;

/**
 * A Player represents the user and/or the Artificial Intelligence. The player
 * moves his pawns in order to bring them all together.
 * 
 * @author Christophe
 * @version 1.0
 */
public abstract class Player implements Serializable {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -6021839516114678401L;

	/*
	 * The name of the player.
	 */
	protected String name;

	/**
	 * The largest group of the player's pawns on the board.
	 */
	protected int chainLength;

	/**
	 * The number of the player's pawns still on the board.
	 */
	protected int nbPawns;

	/**
	 * The color of the Player (White or Black).
	 * 
	 * @see ColorPawn
	 */
	private ColorPawn color;

	/**
	 * Initializes a newly created Player object that it represents the user and/or
	 * the Artifical Intelligence who are the two opponents in the game.
	 * 
	 * @param name the player's name
	 */
	public Player(String name) {
		if (name == null)
			throw new IllegalArgumentException("Player : Player(String) : parameter \"name\" null.");
		else
			this.name = name;
	}

	/**
	 * Get the name of the player chosen by himself before the game starts.
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the color of the pawn in order to find out what team he's on.
	 * 
	 * @return the pawn color
	 * @see #setColor(ColorPawn)
	 */
	public ColorPawn getColor() {
		return this.color;
	}

	/**
	 * Set the color of the current pawn, which determines which team he's on.
	 * 
	 * @param color the pawn color
	 */
	public void setColor(ColorPawn color) {
		if (color == null || color == ColorPawn.RED)
			throw new IllegalArgumentException("Player : setColor(ColorPawn) : invalid parameter.");
		else
			this.color = color;
	}

	/**
	 * Get the length of the chain, which represents the number of pawns grouped
	 * together. If it's equals to {@link #getNbPawns()}, then the player has won.
	 * 
	 * @return the chain length
	 * @see #setChainLength(int)
	 */
	public int getChainLength() {
		return this.chainLength;
	}

	/**
	 * Set the new chain length of pawns. This represents the largest group of the
	 * player's pawns on the board.
	 * 
	 * @param chainLength the chain length
	 */
	public void setChainLength(int chainLength) {
		if (chainLength < 0 || chainLength > this.getNbPawns())
			throw new IllegalArgumentException("Player : setChainLength(int) : invalid parameter.");
		else
			this.chainLength = chainLength;
	}

	/**
	 * Get the number of the player's pawns still on the board. If it's equals to
	 * {@link #getChainLength()}, then the player has won.
	 * 
	 * @return the number of the pawns of the players still on the board
	 * @see #setChainLength(int)
	 */
	public int getNbPawns() {
		return this.nbPawns;
	}

	/**
	 * Set the number of the player's pawns still on the board. Zen counts as one of
	 * the pawns the two teams.
	 * 
	 * @param nbPawns the pawns number
	 */
	public void setNbPawns(int nbPawns) {
		if (nbPawns < 0 || nbPawns > 13)
			throw new IllegalArgumentException("Player : setNbPawns(int) : invalid parameter.");
		else
			this.nbPawns = nbPawns;
	}

	/**
	 * Abstract method which asks the user to give the move he wants to make or
	 * calls the method that generates move for the Artificial Intelligence.
	 * 
	 * @return the move made by the player
	 */
	public abstract Move newMove();
}
