package launcher;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import view.ConsoleView;
import view.GameView;
import view.GraphicalView;

/**
 * The Launcher is used to launch the game, it contains the main method.
 * 
 * @author Christophe
 * @version 1.0
 */
public class Launcher {

	/**
	 * Main
	 * 
	 * @param args possible arguments
	 */
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameView view;
				int answer = 0;

				System.out.println("Welcome to the Zen Game!");
				System.out.println("**************************");
				System.out.println();

				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				while (answer != 1 && answer != 2) {
					System.out.println("Which interface do you want to continue with?");
					System.out.println("\t1. Command Line (cli)");
					System.out.println("\t2. Graphical User Interface (GUI)");
					answer = sc.nextInt();
					System.out.println();
				}

				// The user has to choose between a graphical interface or a command-line
				// interface for the rest of the game.
				if (answer == 1) {
					view = new ConsoleView();
				} else {
					view = new GraphicalView();
				}

				// Displays the home frame.
				view.printHome();
			}
		});
	}
}