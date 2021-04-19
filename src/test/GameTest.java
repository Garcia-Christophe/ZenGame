package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Game;
import main.Mode;
import main.Square;
import view.ConsoleView;
import view.GameView;
import view.Language;

public class GameTest {
	private Game game;
	private Square[][] squareList;
	private String pathname;
	private String playerName1;
	private String playerName2;
	private Mode modeHH;
	private Mode modeHA;
	private Mode modeAA;
	private Language languageENGLISH;
	private Language languageFRENCH;
	private Language languageSPANISH;
	private GameView gv;

	@Before
	public void setUp() {
		squareList = new Square[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++)
				squareList[i][j] = new Square();
		}
		pathname = "data/config1.txt";
		playerName1 = "name 1";
		playerName2 = "name 2";
		modeHH = Mode.HH;
		modeHA = Mode.HA;
		modeAA = Mode.AA;
		languageENGLISH = Language.ENGLISH;
		languageFRENCH = Language.FRENCH;
		languageSPANISH = Language.SPANISH;
		gv = new ConsoleView();
	}

	@After
	public void tearDown() {
		game = null;
		squareList = null;
		pathname = null;
		playerName1 = null;
		playerName2 = null;
		modeHH = null;
		modeHA = null;
		modeAA = null;
		languageENGLISH = null;
		languageFRENCH = null;
		languageSPANISH = null;
		gv = null;
	}

	@Test
	public void testConstructor_HH_ENGLISH_false() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHH, languageENGLISH, gv, false);
		assertNotNull("The instance is not created.", game);
	}

	@Test
	public void testConstructor_HA_ENGLISH_true() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHA, languageENGLISH, gv, true);
		assertNotNull("The instance is not created.", game);
	}

	@Test
	public void testConstructor_AA_ENGLISH_true() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeAA, languageENGLISH, gv, true);
		assertNotNull("The instance is not created.", game);
	}

	@Test
	public void testConstructor_AA_FRENCH_true() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeAA, languageFRENCH, gv, true);
		assertNotNull("The instance is not created.", game);
	}

	@Test
	public void testConstructor_AA_SPANISH_true() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeAA, languageSPANISH, gv, true);
		assertNotNull("The instance is not created.", game);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_SquareListNull() {
		new Game(null, pathname, playerName1, playerName2, modeAA, languageSPANISH, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_PathnameNull() {
		new Game(squareList, null, playerName1, playerName2, modeAA, languageSPANISH, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_PlayerName1Null() {
		new Game(squareList, pathname, null, playerName2, modeAA, languageSPANISH, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_PlayerName2Null() {
		new Game(squareList, pathname, playerName1, null, modeAA, languageSPANISH, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ModeNull() {
		new Game(squareList, pathname, playerName1, playerName2, null, languageSPANISH, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_LanguageNull() {
		new Game(squareList, pathname, playerName1, playerName2, modeAA, null, gv, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_GameViewNull() {
		new Game(squareList, pathname, playerName1, playerName2, modeAA, languageSPANISH, null, true);
	}

	@Test
	public void testGetMode() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHH, languageENGLISH, gv, true);
		assertEquals("The mode is incorrect.", Mode.HH, game.getMode());
	}

	@Test
	public void testSetAndGetHours() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHH, languageENGLISH, gv, true);
		game.setHours(3);
		assertEquals("The nb of hours is incorrect.", 3, game.getHours());
	}

	@Test
	public void testSetAndGetMinutes() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHH, languageENGLISH, gv, true);
		game.setMinutes(15);
		assertEquals("The nb of minutes is incorrect.", 15, game.getMinutes());
	}

	@Test
	public void testSetAndGetSeconds() {
		game = new Game(squareList, pathname, playerName1, playerName2, modeHH, languageENGLISH, gv, true);
		game.setSeconds(45);
		assertEquals("The nb of seconds is incorrect.", 45, game.getSeconds());
	}
}
