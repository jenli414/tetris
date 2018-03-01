package se.liu.ida.jenli414.tddd78.tetris;

/**
 * A tetris block
 * **/
public class Poly
{
    private SquareType[][] squares;

    public Poly(final SquareType[][] squares) {
	this.squares = squares;
    }

    public int getSize() {
        return squares.length;
    }

    public SquareType[][] getSquares() {
        return squares;
    }

    public SquareType getSquareTypeAt(final int row, final int col) {
    	return squares[row][col];
    }

    public void setSquareTypeAt(final int row, final int col, SquareType squareType) {
        squares[row][col] = squareType;
    }
}
