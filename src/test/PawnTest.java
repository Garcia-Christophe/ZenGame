package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.ColorPawn;
import main.Pawn;

public class PawnTest {
	private Pawn pawn;
	private Pawn pawn2;
	private Pawn pawn3;

	@Before
	public void setUp() {
		pawn = new Pawn(ColorPawn.BLACK);
		pawn2 = new Pawn(ColorPawn.WHITE);
		pawn3 = new Pawn(ColorPawn.RED);
	}

	@After
	public void tearDown() {
		pawn = null;
		pawn2 = null;
		pawn3 = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", pawn);
		assertNotNull("The instance is not created.", pawn2);
		assertNotNull("The instance is not created.", pawn3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_PositionNull() {
		new Pawn(null);
	}

	@Test
	public void testGetColor() {
		assertEquals("The ColorPawn is incorrect.", ColorPawn.BLACK, pawn.getColor());
		assertEquals("The ColorPawn is incorrect.", ColorPawn.WHITE, pawn2.getColor());
		assertEquals("The ColorPawn is incorrect.", ColorPawn.RED, pawn3.getColor());
	}
}
