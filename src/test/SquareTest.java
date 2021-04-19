package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.ColorPawn;
import main.Pawn;
import main.Square;

public class SquareTest {
	private Square squareEmpty;
	private Square squareBusy;
	private Pawn pawn;

	@Before
	public void setUp() {
		pawn = new Pawn(ColorPawn.BLACK);
		squareEmpty = new Square();
		squareBusy = new Square(pawn);
	}

	@After
	public void tearDown() {
		squareEmpty = null;
		squareBusy = null;
		pawn = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", squareEmpty);
		assertNotNull("The instance is not created.", squareBusy);
	}

	@Test
	public void testGetPawn() {
		assertEquals("The pawn is incorrect.", null, squareEmpty.getPawn());
		assertEquals("The pawn is incorrect.", pawn, squareBusy.getPawn());
	}

	@Test
	public void testSetPawn() {
		squareEmpty.setPawn(pawn);
		assertEquals("The pawn is incorrect.", pawn, squareEmpty.getPawn());
	}

	@Test
	public void testIsBusy() {
		assertFalse(squareEmpty.isBusy());
		assertTrue(squareBusy.isBusy());
	}

	@Test
	public void testIsBusy_WithColorPawn() {
		assertFalse(squareEmpty.isBusy(ColorPawn.WHITE));
		assertFalse(squareEmpty.isBusy(ColorPawn.BLACK));
		assertFalse(squareEmpty.isBusy(ColorPawn.RED));

		assertFalse(squareBusy.isBusy(ColorPawn.WHITE));
		assertTrue(squareBusy.isBusy(ColorPawn.BLACK));
		assertFalse(squareBusy.isBusy(ColorPawn.RED));
	}
}
