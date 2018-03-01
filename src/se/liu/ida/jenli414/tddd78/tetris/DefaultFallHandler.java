package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Fall handler for when no Powerup is active.
 * **/
public class DefaultFallHandler implements FallHandler
{
    private Board b;

    public DefaultFallHandler(final Board b) {
        this.b = b;
    }

    @Override public boolean hasCollision(Move move) {
	if (b.getFalling() == null) {
	    return false;
	} else if (b.hasBorderCollision()) {
	    return true;
	} else {
	    int fallingX = b.getFallingX();
	    int fallingY = b.getFallingY();
	    int size = b.getFalling().getSize();

	    for (int row = fallingY; row < fallingY + size; row++) {
		for (int col = fallingX; col < fallingX + size; col++) {
		    boolean isBoardOccupied = b.getSquareTypeInBoardAt(row, col) !=
					      SquareType.EMPTY;
		    if (isBoardOccupied && b.isFallingAt(row, col)) {
			return true;
		    }
		}
	    }
	    return false;
	}

    }

    @Override public String getDescription() {
    	return "No powerup";
    }
}
