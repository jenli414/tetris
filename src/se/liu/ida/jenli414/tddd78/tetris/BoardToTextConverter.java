//package se.liu.ida.jenli414.tddd78.tetris;

/*public final class BoardToTextConverter
{

    private BoardToTextConverter() {
    }

    public static String convertToText(Board board) {
        StringBuilder boardStr = new StringBuilder();
	for (int row = 0; row < board.getHeight(); row++) {
	    for (int col = 0; col < board.getWidth(); col++) {
	        SquareType squareType;
	        if (board.isFallingAt(row, col)) {
		    int fallingRow = row - board.getFallingY();
		    int fallingCol = col - board.getFallingX();
	            squareType = board.getFalling().getSquareTypeAt(fallingRow, fallingCol);
		} else {
		    squareType = board.getSquareTypeAt(row, col);
		}
	        boardStr.append(getSquareStr(squareType));
	    }
	    boardStr.append("\n");
	}
	return boardStr.toString();
    }

    public static String getSquareStr(SquareType squareType) {
    	switch (squareType) {
	    case EMPTY:
	        return "#";
	    case I:
		return "I";
	    case O:
		return "O";
	    case T:
		return "T";
	    case S:
		return "S";
	    case Z:
		return "Z";
	    case J:
		return "J";
	    case L:
		return "L";
	    default:
	    	throw new IllegalArgumentException("Illegal SquareType!");
	}
    }
}*/
