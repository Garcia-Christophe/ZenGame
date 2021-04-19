package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.AutoPlayer;
import main.ColorPawn;
import main.HumanPlayer;
import main.Player;
import main.Rules;
import view.ConsoleView;
import view.GameView;

public class PlayerTest {
	private String nameHuman;
	private String nameAuto;
	private GameView view;
	private Rules rules;
	private Player humanPlayer;
	private Player autoPlayer;

	@Before
	public void setUp() {
		nameHuman = "human name";
		nameAuto = "auto name";
		view = new ConsoleView();
		rules = new Rules();
		humanPlayer = new HumanPlayer(nameHuman, view, false);
		autoPlayer = new AutoPlayer(nameAuto, rules);
	}

	@After
	public void tearDown() {
		nameHuman = null;
		nameAuto = null;
		view = null;
		rules = null;
		humanPlayer = null;
		autoPlayer = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", humanPlayer);
		assertNotNull("The instance is not created.", autoPlayer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_AutoStringNull() {
		new AutoPlayer(null, rules);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_RulesNull() {
		new AutoPlayer(nameAuto, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_HumanStringNull() {
		new HumanPlayer(null, view, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ViewNull() {
		new HumanPlayer(nameHuman, null, true);
	}

	@Test
	public void testGetName() {
		assertEquals("The name is incorrect.", nameHuman, humanPlayer.getName());
		assertEquals("The name is incorrect.", nameAuto, autoPlayer.getName());
	}

	@Test
	public void testSetAndGetColor() {
		humanPlayer.setColor(ColorPawn.BLACK);
		autoPlayer.setColor(ColorPawn.WHITE);
		assertEquals("The name is incorrect.", ColorPawn.BLACK, humanPlayer.getColor());
		assertEquals("The name is incorrect.", ColorPawn.WHITE, autoPlayer.getColor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetColor_Null() {
		humanPlayer.setColor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetColor_ColorRed() {
		humanPlayer.setColor(ColorPawn.RED);
	}

	@Test
	public void testSetAndGetChainLength() {
		humanPlayer.setNbPawns(13);
		humanPlayer.setChainLength(5);
		autoPlayer.setNbPawns(13);
		autoPlayer.setChainLength(4);
		assertEquals("The name is incorrect.", 5, humanPlayer.getChainLength());
		assertEquals("The name is incorrect.", 4, autoPlayer.getChainLength());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetChainLength_Negative() {
		humanPlayer.setChainLength(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetChainLength_TooHigh() {
		humanPlayer.setChainLength(14);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetChainLength_HigherThanNbPawns() {
		humanPlayer.setNbPawns(5);
		humanPlayer.setChainLength(6);
	}

	@Test
	public void testSetAndGetNbPawns() {
		humanPlayer.setNbPawns(3);
		autoPlayer.setNbPawns(2);
		assertEquals("The name is incorrect.", 3, humanPlayer.getNbPawns());
		assertEquals("The name is incorrect.", 2, autoPlayer.getNbPawns());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNbPawns_Negative() {
		humanPlayer.setNbPawns(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNbPawns_TooHigh() {
		humanPlayer.setNbPawns(14);
	}
}
