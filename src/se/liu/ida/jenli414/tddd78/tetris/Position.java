package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Object that represents a position (row,col) in board.
 **/
public class Position
{
    private int row;
    private int col;

    public Position(final int row, final int col) {
	this.row = row;
	this.col = col;
    }

    public int getRow() {
	return row;
    }

    public int getCol() {
	return col;
    }

    public void setRow(final int row) {
	this.row = row;
    }

    public void setCol(final int col) {
	this.col = col;
    }
}
