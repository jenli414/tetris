package se.liu.ida.jenli414.tddd78.tetris;

import java.util.Comparator;

/**
 * Comparator for highscores
 **/
public class ScoreComparator implements Comparator<Highscore>
{
    @Override public int compare(final Highscore hs1, final Highscore hs2) {
        return Integer.compare(hs2.getScore(), hs1.getScore());
    }
}
