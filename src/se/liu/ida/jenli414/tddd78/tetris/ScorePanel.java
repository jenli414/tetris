package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Label for displaying the current score and highest highscore
 **/
public class ScorePanel extends JPanel implements BoardListener
{
    private Board board;
    private final static int FONTSIZE = 18;
    private final static int SCOREHEIGHT = 80;
    private final static int SCOREWIDTH = 350;
    private final Dimension preferredSize = new Dimension(SCOREWIDTH, SCOREHEIGHT);

    private JLabel scoreLabel;
    private JLabel highscoreLabel;
    private JLabel powerupLabel;

    public ScorePanel(final Board board) {
	super(new GridLayout(3,1));
        this.board = board;

	setFocusable(false);

	scoreLabel = new JLabel();
	scoreLabel.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(scoreLabel);

	highscoreLabel = new JLabel();
	highscoreLabel.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	highscoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(highscoreLabel);

	powerupLabel = new JLabel();
	powerupLabel.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
	powerupLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(powerupLabel);
    }

    @Override public Dimension getPreferredSize() {
    	return preferredSize;
    }

    @Override public void boardChanged() {
	scoreLabel.setText("Score: " + board.getScore() + " points");

	Highscore highestHighscore = HighscoreList.getInstance().getHighestHighscore();
	highscoreLabel.setText("Highscore: " + highestHighscore);

	powerupLabel.setText(board.getFallHandler().getDescription());
    }
}
