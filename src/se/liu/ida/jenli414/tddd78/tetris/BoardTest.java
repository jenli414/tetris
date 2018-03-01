package se.liu.ida.jenli414.tddd78.tetris;


/**
 * Main class of Tetris
 **/
public final class BoardTest
{
    private BoardTest() {
    }

    public static void main(String[] args) {
	new TetrisFrame(new Board(17,12));
    }
}
