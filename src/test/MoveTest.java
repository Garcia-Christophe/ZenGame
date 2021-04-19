package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Move;
import main.Position;

public class MoveTest {
	private Position start;
	private Position finish;
	private Move move;
	private Move noMove;

	@Before
	public void setUp() {
		start = new Position(5, 5);
		finish = new Position(9, 5);
		move = new Move(start, finish);
		noMove = new Move(start, start);
	}

	@After
	public void tearDown() {
		start = null;
		finish = null;
		move = null;
		noMove = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", move);
		assertNotNull("The instance is not created.", noMove);
	}

	@Test
	public void testConstructor_StartPositionOk() {
		new Move(null, new Position(5, 5));
	}

	@Test
	public void testConstructor_FinishPositionOk() {
		new Move(new Position(5, 5), null);
	}

	@Test
	public void testGetStartPosition() {
		assertEquals("The start position is incorrect.", start, move.getStartPosition());
		assertEquals("The start position is incorrect.", start, noMove.getStartPosition());
	}

	@Test
	public void testGetFinishPosition() {
		assertEquals("The finish position is incorrect.", finish, move.getFinishPosition());
		assertEquals("The finish position is incorrect.", start, noMove.getFinishPosition());
	}

	@Test
	public void testSetStartPosition() {
		Position position = new Position(0, 0);
		move.setStartPosition(position);
		assertEquals("The start position is incorrect.", position, move.getStartPosition());
	}

	@Test
	public void testSetFinishPosition() {
		Position position = new Position(0, 0);
		move.setFinishPosition(position);
		assertEquals("The finish position is incorrect.", position, move.getFinishPosition());
	}

	@Test
	public void testGetMoveX() {
		assertEquals("The moveX is incorrect.", 9 - 5, move.getMoveX());
		assertEquals("The moveX is incorrect.", 5 - 5, noMove.getMoveX());
	}

	@Test
	public void testGetMoveY() {
		assertEquals("The moveX is incorrect.", 5 - 5, move.getMoveY());
		assertEquals("The moveX is incorrect.", 5 - 5, noMove.getMoveY());
	}

	@Test
	public void testIsNoMove() {
		assertFalse(move.isNoMove());
		assertTrue(noMove.isNoMove());
	}
}
