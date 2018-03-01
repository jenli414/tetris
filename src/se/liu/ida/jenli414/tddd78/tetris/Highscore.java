package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Entry for the highscore list containing name and score.
 **/
public class Highscore
{
    private String name;
    private int score;

    public Highscore(final String name, final int score) {
	this.name = name;
	this.score = score;
    }

    public String getName() {
	return name;
    }

    public int getScore() {
	return score;
    }

    @Override public String toString() {
	return name + " - " + score + " points";
    }
}
