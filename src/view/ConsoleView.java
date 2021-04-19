package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import main.ColorPawn;
import main.Mode;
import main.Player;
import main.Square;
import main.ZenGame;

/**
 * The ConsoleView represents the Text-based User Interface. User can choose
 * which interface will display the game before playing. It is a subclass of
 * GameView.
 * 
 * @author Christophe
 * @version 1.0
 */
public class ConsoleView extends GameView {

	private static final long serialVersionUID = -5439532840444476314L;
	private ArrayList<String> historyBoard;
	transient private Scanner in;

	public ConsoleView() {
		this.setLanguage(Language.ENGLISH);
	}

	@Override
	public void printHome() {
		this.in = new Scanner(System.in);
		this.setAnswer(0);

		while (this.getAnswer() != 1 && this.getAnswer() != 2) {
			if (this.getLanguage() == Language.FRENCH) {
				System.out.println("ACCUEIL");
				System.out.println("**************************");
				System.out.println("\t1. Options");
				System.out.println("\t2. Jouer");
				System.out.println();
				System.out.println("Que voulez-vous faire ?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("HOME");
				System.out.println("**************************");
				System.out.println("\t1. Settings");
				System.out.println("\t2. Play");
				System.out.println();
				System.out.println("What do you want to do?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else {
				System.out.println("INICIO");
				System.out.println("**************************");
				System.out.println("\t1. Opciones");
				System.out.println("\t2. Jugar");
				System.out.println();
				System.out.println("¿Qué quieres hacer?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			}
		}

		if (this.getAnswer() == 1)
			this.printSettings();
		else
			this.printRetrievingInformations();
	}

	@Override
	public void printSettings() {
		this.in = new Scanner(System.in);
		this.setAnswer(0);

		while (this.getAnswer() != 1 && this.getAnswer() != 2 && this.getAnswer() != 3 && this.getAnswer() != 4
				&& this.getAnswer() != -1) {
			if (this.getLanguage() == Language.FRENCH) {
				System.out.println("OPTIONS");
				System.out.println("**************************");
				System.out.println("\t1. Charger une partie");
				System.out.println("\t2. Changer de langue");
				System.out.println("\t3. Quitter le jeu");
				System.out.println("\t4. Retour");
				System.out.println();
				System.out.println("Que voulez-vous faire ?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("SETTINGS");
				System.out.println("**************************");
				System.out.println("\t1. Load a game");
				System.out.println("\t2. Change language");
				System.out.println("\t3. Exit the game");
				System.out.println("\t4. Back");
				System.out.println();
				System.out.println("What do you want to do?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else {
				System.out.println("OPCIONES");
				System.out.println("**************************");
				System.out.println("\t1. Cargar un juego");
				System.out.println("\t2. Cambiar el idioma");
				System.out.println("\t3. Salir del juego");
				System.out.println("\t4. Volver");
				System.out.println();
				System.out.println("¿Qué quieres hacer?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			}

			// If the user chooses to load a game.
			if (this.getAnswer() == 1) {
				this.pathname = this.askPathname();
			}

			// If the user chooses to change the language.
			else if (this.getAnswer() == 2) {
				if (this.getLanguage() == Language.ENGLISH) {
					System.out.println("What will be the new language?");
					System.out.println("\t1. French");
					System.out.println("\t2. Spanish");
				} else if (this.getLanguage() == Language.FRENCH) {
					System.out.println("Quelle sera la nouvelle langue ?");
					System.out.println("\t1. Anglais");
					System.out.println("\t2. Espagnol");
				} else {
					System.out.println("¿Cuál será el nuevo idioma?");
					System.out.println("\t1. Francés");
					System.out.println("\t2. Inglés");
				}
				this.setAnswer(this.in.nextInt());

				// Change the language according to the current language and both propositions.
				if (this.getAnswer() == 1) {
					if (this.getLanguage() == Language.SPANISH || this.getLanguage() == Language.ENGLISH)
						this.setLanguage(Language.FRENCH);
					else if (this.getLanguage() == Language.FRENCH)
						this.setLanguage(Language.ENGLISH);
					else
						this.setLanguage(Language.SPANISH);
				} else {
					if (this.getLanguage() == Language.SPANISH)
						this.setLanguage(Language.ENGLISH);
					else if (this.getLanguage() == Language.FRENCH || this.getLanguage() == Language.ENGLISH)
						this.setLanguage(Language.SPANISH);
				}
				this.setAnswer(2);
			}
		}

		if (this.getAnswer() == -1)

			// Create a new form of ZenGame after collecting all the necessary data.
			new ZenGame(this.pathname, this.namePlayer1, this.namePlayer2, this.getLanguage(), null, this, false);
		else if (this.getAnswer() == 1 || this.getAnswer() == 2)
			this.printSettings();
		else if (this.getAnswer() == 3)
			System.exit(0);
		else
			this.printHome();

	}

	@Override
	public String askPathname() {

		// While the given pathname is incorrect.
		while (this.pathname == null || (!this.pathname.contains("data/config") && !this.pathname.contains(".txt")
				&& !(new File(this.pathname)).exists() && !this.pathname.equals("1"))) {
			if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("Enter the pathname of the previous game:");
				System.out.println("\t1. Back");
			} else if (this.getLanguage() == Language.FRENCH) {
				System.out.println("Entrez le nom du chemin d'accès de la partie précédente :");
				System.out.println("\t1. Retour");
			} else {
				System.out.println("Introduzca el camino para acceder a la partida anterior:");
				System.out.println("\t1. Volver");
			}
			this.in.nextLine();
			this.pathname = this.in.nextLine();
			this.pathname = this.pathname.replaceAll("\\s+", "");
		}
		if (this.pathname.equals("1")) {
			this.setAnswer(1);
			this.pathname = null;
		} else
			this.setAnswer(-1);

		return this.pathname;
	}

	@Override
	public void printRetrievingInformations() {
		this.in = new Scanner(System.in);
		this.setAnswer(0);

		while (this.getAnswer() != 1 && this.getAnswer() != 2 && this.getAnswer() != 3 && this.getAnswer() != 4) {
			if (this.getLanguage() == Language.FRENCH) {
				System.out.println("NOMBRE DE JOUEURS");
				System.out.println("**************************");
				System.out.println("\t1. 0");
				System.out.println("\t2. 1");
				System.out.println("\t3. 2");
				System.out.println("\t4. Retour");
				System.out.println();
				System.out.println("Combien y a-t-il de joueurs humains ?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("NUMBER OF PLAYERS");
				System.out.println("**************************");
				System.out.println("\t1. 0");
				System.out.println("\t2. 1");
				System.out.println("\t3. 2");
				System.out.println("\t4. Back");
				System.out.println();
				System.out.println("How many human players are there?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			} else {
				System.out.println("NÚMERO DE JUGADORES");
				System.out.println("**************************");
				System.out.println("\t1. 0");
				System.out.println("\t2. 1");
				System.out.println("\t3. 2");
				System.out.println("\t4. Volver");
				System.out.println();
				System.out.println("¿Cuántos jugadores humanos hay?");
				this.setAnswer(this.in.nextInt());
				System.out.println("**************************");
				System.out.println();
			}
		}

		// The user has chosen the Auto-Auto mode. Assigning names to auto players.
		if (this.getAnswer() == 1) {
			if (this.getLanguage() == Language.ENGLISH) {
				this.namePlayer1 = "A.I. 1";
				this.namePlayer2 = "A.I. 2";
			} else {
				this.namePlayer1 = "I.A. 1";
				this.namePlayer2 = "I.A. 2";
			}
		}

		// The user has chosen the Human-Auto mode. Asking the human player name.
		else if (this.getAnswer() == 2) {

			// As long as the user has not written a valid name.
			while (this.namePlayer1 == null || this.namePlayer1.length() == 0
					|| ((this.getLanguage() == Language.ENGLISH) ? this.namePlayer1.equals("A.I.")
							: this.namePlayer1.equals("I.A."))) {
				if (this.getLanguage() == Language.FRENCH) {
					System.out.println("Quel est le nom du joueur humain ? (nom \"I.A.\" impossible)");
					this.namePlayer2 = "I.A.";
				} else if (this.getLanguage() == Language.ENGLISH) {
					System.out.println("What's the human player's name? (name \"A.I.\" impossible)");
					this.namePlayer2 = "A.I.";
				} else {
					System.out.println("¿Cómo se llama el jugador humano? (nombre \"I.A.\" imposible)");
					this.namePlayer2 = "I.A.";
				}
				this.in.nextLine();
				this.namePlayer1 = this.in.nextLine();
			}
		}

		// The user has chosen the Human-Human mode. Asking the human players name.
		else if (this.getAnswer() == 3) {

			// As long as the user has not written a valid name.
			while (this.namePlayer1 == null || this.namePlayer1.length() == 0
					|| ((this.getLanguage() == Language.ENGLISH) ? this.namePlayer1.equals("A.I.")
							: this.namePlayer1.equals("I.A."))) {
				if (this.getLanguage() == Language.FRENCH) {
					System.out.println("Quel est le nom du premier joueur ? (nom \"I.A.\" impossible)");
				} else if (this.getLanguage() == Language.ENGLISH) {
					System.out.println("What's the first player's name? (name \"A.I.\" impossible)");
				} else {
					System.out.println("¿Cómo se llama el primer jugador? (nombre \"I.A.\" imposible)");
				}
				this.in.nextLine();
				this.namePlayer1 = this.in.nextLine();
			}

			// As long as the user has not written a valid name.
			while (this.namePlayer2 == null || this.namePlayer2.length() == 0
					|| ((this.getLanguage() == Language.ENGLISH) ? this.namePlayer2.equals("A.I.")
							: this.namePlayer2.equals("I.A."))) {
				if (this.getLanguage() == Language.FRENCH)
					System.out.println("Quel est le nom du second joueur ? (nom \"I.A.\" impossible)");
				else if (this.getLanguage() == Language.ENGLISH)
					System.out.println("What's the second player's name? (name \"A.I.\" impossible)");
				else
					System.out.println("¿Cómo se llama el segundo jugador? (nombre \"I.A.\" imposible)");
				this.namePlayer2 = this.in.nextLine();
			}
		}

		if (this.getAnswer() == 1 || this.getAnswer() == 2 || this.getAnswer() == 3)

			// Create a new form of ZenGame after collecting all the necessary data.
			new ZenGame(this.pathname, this.namePlayer1, this.namePlayer2, this.getLanguage(),
					this.getAnswer() == 1 ? Mode.AA : this.getAnswer() == 2 ? Mode.HA : Mode.HH, this, false);
		else
			this.printHome();
	}

	@Override
	public void printBoard(Square[][] squareList, Player current, Player opponent, DefaultListModel<String> listMoves,
			int nbMove) {
		this.in = new Scanner(System.in);
		if (squareList == null || current == null || opponent == null || listMoves == null)
			throw new IllegalArgumentException(
					"ConsoleView : printBoard(Square[][], Player, Player, DefaultListModel<String>, int) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException(
								"ConsoleView : printBoard(Square[][], Player, Player, DefaultListModel<String>, int) : a Square ("
										+ i + "," + j + ") in the \"squareList\" array is null.");

				}
			}
			this.modelHistoryMoves = listMoves;

			// Displays some informations.
			if (this.getLanguage() == Language.FRENCH) {
				System.out.println("\nZEN l'INITIÉ : Coup n° " + nbMove);
				System.out.println("******************************************************************************");
				System.out.println("Pour accéder à Fichier, écrivez : fichiers");
				System.out.println("Pour accéder aux Règles, écrivez : regles");
				System.out.println("Pour visualiser l'Historique des coups joués, écrivez : historique");
				System.out.println("Pour jouer, écrivez la case correspondante sous la forme : LettreChiffre");
				System.out.println(
						"O = Blanc (" + (current.getColor() == ColorPawn.WHITE ? current.getName() : opponent.getName())
								+ "), X = Noir ("
								+ (current.getColor() == ColorPawn.BLACK ? current.getName() : opponent.getName())
								+ "), Z = Zen");
			} else if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("\nZEN GAME : Move n° " + nbMove);
				System.out.println("******************************************************************************");
				System.out.println("To access File, write: files");
				System.out.println("To access the Rules, write: rules");
				System.out.println("To visualize the History of the played moves, write: history");
				System.out.println("To play, write the corresponding box in the form: LetterNumber");
				System.out.println(
						"O = White (" + (current.getColor() == ColorPawn.WHITE ? current.getName() : opponent.getName())
								+ "), X = Black ("
								+ (current.getColor() == ColorPawn.BLACK ? current.getName() : opponent.getName())
								+ "), Z = Zen");
			} else {
				System.out.println("\nEL ZEN : Toma n° " + nbMove);
				System.out.println("******************************************************************************");
				System.out.println("Para acceder a los Archivos, escriba: archivos");
				System.out.println("Para acceder a las Reglas, escriba: reglas");
				System.out.println("Para ver la Historia de las jugadas, escribe: historia");
				System.out.println("Para jugar, escriba la casilla correspondiente en el formulario: LetraNúmero");
				System.out.println("O = Blanco ("
						+ (current.getColor() == ColorPawn.WHITE ? current.getName() : opponent.getName())
						+ "), X = Negro ("
						+ (current.getColor() == ColorPawn.BLACK ? current.getName() : opponent.getName())
						+ "), Z = Zen");
			}
			System.out.println("******************************************************************************");
			System.out.println();

			// Displays the board.
			System.out.println(this.gameBoardToString(squareList));

			// Displays the number of remaining pawns.
			boolean isCurrentPlayerWhite = current.getColor() == ColorPawn.WHITE;

			if (this.getLanguage() == Language.FRENCH) {
				System.out.println(
						"Pions restants : O = " + (isCurrentPlayerWhite ? current.getNbPawns() : opponent.getNbPawns())
								+ ", X = " + (isCurrentPlayerWhite ? opponent.getNbPawns() : current.getNbPawns()));
				System.out.println("C'est à " + current.getName() + " de jouer !");
			} else if (this.getLanguage() == Language.ENGLISH) {
				System.out.println("Pawns remainings : O = "
						+ (isCurrentPlayerWhite ? current.getNbPawns() : opponent.getNbPawns()) + ", X = "
						+ (isCurrentPlayerWhite ? opponent.getNbPawns() : current.getNbPawns()));
				System.out.println("It's " + current.getName() + "'s turn!");
			} else {
				System.out.println("Los peones restantes : O = "
						+ (isCurrentPlayerWhite ? current.getNbPawns() : opponent.getNbPawns()) + ", X = "
						+ (isCurrentPlayerWhite ? opponent.getNbPawns() : current.getNbPawns()));
				System.out.println("¡Es el turno de " + current.getName() + "!");
			}
			isCurrentPlayerWhite = false;
		}
	}

	/**
	 * Gets the String representation of the game board.
	 * 
	 * @param squareList the game board
	 * 
	 * @return the game board
	 */
	public String gameBoardToString(Square[][] squareList) {
		String l1 = null;
		String l2 = null;
		String l3 = null;
		String lines = null;

		if (squareList == null)
			throw new IllegalArgumentException(
					"ConsoleView : gameBoardToString(Square[][]) : parameter \"squareList\" null.");
		else {
			l1 = "\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\n";
			l2 = "\t_________________________________________________________________________________\n";
			lines = "";

			int number = 11; // The number of the line of the game board.
			for (int j = 0; j < 11; j++) {
				for (int i = 0; i < 11; i++) {

					// The line number.
					if (i == 0)
						lines = lines + number + "\t|";

					// The square.
					if (squareList[i][j].isBusy(ColorPawn.WHITE))
						lines = lines + "O";
					else if (squareList[i][j].isBusy(ColorPawn.BLACK))
						lines = lines + "X";
					else if (squareList[i][j].isBusy(ColorPawn.RED))
						lines = lines + "Z";
					else
						lines = lines + ".";

					// The end of the line.
					if (i == 10)
						lines = lines + "|\n";
					else
						lines = lines + "\t";
				}
				if (j != 10)
					lines = lines + "\t|\t\t\t\t\t\t\t\t\t\t |\n";
				number--;
			}
			l3 = "\t|________________________________________________________________________________|\n";
		}

		return l1 + l2 + lines + l3;
	}

	@Override
	public String askStartPosition() {
		String answerStart = null;
		String textStart = (this.getLanguage() == Language.ENGLISH ? "Which pawn do you want to move?"
				: this.getLanguage() == Language.FRENCH ? "Quelle pièce voulez-vous déplacer ?"
						: "¿Qué peón quieres mover?");

		System.out.println("\n" + textStart);
		answerStart = this.in.nextLine();

		return answerStart;
	}

	@Override
	public String askFinishPosition() {
		String answerFinish = null;
		String textFinish = (this.getLanguage() == Language.ENGLISH ? "Where do you want it moved to?"
				: this.getLanguage() == Language.FRENCH ? "Où est-ce que vous voulez la déplacer ?"
						: "¿Adónde quiere que lo traslademos?");

		System.out.println(textFinish);
		answerFinish = this.in.nextLine();

		return answerFinish;
	}

	@Override
	public void printEnd(String playerName1, String playerName2, boolean isThereATie) {
		if (playerName1 == null || playerName2 == null)
			throw new IllegalArgumentException("ConsoleView : printEnd(String, String, boolean) : parameter null.");
		else {

			// Set congratulations text.
			String text = null;
			String title = (this.getLanguage() == Language.ENGLISH) ? "CONGRATULATIONS"
					: ((this.getLanguage() == Language.FRENCH) ? "FÉLICITATIONS" : "FELICIDADES");

			if (isThereATie) {
				if (this.getLanguage() == Language.ENGLISH)
					text = "Congratulations to both of you!\n" + playerName1 + " and " + playerName2
							+ " have beaten with equal force!";
				else if (this.getLanguage() == Language.FRENCH)
					text = "Félicitations à vous 2 !\n" + playerName1 + " et " + playerName2
							+ " ont battu à force égale !";
				else
					text = "Félicitations à vous 2 !\n" + playerName1 + " y " + playerName2
							+ " han golpeado con igual fuerza!";
			} else {
				if (this.getLanguage() == Language.ENGLISH)
					text = playerName1 + " won against " + playerName2 + "\nNothing stops the great " + playerName1
							+ "!\nNever forget: the most important thing is to participate!";
				else if (this.getLanguage() == Language.FRENCH)
					text = playerName1 + " a gagné contre " + playerName2 + "\nRien n'arrête le grand " + playerName1
							+ " !\nN'oubliez jamais : le plus important, c'est de participer !";
				else
					text = playerName1 + " ganó contra " + playerName2 + "\nNada detiene al gran " + playerName1
							+ "!\nNunca lo olvides: ¡lo más importante es participar!";
			}

			// Displays the congratulations.
			System.out.println();
			System.out.println(title);
			System.out.println("**************************************************");
			System.out.println(text);
			System.out.println("**************************************************");
			System.out.println();
		}
	}

	@Override
	public void printRules() {
		System.out.println(this.rules);
	}

	@Override
	public void printFiles() {
		if (this.getLanguage() == Language.ENGLISH) {
			System.out.println(
					"\n                                               FILES:                                                ");
			System.out.println("0. Back");
			System.out.println("1. Load a game");
			System.out.println("2. Save the game");
			System.out.println("3. Leave the game");
		} else if (this.getLanguage() == Language.FRENCH) {
			System.out.println(
					"\n                                              FICHIERS:                                              ");
			System.out.println("1. Charger une partie");
			System.out.println("2. Sauvegarder la partie");
			System.out.println("3. Quitter la partie");
			System.out.println("0. Retour");
		} else {
			System.out.println(
					"\n                                              ARCHIVOS:                                              ");
			System.out.println("0. Volver");
			System.out.println("1. Cargar una partida");
			System.out.println("2. Salvar el juego");
			System.out.println("3. Dejar el juego.");
		}

		this.setAnswer(this.in.nextInt());
	}

	/**
	 * Sets the history of all the board precedently displayed.
	 * 
	 * @param historyBoard the game boards
	 */
	public void setHistoryBoard(ArrayList<String> historyBoard) {
		if (historyBoard == null)
			throw new IllegalArgumentException(
					"ConsoleView : setHistoryBoard(ArrayList<String>) : parameter \"historyBoard\" null.");
		else
			this.historyBoard = historyBoard;
	}

	/**
	 * Prints the history of the moves on the user's screen.
	 */
	public void printHistory() {
		if (this.getLanguage() == Language.ENGLISH)
			System.out.println(
					"\n                                              HISTORY:                                               ");
		else if (this.getLanguage() == Language.FRENCH)
			System.out.println(
					"\n                                             HISTORIQUE:                                             ");
		else
			System.out.println(
					"\n                                             HISTORIA:                                               ");

		// As long as the user wants to see the history of the moves.
		while (this.getAnswer() != 0) {
			if (this.getLanguage() == Language.ENGLISH)
				System.out.println("0. Back");
			else if (this.getLanguage() == Language.FRENCH)
				System.out.println("0. Retour");
			else
				System.out.println("0. Volver");
			for (int i = 0; i < this.modelHistoryMoves.getSize(); i++)
				System.out.println((i + 1) + ". " + this.modelHistoryMoves.get(i));
			this.setAnswer(this.in.nextInt());

			if (this.getAnswer() != 0) {
				System.out.println(this.historyBoard.get(this.getAnswer() - 1));
			}
		}
		this.setAnswer(-2);
	}
}