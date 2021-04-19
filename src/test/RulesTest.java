package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.ColorPawn;
import main.HumanPlayer;
import main.Move;
import main.Pawn;
import main.Player;
import main.Position;
import main.Rules;
import main.Square;
import view.ConsoleView;
import view.GameView;

public class RulesTest {
	private Move move;
	private GameView view;
	private Rules rules;
	private Player player;
	private Square[][] squareList;
	private Pawn pawnWhite;
	private Pawn pawnWhite2;
	private Pawn pawnBlack;
	private Pawn pawnRed;

	@Before
	public void setUp() {
		rules = new Rules();
		view = new ConsoleView();
		player = new HumanPlayer("name", view, false);
		player.setColor(ColorPawn.WHITE);
		squareList = new Square[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++)
				squareList[i][j] = new Square();
		}

		pawnWhite = new Pawn(ColorPawn.WHITE);
		pawnWhite2 = new Pawn(ColorPawn.WHITE);
		pawnBlack = new Pawn(ColorPawn.BLACK);
		pawnRed = new Pawn(ColorPawn.RED);
	}

	@After
	public void tearDown() {
		rules = null;
		view = null;
		player = null;
		squareList = null;
		pawnWhite = null;
		pawnWhite2 = null;
		pawnBlack = null;
		pawnRed = null;
		move = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", rules);
	}

	@Test
	public void testIsMoveable_UP() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(5, 4));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_DOWN() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(5, 6));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_LEFT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(4, 5));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_RIGHT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(6, 5));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_UP_LEFT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(4, 4));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_UP_RIGHT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(6, 4));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_DOWN_LEFT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(4, 6));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_DOWN_RIGHT() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(6, 6));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_WrongMove() {
		int column = 5;
		int line = 5;
		squareList[column][line].setPawn(pawnWhite);
		move = new Move(new Position(column, line), new Position(4, 3));
		assertFalse(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_2Pawns_2Squares() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[5][2].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 3));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_3Pawns_3Squares() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[4][6].setPawn(pawnBlack);
		squareList[8][2].setPawn(pawnRed);
		move = new Move(new Position(5, 5), new Position(8, 2));
		assertTrue(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_2Pawns_1Square() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[5][7].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertFalse(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsMoveable_WhitePlayer_Moves_BlackPawn() {
		squareList[5][5].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertFalse(rules.isMoveable(squareList, move, player.getColor()));
	}

	@Test
	public void testIsNotEmbarassed_WhiteJumpWhite() {
		squareList[5][3].setPawn(pawnWhite);
		squareList[5][5].setPawn(pawnWhite2);
		squareList[5][7].setPawn(pawnBlack);
		move = new Move(new Position(5, 3), new Position(5, 6));
		assertTrue(rules.isNotEmbarassed(squareList, move, ColorPawn.WHITE));
	}

	@Test
	public void testIsNotEmbarassed_WhiteJumpRed() {
		squareList[5][3].setPawn(pawnWhite);
		squareList[5][5].setPawn(pawnRed);
		squareList[5][7].setPawn(pawnBlack);
		move = new Move(new Position(5, 3), new Position(5, 6));
		assertTrue(rules.isNotEmbarassed(squareList, move, ColorPawn.WHITE));
	}

	@Test
	public void testIsNotEmbarassed_WhiteJumpBlack() {
		squareList[5][3].setPawn(pawnWhite);
		squareList[5][5].setPawn(pawnBlack);
		squareList[5][7].setPawn(pawnWhite2);
		move = new Move(new Position(5, 3), new Position(5, 6));
		assertFalse(rules.isNotEmbarassed(squareList, move, ColorPawn.WHITE));
	}

	@Test
	public void testIsSquareValid_Empty() {
		squareList[5][5].setPawn(pawnWhite);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isSquareValid(squareList, move));
	}

	@Test
	public void testIsSquareValid_BusyBlack() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[5][7].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 7));
		assertTrue(rules.isSquareValid(squareList, move));
	}

	@Test
	public void testIsSquareValid_BusyRed() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[5][7].setPawn(pawnRed);
		move = new Move(new Position(5, 5), new Position(5, 7));
		assertTrue(rules.isSquareValid(squareList, move));
	}

	@Test
	public void testIsSquareValid_BusyWhite() {
		squareList[5][5].setPawn(pawnWhite);
		squareList[5][7].setPawn(pawnWhite2);
		move = new Move(new Position(5, 5), new Position(5, 7));
		assertFalse(rules.isSquareValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveWhite() {
		squareList[5][5].setPawn(pawnWhite);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isZenMoveValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveBlack() {
		squareList[5][5].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isZenMoveValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveRed_NoContact() {
		squareList[5][5].setPawn(pawnRed);
		squareList[5][9].setPawn(pawnWhite);
		move = new Move(new Position(5, 5), new Position(5, 7));
		assertFalse(rules.isZenMoveValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveRed_WithWhiteContact() {
		squareList[5][5].setPawn(pawnRed);
		squareList[6][6].setPawn(pawnWhite);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isZenMoveValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveRed_WithBlackContact() {
		squareList[5][5].setPawn(pawnRed);
		squareList[6][6].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isZenMoveValid(squareList, move));
	}

	@Test
	public void testIsZenMoveValid_MoveRed_AtSamePlace() {
		squareList[5][5].setPawn(pawnRed);
		squareList[6][6].setPawn(pawnBlack);
		move = new Move(new Position(5, 5), new Position(5, 6));
		assertTrue(rules.isZenMoveValid(squareList, move));

		squareList[5][5].setPawn(null);
		squareList[5][6].setPawn(pawnRed);
		squareList[6][6].setPawn(pawnBlack);
		move = new Move(new Position(5, 6), new Position(5, 5));
		assertFalse(rules.isZenMoveValid(squareList, move));
	}
}
