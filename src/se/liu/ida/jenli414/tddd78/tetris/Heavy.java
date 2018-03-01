package se.liu.ida.jenli414.tddd78.tetris;

import java.util.ArrayList;
import java.util.List;

/**
 * Fall handler for when the "Heavy" Powerup is active.
 **/
public class Heavy implements FallHandler
{
    private Board b;
    private List<Position> colsToCompress = new ArrayList<>();

    public Heavy(final Board b) {
        this.b = b;
    }

    @Override public boolean hasCollision(Move move) {
	if (b.getFalling() == null) {
	    return false;
	} else if (b.hasBorderCollision()) {
	    return true;
	} else {
	    colsToCompress.clear();
	    boolean canCompressWithMove = move == Move.DOWN;
	    for (int row = b.getFallingY(); row < b.getFallingY() + b.getFalling().getSize(); row++) {
		for (int col = b.getFallingX(); col < b.getFallingX() + b.getFalling().getSize(); col++) {
		    boolean isBoardOccupied = b.getSquareTypeInBoardAt(row, col) != SquareType.EMPTY;
		    if (isBoardOccupied && b.isFallingAt(row, col)) {
		        if (!canCompressWithMove || !b.canCompressColBelowPos(new Position(row, col))) {
			    return true;
			} else {
			    colsToCompress.add(new Position(row,col));
			}
		    }
		}
	    }
	    for (Position pos : colsToCompress) {
		b.compressColBelowPos(pos);
	    }
	    return false;
	}

    }

    @Override public String getDescription() {
    	return "Heavy!";
    }
}
