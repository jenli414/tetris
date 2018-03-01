package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * List of highscores
 * **/
public final class HighscoreList {

    private static final HighscoreList INSTANCE = new HighscoreList();

    private static final int NUM_HIGHSCORES = 10;
    private List<Highscore> highscores = new ArrayList<>();

    private JButton returnButton;

    private final static int COL_WIDTH = 180;
    private final static int COL_HEIGHT = 80;
    private final static int FONTSIZE = 20;

    private HighscoreList() {
	returnButton = new JButton();
	returnButton.setPreferredSize(new Dimension(150,45));

	for (int i = 0; i < NUM_HIGHSCORES; i++) {
	    highscores.add(new Highscore("N/A", 0));
	}
    }

    public static HighscoreList getInstance() {
        return INSTANCE;
    }

    private Highscore getNewHighscore(int score) {
    	String nameInput = JOptionPane.showInputDialog("New Highscore!" + "\n" + "Enter your name: ");

    	if (nameInput == null || nameInput.isEmpty()) {
    	    nameInput = "Unknown";
    	}

    	String name = nameInput.substring(0, 1).toUpperCase() + nameInput.substring(1).toLowerCase();

    	return new Highscore(name, score);
    }

    public void addScore(int score) {
    	if (score > 0 && highscores.size() < NUM_HIGHSCORES) {
	    highscores.add(getNewHighscore(score));
    	} else if (highscores.size() == NUM_HIGHSCORES) {
    	    int lowestHighscore = highscores.get(NUM_HIGHSCORES - 1).getScore();
    	    if (score > lowestHighscore) {
		highscores.add(getNewHighscore(score));
    	    }
    	}

    	Collections.sort(highscores, new ScoreComparator());

    	if (highscores.size() > NUM_HIGHSCORES) {
	    highscores.remove(NUM_HIGHSCORES);
    	}
    }

    public Highscore getHighestHighscore() {
        return highscores.get(0);
    }

    public JPanel getHighscoreListComponent(Dimension topPanelSize, Dimension bottonPanelSize) {

	JPanel topPanel = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	c.gridx = 0;
	c.gridy = 0;

	JLabel topCol1 = new JLabel("", SwingConstants.RIGHT);
	topCol1.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	topCol1.setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));
	JLabel topCol2 = new JLabel("Name:", SwingConstants.LEFT);
	topCol2.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	topCol2.setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));
	JLabel topCol3 = new JLabel("Score:", SwingConstants.LEFT);
	topCol3.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	topCol3.setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));

	topPanel.add(topCol1, c);
	c.gridx = 1;
	topPanel.add(topCol2, c);
	c.gridx = 2;
	topPanel.add(topCol3, c);

	c.gridy = 2;
	int num = 1;
	for (Highscore highscore : highscores) {
	    c.gridx = 0;
	    c.anchor = GridBagConstraints.CENTER;
	    JLabel col1 = new JLabel(Integer.toString(num) + ".", SwingConstants.RIGHT);
	    col1.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	    topPanel.add(col1, c);

	    c.gridx = 1;
	    c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
	    JLabel col2 = new JLabel(highscore.getName(), SwingConstants.LEFT);
	    col2.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	    topPanel.add(col2, c);

	    c.gridx = 2;
	    JLabel col3 = new JLabel(Integer.toString(highscore.getScore()), SwingConstants.LEFT);
	    col3.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	    topPanel.add(col3, c);

	    num++;
	    c.gridy++;
	}

	c.gridwidth = 3;
	c.weighty = 1.0;
	c.anchor = GridBagConstraints.NORTHWEST;
	JLabel bottomRow = new JLabel("", SwingConstants.RIGHT);
	bottomRow.setPreferredSize(new Dimension(COL_WIDTH, COL_HEIGHT));
	topPanel.add(bottomRow, c);
	topPanel.setPreferredSize(topPanelSize);

	JPanel bottomPanel = new JPanel(new FlowLayout());
	returnButton.setText("Return to Game");
	bottomPanel.add(returnButton);
	bottomPanel.setPreferredSize(bottonPanelSize);

	JPanel highscoreListPanel = new JPanel(new GridBagLayout());
	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.CENTER;

	highscoreListPanel.add(topPanel, c);
	c.gridy++;
	highscoreListPanel.add(bottomPanel, c);

	return highscoreListPanel;
    }

    public JButton getReturnButton() {
        return returnButton;
    }
}
