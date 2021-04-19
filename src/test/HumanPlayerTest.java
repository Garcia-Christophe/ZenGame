package test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.HumanPlayer;
import view.ConsoleView;
import view.GameView;

public class HumanPlayerTest {
	private String name;
	private GameView view;
	private HumanPlayer human;
	private HumanPlayer human2;

	@Before
	public void setUp() {
		name = "player name";
		view = new ConsoleView();
		human = new HumanPlayer(name, view, true);
		human2 = new HumanPlayer(name, view, false);
	}

	@After
	public void tearDown() {
		name = null;
		view = null;
		human = null;
		human2 = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", human);
		assertNotNull("The instance is not created.", human2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_StringNull() {
		new HumanPlayer(null, view, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ViewNull() {
		new HumanPlayer(name, null, true);
	}
}
