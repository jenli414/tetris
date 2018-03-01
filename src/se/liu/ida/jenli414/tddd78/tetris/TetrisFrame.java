package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;

/**
 * The game frame
 * **/
public class TetrisFrame extends JFrame
{

    private Board b;
    private TetrisComponent tetrisComponent;

    private ScorePanel scorePanel;

    private HighscoreList highscoreList = HighscoreList.getInstance();
    private JPanel highscoreListComponent;
    private boolean showingHighscores = false;

    private final static int TICK_TIME = 300;
    private final Timer tickTimer;

    public TetrisFrame(Board b) throws HeadlessException {
	super("Awesome Tetris");

	setLayout(new BorderLayout());
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.b = b;

	// Tetris component
	tetrisComponent = new TetrisComponent(b, getColors());
	b.addBoardListener(tetrisComponent);
	add(tetrisComponent, BorderLayout.NORTH);

	//ScoreLabel
	scorePanel = new ScorePanel(b);
	b.addBoardListener(scorePanel);
	add(scorePanel, BorderLayout.CENTER);

	// Return button for highscore list
	highscoreListComponent = highscoreList.getHighscoreListComponent(tetrisComponent.getPreferredSize(),
									 scorePanel.getPreferredSize());
	highscoreList.getReturnButton().setAction(new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		showTetrisComponent();
	    }
	});

	setMenuBar();
	setKeyBindings(tetrisComponent);
	setKeyBindings(highscoreListComponent);

	// Timer
	tickTimer = new Timer(TICK_TIME, tickAction);
	tickTimer.setCoalesce(true);

	// Start game
	pack();
	setVisible(true);
	JOptionPane.showMessageDialog(tetrisComponent, "Get ready!");
	tickTimer.start();
    }

    private static EnumMap<SquareType, Color> getColors() {
        EnumMap<SquareType, Color> colors = new EnumMap<>(SquareType.class);
        colors.put(SquareType.EMPTY, Color.WHITE);
	colors.put(SquareType.OUTSIDE, Color.BLACK);
	colors.put(SquareType.I, Color.MAGENTA);
	colors.put(SquareType.O, Color.BLUE);
	colors.put(SquareType.T, Color.YELLOW);
	colors.put(SquareType.S, Color.GREEN);
	colors.put(SquareType.Z, Color.ORANGE);
	colors.put(SquareType.J, Color.RED);
	colors.put(SquareType.L, Color.CYAN);

	return colors;
    }

    private void setMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Options");
	menuBar.add(menu);

	// Highscore List option
	JMenuItem highscoreListOption = new JMenuItem("Show/hide highscores");
	highscoreListOption.addActionListener(toggleHighscoreListAction);
	menu.add(highscoreListOption);

	// Pause option
	JMenuItem pauseOption = new JMenuItem("Pause");
	pauseOption.addActionListener(pauseAction);
	menu.add(pauseOption);

	// Restart option
	JMenuItem restartOption = new JMenuItem("Restart game");
	restartOption.addActionListener(restartAction);
	menu.add(restartOption);

	// Exit option
	JMenuItem exitOption = new JMenuItem("Exit");
	exitOption.addActionListener(exitAction);
	menu.add(exitOption);

	setJMenuBar(menuBar);
    }

    private void setKeyBindings(JComponent component) {
	final InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	inputMap.put(KeyStroke.getKeyStroke("H"), "highscoreList");
	inputMap.put(KeyStroke.getKeyStroke("P"), "pause");
	inputMap.put(KeyStroke.getKeyStroke("R"), "restart");
	inputMap.put(KeyStroke.getKeyStroke("E"), "exit");

	final ActionMap actionMap = component.getActionMap();
	actionMap.put("highscoreList", toggleHighscoreListAction);
	actionMap.put("pause", pauseAction);
	actionMap.put("restart", restartAction);
	actionMap.put("exit", exitAction);
    }

    private void showTetrisComponent() {
	if (showingHighscores) {
	    remove(highscoreListComponent);
	    highscoreListComponent.setVisible(false);

	    add(tetrisComponent, BorderLayout.NORTH);
	    tetrisComponent.setVisible(true);
	    add(scorePanel, BorderLayout.SOUTH);
	    scorePanel.setVisible(true);

	    pack();

	    showingHighscores = false;

	    if (!b.isGameOver()) {
	        tickTimer.stop();
		JOptionPane.showMessageDialog(tetrisComponent, "Paused.");
		tickTimer.start();
	    }
    	}
    }

    private void showHighscoreList() {
        if (!showingHighscores) {
	    tickTimer.stop();

	    remove(tetrisComponent);
	    tetrisComponent.setVisible(false);
	    remove(scorePanel);
	    scorePanel.setVisible(false);

	    highscoreListComponent = highscoreList.getHighscoreListComponent(tetrisComponent.getPreferredSize(),
	    								 scorePanel.getPreferredSize());
	    setKeyBindings(highscoreListComponent);
	    add(highscoreListComponent, BorderLayout.NORTH);
	    highscoreListComponent.setVisible(true);

	    pack();

	    showingHighscores = true;
	}
    }

    private final Action tickAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    if (b.isGameOver()) {
	        tickTimer.stop();
		JOptionPane.showMessageDialog(tetrisComponent, "Game over!");
		highscoreList.addScore(b.getScore());
		if (JOptionPane.showConfirmDialog(null, "Restart game?",
		     "Restart", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    b.restartGame();
		    JOptionPane.showMessageDialog(tetrisComponent, "Get ready!");
		    tickTimer.start();
		}
	    } else {
		b.tick();
	    }
	}
    };

    private final Action toggleHighscoreListAction = new AbstractAction() {
	public void actionPerformed(final ActionEvent e) {
	    if (!showingHighscores) {
		showHighscoreList();
	    } else {
	        showTetrisComponent();
	    }
	}
    };

    private final Action pauseAction = new AbstractAction() {
    	public void actionPerformed(final ActionEvent e) {
	    if (!showingHighscores && !b.isGameOver()) {
    	    	tickTimer.stop();
		JOptionPane.showMessageDialog(tetrisComponent, "Paused.");
		tickTimer.start();
	    }
    	}
    };

    private final Action restartAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    if (showingHighscores) {
		showTetrisComponent();
	    }
	    tickTimer.stop();
	    if (JOptionPane.showConfirmDialog(null, "Restart game?", "Restart", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		highscoreList.addScore(b.getScore());
		b.restartGame();
		JOptionPane.showMessageDialog(tetrisComponent, "Get ready!");
	    }
	    if (!b.isGameOver()) {
		tickTimer.start();
	    }
	}
    };

    private final Action exitAction = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    tickTimer.stop();
	    if (JOptionPane.showConfirmDialog(null, "Exit game?",
		 "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		System.exit(0);
	    }
	    if (!showingHighscores) {
		tickTimer.start();
	    }
	}
    };
}
