package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Position;

public class PositionTest {
	private Position position;

	@Before
	public void setUp() {
		position = new Position(0, 10);
	}

	@After
	public void tearDown() {
		position = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", position);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_NegativeX() {
		new Position(-1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_NegativeY() {
		new Position(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_XTooHigh() {
		new Position(11, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_YTooHigh() {
		new Position(0, 11);
	}

	@Test
	public void testGetX() {
		assertEquals("The X coordinate is incorrect.", 0, position.getX());
	}

	@Test
	public void testGetY() {
		assertEquals("The Y coordinate is incorrect.", 10, position.getY());
	}

	@Test
	public void testSetX() {
		position.setX(7);
		assertEquals("The X coordinate is incorrect.", 7, position.getX());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetX_Negative() {
		position.setX(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetX_TooHigh() {
		position.setX(11);
	}

	@Test
	public void testSetY() {
		position.setY(8);
		assertEquals("The X coordinate is incorrect.", 8, position.getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetY_Negative() {
		position.setX(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetY_TooHigh() {
		position.setX(11);
	}
}
