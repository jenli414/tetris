package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Creates Polys
 * **/
public final class TetrominoMaker
{
    private TetrominoMaker() {
    }

    public static Poly getPoly(int typeNum) {
        if (typeNum >= 0 && typeNum < getNumberOfTypes()) {
	    SquareType squareType = SquareType.values()[typeNum];
	    switch (squareType) {
		case I:
		    return getPolyI();
		case O:
		    return getPolyO();
		case T:
		    return getPolyT();
		case S:
		    return getPolyS();
		case Z:
		    return getPolyZ();
		case J:
		    return getPolyJ();
		case L:
		    return getPolyL();
		default:
		    throw new IllegalArgumentException("Illegal SquareType!");
	    }
	} else {
            throw new IllegalArgumentException("Illegal type number: " + typeNum + "!");
	}
    }

    public static int getNumberOfTypes() {
        return SquareType.values().length - 2;
    }

    public static SquareType[][] getEmptySquares(int size) {
	SquareType[][] squares = new SquareType[size][size];
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {
		squares[row][col] = SquareType.EMPTY;
	    }
	}
	return squares;
    }

    public static Poly getPolyI() {
        int size = 4;
        SquareType[][] squares = getEmptySquares(size);

	squares[1][0] = SquareType.I;
	squares[1][1] = SquareType.I;
	squares[1][2] = SquareType.I;
	squares[1][3] = SquareType.I;

	return new Poly(squares);
    }

    public static Poly getPolyO() {
        int size = 2;
	SquareType[][] squares = getEmptySquares(size);
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {
		squares[row][col] = SquareType.O;
	    }
	}

	return new Poly(squares);
    }

    public static Poly getPolyT() {
	SquareType[][] squares = getEmptySquares(3);

	squares[0][1] = SquareType.T;
	squares[1][0] = SquareType.T;
	squares[1][1] = SquareType.T;
	squares[1][2] = SquareType.T;

	return new Poly(squares);
    }

    public static Poly getPolyS() {
	SquareType[][] squares = getEmptySquares(3);

    	squares[0][1] = SquareType.S;
    	squares[0][2] = SquareType.S;
    	squares[1][0] = SquareType.S;
    	squares[1][1] = SquareType.S;

    	return new Poly(squares);
    }

    public static Poly getPolyZ() {
    	SquareType[][] squares = getEmptySquares(3);

	squares[0][0] = SquareType.Z;
	squares[0][1] = SquareType.Z;
	squares[1][1] = SquareType.Z;
	squares[1][2] = SquareType.Z;

	return new Poly(squares);
    }

    public static Poly getPolyJ() {
	SquareType[][] squares = getEmptySquares(3);

    	squares[0][0] = SquareType.J;
    	squares[1][0] = SquareType.J;
    	squares[1][1] = SquareType.J;
    	squares[1][2] = SquareType.J;

    	return new Poly(squares);
    }

    public static Poly getPolyL() {
    	SquareType[][] squares = getEmptySquares(3);

	squares[0][2] = SquareType.L;
	squares[1][0] = SquareType.L;
	squares[1][1] = SquareType.L;
	squares[1][2] = SquareType.L;

	return new Poly(squares);
    }
}
