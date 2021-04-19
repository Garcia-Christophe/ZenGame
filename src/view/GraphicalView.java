package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import controller.GraphicalViewListener;
import main.Player;
import main.Square;

/**
 * The GraphicalView represents the Graphical User Interface. User can choose
 * which interface will display the game before playing. It is a subclass of
 * GameView.
 * 
 * @author Christophe
 * @version 1.0
 */
public class GraphicalView extends GameView {

	private static final long serialVersionUID = 423661009053055423L;

	private Font fontTitle;
	private Color colorBackground;
	private String pathname;
	private String playerName1;
	private String playerName2;
	private JList<String> historyMoves;
	private ArrayList<Square[][]> moveDisplayList;
	private JFrame homeFrame;
	private JFrame settingsFrame;
	private JFrame filesFrame;
	private JFrame rulesFrame;
	private JButton playButton;
	private JButton settingsButton;
	private JButton backButton;
	private JButton playButtonSettings;
	private JComboBox<String> listLanguages;
	private JTextField numberJTF;
	private JButton quitButtonSettings;

	public GraphicalView() {
		this.fontTitle = new Font("Calibri", Font.BOLD, 50);
		this.colorBackground = new Color(251, 223, 202);
		this.modelHistoryMoves = new DefaultListModel<String>();
		this.historyMoves = new JList<String>(this.modelHistoryMoves);
		this.setLanguage(Language.ENGLISH);

		// Initializing the home frame.
		this.homeFrame = new JFrame();
		this.homeFrame.setTitle("Zen Game - Home");
		this.homeFrame.setSize(600, 400);
		this.homeFrame.setResizable(false);
		this.homeFrame.setLocationRelativeTo(null);
		this.homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.homeFrame.getContentPane().setLayout(new BorderLayout());
		JPanel topPanelHome = new JPanel();
		topPanelHome.setBackground(this.colorBackground);
		JLabel titleHome = new JLabel(this.getLanguage() == Language.ENGLISH ? "HOME"
				: this.getLanguage() == Language.FRENCH ? "ACCUEIL" : "BIENVENIDA");
		titleHome.setHorizontalAlignment(JLabel.CENTER);
		titleHome.setFont(this.fontTitle);
		topPanelHome.add(titleHome);
		this.settingsButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("settings.png")));
		this.settingsButton.setBackground(this.colorBackground);
		this.settingsButton.setBorderPainted(false);
		this.settingsButton.setFocusPainted(false);
		this.settingsButton.setBounds(530, 2, 60, 60);
		this.homeFrame.add(this.settingsButton);
		JPanel centerPanelHome = new JPanel();
		centerPanelHome.setBackground(this.colorBackground);
		centerPanelHome.setLayout(new BorderLayout());
		if (this.getLanguage() == Language.ENGLISH) {
			this.playButton = new JButton("PLAY");
		} else if (this.getLanguage() == Language.FRENCH) {
			this.playButton = new JButton("JOUER");
		} else {
			this.playButton = new JButton("JUGAR");
		}
		this.playButton.setFont(this.fontTitle);
		this.playButton.setBackground(new Color(249, 202, 0));
		this.playButton.setBorder(new MatteBorder(4, 4, 4, 4, new Color(124, 69, 49)));
		this.playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel panelPlayButton = new JPanel();
		panelPlayButton.setLayout(new GridLayout(1, 3));
		panelPlayButton.setBackground(this.colorBackground);
		panelPlayButton.add(new JLabel());
		panelPlayButton.add(this.playButton);
		panelPlayButton.add(new JLabel());
		JLabel logo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("logo.png")));
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanelHome.add(logo, BorderLayout.NORTH);
		centerPanelHome.add(panelPlayButton, BorderLayout.CENTER);
		JPanel panelEmptyHome = new JPanel();
		panelEmptyHome.setPreferredSize(new Dimension(600, 50));
		panelEmptyHome.setBackground(this.colorBackground);
		this.homeFrame.add(topPanelHome, BorderLayout.NORTH);
		this.homeFrame.add(centerPanelHome, BorderLayout.CENTER);
		this.homeFrame.add(panelEmptyHome, BorderLayout.SOUTH);

		// Initializing the settings frame.
		this.settingsFrame = new JFrame();
		this.settingsFrame.setTitle("Zen Game - Settings");
		this.settingsFrame.setSize(600, 400);
		this.settingsFrame.setResizable(false);
		this.settingsFrame.setLocationRelativeTo(null);
		this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.settingsFrame.getContentPane().setLayout(new BorderLayout());
		JPanel topPanelSettings = new JPanel();
		topPanelSettings.setBackground(this.colorBackground);
		JLabel titleSettings = new JLabel(this.getLanguage() == Language.ENGLISH ? "SETTINGS"
				: this.getLanguage() == Language.FRENCH ? "OPTIONS" : "OPCIONES");
		titleSettings.setHorizontalAlignment(JLabel.CENTER);
		titleSettings.setFont(this.fontTitle);
		topPanelSettings.add(titleSettings);
		this.backButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back.png")));
		this.backButton.setBackground(this.colorBackground);
		this.backButton.setBorderPainted(false);
		this.backButton.setFocusPainted(false);
		this.backButton.setBounds(2, 2, 60, 60);
		JPanel centerPanelSettings = new JPanel();
		centerPanelSettings.setBackground(this.colorBackground);
		centerPanelSettings.setLayout(new GridLayout(7, 1));
		JPanel panel1_Settings = new JPanel();
		panel1_Settings.setBackground(this.colorBackground);
		panel1_Settings.setLayout(new GridLayout(1, 2));
		Font fontText = new Font("Calibri", Font.BOLD, 20);
		Font fontText2 = new Font("Calibri", Font.BOLD, 15);
		JLabel loadGameText = new JLabel(this.getLanguage() == Language.ENGLISH ? "Load a game:"
				: this.getLanguage() == Language.FRENCH ? "Charger une partie:" : "Cargar una partida:");
		loadGameText.setFont(fontText);
		JPanel pathnamePanel = new JPanel();
		pathnamePanel.setLayout(new GridLayout(1, 3));
		pathnamePanel.setBackground(this.colorBackground);
		JLabel dataConfigText = new JLabel("data/config");
		dataConfigText.setFont(fontText2);
		dataConfigText.setVerticalAlignment(JLabel.CENTER);
		this.numberJTF = new JTextField();
		this.numberJTF.setSize(10, 10);
		JLabel txtText = new JLabel(".txt");
		txtText.setFont(fontText2);
		this.playButtonSettings = new JButton(
				new ImageIcon(getClass().getClassLoader().getResource("playSettings.png")));
		this.playButtonSettings.setBackground(new Color(237, 125, 44));
		JPanel pathnamePanel1 = new JPanel();
		pathnamePanel1.setLayout(new GridLayout(3, 1));
		pathnamePanel1.setBackground(this.colorBackground);
		pathnamePanel1.add(new JLabel());
		pathnamePanel1.add(dataConfigText);
		pathnamePanel1.add(new JLabel());
		JPanel pathnamePanel2 = new JPanel();
		pathnamePanel2.setLayout(new GridLayout(1, 2));
		pathnamePanel2.setBackground(this.colorBackground);
		pathnamePanel2.add(this.numberJTF);
		pathnamePanel2.add(txtText);
		JPanel pathnamePanel3 = new JPanel();
		pathnamePanel3.setBackground(this.colorBackground);
		pathnamePanel3.add(this.playButtonSettings);
		pathnamePanel.add(pathnamePanel1);
		pathnamePanel.add(pathnamePanel2);
		pathnamePanel.add(pathnamePanel3);
		JPanel panel2_Settings = new JPanel();
		panel2_Settings.setBackground(this.colorBackground);
		panel2_Settings.setLayout(new GridLayout(1, 2));
		JLabel changeLangText = new JLabel(this.getLanguage() == Language.ENGLISH ? "Change the language:"
				: this.getLanguage() == Language.FRENCH ? "Changer de langage:" : "Cambiar el idioma:");
		changeLangText.setFont(fontText);
		this.listLanguages = new JComboBox<String>(
				this.getLanguage() == Language.ENGLISH ? new String[] { "English", "French", "Spanish" }
						: this.getLanguage() == Language.FRENCH ? new String[] { "Anglais", "Français", "Espagnol" }
								: new String[] { "Inglés", "Francés", "Español" });
		this.listLanguages.setBackground(Color.white);
		this.listLanguages.setBorder(new MatteBorder(1, 1, 1, 1, Color.blue));
		listLanguages.setSelectedIndex(0);
		JPanel panel3_Settings = new JPanel();
		panel3_Settings.setBackground(this.colorBackground);
		panel3_Settings.setLayout(new GridLayout(1, 2));
		JLabel leaveGameText = new JLabel(this.getLanguage() == Language.ENGLISH ? "Leave the game:"
				: this.getLanguage() == Language.FRENCH ? "Quitter le jeu:" : "Dejar el juego:");
		leaveGameText.setFont(fontText);
		this.quitButtonSettings = new JButton(this.getLanguage() == Language.ENGLISH ? "Leave"
				: this.getLanguage() == Language.FRENCH ? "Quitter" : "Dejar");
		this.quitButtonSettings.setFont(fontText);
		this.quitButtonSettings.setBackground(Color.white);
		this.quitButtonSettings.setBorder(new MatteBorder(2, 2, 2, 2, Color.red));
		centerPanelSettings.add(new JLabel());
		panel1_Settings.add(loadGameText);
		panel1_Settings.add(pathnamePanel);
		centerPanelSettings.add(panel1_Settings);
		centerPanelSettings.add(new JLabel());
		panel2_Settings.add(changeLangText);
		panel2_Settings.add(listLanguages);
		centerPanelSettings.add(panel2_Settings);
		centerPanelSettings.add(new JLabel());
		panel3_Settings.add(leaveGameText);
		panel3_Settings.add(this.quitButtonSettings);
		centerPanelSettings.add(panel3_Settings);
		centerPanelSettings.add(new JLabel());
		JPanel panelEmptyLeftSettings = new JPanel();
		panelEmptyLeftSettings.setPreferredSize(new Dimension(50, 400));
		panelEmptyLeftSettings.setBackground(this.colorBackground);
		JPanel panelEmptyRightSettings = new JPanel();
		panelEmptyRightSettings.setPreferredSize(new Dimension(50, 400));
		panelEmptyRightSettings.setBackground(this.colorBackground);
		this.settingsFrame.add(panelEmptyLeftSettings, BorderLayout.EAST);
		this.settingsFrame.add(panelEmptyRightSettings, BorderLayout.WEST);
		this.settingsFrame.add(topPanelSettings, BorderLayout.NORTH);
		this.settingsFrame.add(centerPanelSettings, BorderLayout.CENTER);

		// Initializing the rules frame.
		this.rulesFrame = new JFrame();
		this.rulesFrame.setTitle("Zen Game - Rules");
		this.rulesFrame.setSize(1000, 700);
		this.rulesFrame.setResizable(false);
		this.rulesFrame.setLocationRelativeTo(null);
		this.rulesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.rulesFrame.getContentPane().setLayout(new BorderLayout());
		this.rulesFrame.setBackground(Color.white);

		// Listeners
		GraphicalViewListener homeVisualListener = new GraphicalViewListener(this);
		this.playButton.addMouseListener(homeVisualListener);
		this.settingsButton.addMouseListener(homeVisualListener);
		this.playButtonSettings.addMouseListener(homeVisualListener);
		this.quitButtonSettings.addMouseListener(homeVisualListener);
	}

	@Override
	public void printHome() {
		this.homeFrame.setVisible(true);
	}

	/**
	 * Hide the home page.
	 */
	public void hideHome() {
		this.homeFrame.setVisible(false);
	}

	@Override
	public void printSettings() {
		this.settingsFrame.setVisible(true);
	}

	/**
	 * Hide the settings page.
	 */
	public void hideSettings() {
	}

	@Override
	public void printRetrievingInformations() {
	}

	@Override
	public void printBoard(Square[][] squareList, Player current, Player opponent, DefaultListModel<String> listMoves,
			int nbMove) {
		if (squareList == null || current == null || opponent == null || listMoves == null)
			throw new IllegalArgumentException(
					"GraphicalView : printBoard(Square[][], Player, Player, DefaultListModel<String>, int) : parameter null.");
		else {
			for (int i = 0; i < squareList.length; i++) {
				for (int j = 0; j < squareList[0].length; j++) {
					if (squareList[i][j] == null)
						throw new IllegalArgumentException(
								"GraphicalView : printBoard(Square[][], Player, Player, DefaultListModel<String>, int) : a Square ("
										+ i + "," + j + ") in the \"squareList\" array is null.");

				}
			}
			this.modelHistoryMoves = listMoves;

			this.homeFrame.setVisible(false);
		}
	}

	public String getPathname() {
		return this.pathname;
	}

	@Override
	public String askStartPosition() {
		String answerStart = null;
		String title = (this.getLanguage() == Language.ENGLISH ? "NEW SHOT"
				: this.getLanguage() == Language.FRENCH ? "NOUVEAU COUP" : "NUEVO TIRO");
		String textStart = (this.getLanguage() == Language.ENGLISH ? "Which pawn do you want to move?"
				: this.getLanguage() == Language.FRENCH ? "Quelle pièce voulez-vous déplacer ?"
						: "¿Qué peón quieres mover?");

		while (answerStart == null)
			answerStart = JOptionPane.showInputDialog(null, textStart, title, JOptionPane.INFORMATION_MESSAGE);

		return answerStart;
	}

	@Override
	public String askFinishPosition() {
		String answerFinish = null;
		String title = (this.getLanguage() == Language.ENGLISH ? "NEW SHOT"
				: this.getLanguage() == Language.FRENCH ? "NOUVEAU COUP" : "NUEVO TIRO");
		String textFinish = (this.getLanguage() == Language.ENGLISH ? "Where do you want it moved to?"
				: this.getLanguage() == Language.FRENCH ? "Où est-ce que vous voulez la déplacer ?"
						: "¿Adónde quiere que lo traslademos?");

		while (answerFinish == null)
			answerFinish = JOptionPane.showInputDialog(null, textFinish, title, JOptionPane.INFORMATION_MESSAGE);

		return answerFinish;
	}

	@Override
	public String askPathname() {

		// While the given pathname is incorrect.
		while (this.pathname != null && (!this.pathname.contains("data/config") && !this.pathname.contains(".txt")
				&& !(new File(this.pathname)).exists() && !this.pathname.equals("1"))) {
			if (this.getLanguage() == Language.ENGLISH)
				this.pathname = JOptionPane.showInputDialog(null,
						"Enter the pathname of the previous game:\nGo back, write: 1", "Pathname",
						JOptionPane.INFORMATION_MESSAGE);
			else if (this.getLanguage() == Language.FRENCH)
				this.pathname = JOptionPane.showInputDialog(null,
						"Entrez le nom du chemin d'accès de la partie précédente :\nRevenir, écrivez: 1", "Nom du path",
						JOptionPane.INFORMATION_MESSAGE);
			else
				this.pathname = JOptionPane.showInputDialog(null,
						"Introduzca el camino para acceder a la partida anterior:\nVolver, escribe: 1",
						"Nombre del path", JOptionPane.INFORMATION_MESSAGE);

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
	public void printEnd(String playerName1, String playerName2, boolean isThereATie) {
		if (playerName1 == null || playerName2 == null)
			throw new IllegalArgumentException("GraphicalView : printEnd(String, String, boolean) : parameter null.");
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
			JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void printRules() {
		JLabel textRules = new JLabel(this.rules.toString());
		textRules.setFont(new Font("Calibri", Font.BOLD, 20));
		this.rulesFrame.add(textRules, BorderLayout.CENTER);
		this.rulesFrame.setVisible(true);
	}

	/**
	 * Hide the rules page.
	 */
	public void hideRules() {
		this.rulesFrame.setVisible(false);
	}

	@Override
	public void printFiles() {
		this.filesFrame.setVisible(true);
	}

	/**
	 * Hide the files page.
	 */
	public void hideFiles() {
		this.filesFrame.setVisible(false);
	}

	public void setPathname(String path) {
		if (path == null)
			throw new IllegalArgumentException("GraphicalView : setPathname(String) : parameter \"path\" null.");
		else
			this.playerName1 = path;
	}

	public String getPlayerName1() {
		return this.playerName1;
	}

	public void setPlayerName1(String name) {
		if (name == null)
			throw new IllegalArgumentException("GraphicalView : setPlayerName1(String) : parameter \"name\" null.");
		else
			this.playerName1 = name;
	}

	public String getPlayerName2() {
		return this.playerName2;
	}

	public void setPlayerName2(String name) {
		if (name == null)
			throw new IllegalArgumentException("GraphicalView : setPlayerName2(String) : parameter \"name\" null.");
		else
			this.playerName2 = name;
	}

	public JButton getPlayButton() {
		return this.playButton;
	}

	public JButton getSettingsButton() {
		return this.settingsButton;
	}

	public JButton getBackButton() {
		return this.backButton;
	}

	public JButton getPlayButtonSettings() {
		return this.playButtonSettings;
	}

	public JButton getLeaveButton() {
		return this.quitButtonSettings;
	}

	public JComboBox<String> getJCBLanguage() {
		return this.listLanguages;
	}

	public JTextField getJTF() {
		return this.numberJTF;
	}
}