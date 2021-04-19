package test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.ZenGame;
import view.ConsoleView;
import view.GameView;
import view.Language;

public class ZenGameTest {
	private ZenGame zenGame;
	private String filename;
	private String playerName1;
	private String playerName2;
	private Language language;
	private GameView gv;

	@Before
	public void setUp() {
		filename = "data/config1.txt";
		playerName1 = "name 1";
		playerName2 = "name 2";
		language = Language.ENGLISH;
		gv = new ConsoleView();
	}

	@After
	public void tearDown() {
		zenGame = null;
		filename = null;
		playerName1 = null;
		playerName2 = null;
		language = null;
		gv = null;
	}

	@Test
	public void testConstructor_Filename_Null() {
		zenGame = new ZenGame(null, playerName1, playerName2, language, null, gv, false);
		assertNotNull("The instance is not created.", zenGame);
	}

	@Test
	public void testConstructor_PlayerName1_Null() {
		zenGame = new ZenGame(filename, null, playerName2, language, null, gv, false);
		assertNotNull("The instance is not created.", zenGame);
	}

	@Test
	public void testConstructor_PlayerName2_Null() {
		zenGame = new ZenGame(filename, playerName1, null, language, null, gv, false);
		assertNotNull("The instance is not created.", zenGame);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Language_Null() {
		zenGame = new ZenGame(filename, playerName1, playerName2, null, null, gv, false);
		assertNotNull("The instance is not created.", zenGame);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_GameView_Null() {
		zenGame = new ZenGame(filename, playerName1, playerName2, language, null, null, false);
		assertNotNull("The instance is not created.", zenGame);
	}

	@Test
	public void testConstructor_isGraphicBoolean_True() {
		zenGame = new ZenGame(filename, playerName1, playerName2, language, null, gv, true);
		assertNotNull("The instance is not created.", zenGame);
	}
}
