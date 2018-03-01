package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Board containing information about what the tetris game currently looks like
 * and how the player may interact with it.
 * **/
public class Board
{
    private Random rnd;

    private List<BoardListener> boardListeners = new ArrayList<>();

    private Poly falling = null;
    private int fallingX;
    private int fallingY;

    private FallHandler defaultFallHandler = new DefaultFallHandler(this);
    private FallHandler fallHandler = defaultFallHandler;
    private List<FallHandler> powerupFallHandlers = new ArrayList<>();
    private int numPowerups;

    private SquareType[][] squares;

    /** Size of OUTSIDE border around board. **/
    public final static int OUTSIDE = 2;
    private int height;
    private int width;
    private int totalHeight;
    private int totalWidth;

    private int score = 0;
    private int lastPowerupScore = 0;
    private final static int POWERUP_EVERY = 300;
    private boolean gameOver;

    public static void main(String[] args) {
        int height = 20;
        int width = 15;
    	Board board = new Board(height,width);
    }

    public Board(final int height, final int width) {
	rnd = new Random();

        this.height = height;
        this.width = width;
        this.totalHeight = height + (OUTSIDE*2);
	this.totalWidth = width + (OUTSIDE*2);

        // Powerups
	powerupFallHandlers.add(new Fallthrough(this));
	powerupFallHandlers.add(new Heavy(this));
	numPowerups = powerupFallHandlers.size();

	squares = new SquareType[totalHeight][totalWidth];
	for (int row = 0; row < totalHeight; row++) {
		for (int col = 0; col < totalWidth; col++) {
		squares[row][col] = SquareType.OUTSIDE;
	    }
	}
	restartGame();
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public int getScore() {
        return score;
    }

    public FallHandler getFallHandler() {
	return fallHandler;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    /**
     * Returns SquareType at (row, col) (board or falling. As if there was no border).
     * **/
    public SquareType getSquareTypeAt(final int row, final int col) {
    	if (isFallingAt(row, col)) {
    	    int fallingRow = row + OUTSIDE - fallingY;
    	    int fallingCol = col + OUTSIDE - fallingX;
    	    return falling.getSquareTypeAt(fallingRow, fallingCol);
    	} else {
    	    return getSquareTypeInBoardAt(row, col);
    	}
    }

    /**
     * Returns SquareType at (row, col) in _board_ (As if there was no OUTSIDE border).
     * **/
    public SquareType getSquareTypeInBoardAt(final int row, final int col) {
	return squares[row + OUTSIDE][col + OUTSIDE];
    }

    /**
     * Returns true if falling is colliding with the border around the board.
     * **/
    public boolean hasBorderCollision() {
        if (falling == null) {
            return false;
	}
	boolean outsideWidthBounds = fallingX < 0 || fallingX >= totalWidth;
	boolean outsideHeightBounds = fallingY < 0 || fallingY >= totalHeight;
	if (outsideWidthBounds || outsideHeightBounds) {
	    return true;
	}
	for (int row = fallingY; row < fallingY + falling.getSize() ; row++) {
	    for (int col = fallingX; col < fallingX + falling.getSize() ; col++) {
	        int fallingRow = row - fallingY;
	        int fallingCol = col - fallingX;
		if (falling.getSquareTypeAt(fallingRow,fallingCol) != SquareType.EMPTY &&
		    squares[row][col] == SquareType.OUTSIDE) {
		    return true;
		}
	    }
	}
	return false;
    }

    private void setEmptyBoard() {
	for (int row = OUTSIDE; row < height + OUTSIDE; row++) {
	    for (int col = OUTSIDE; col < width + OUTSIDE; col++) {
		squares[row][col] = SquareType.EMPTY;
	    }
	}
    	notifyListeners();
    }

    private void addNewFalling() {
	falling = TetrominoMaker.getPoly(rnd.nextInt(TetrominoMaker.getNumberOfTypes()));
	fallingX = (width / 2) - (falling.getSize() / 2) + OUTSIDE;
	fallingY = OUTSIDE;

	if (fallHandler.hasCollision(Move.DOWN)) {
	    falling = null;
	    gameOver = true;
	}
	notifyListeners();
    }

    private void addFallingToBoard() {
	for (int row = fallingY; row < fallingY + falling.getSize(); row++) {
	    for (int col = fallingX; col < fallingX + falling.getSize(); col++) {
		int fallingRow = row - fallingY;
		int fallingCol = col - fallingX;
		SquareType fallingSquareType = falling.getSquareTypeAt(fallingRow, fallingCol);
		if (fallingSquareType != SquareType.EMPTY) {
		    squares[row][col] = fallingSquareType;
		}
	    }
	}
	fallHandler = defaultFallHandler;
	notifyListeners();
    }

    public Poly getFalling() {
	return falling;
    }

    public int getFallingX() {
	return fallingX - OUTSIDE;
    }

    public int getFallingY() {
	return fallingY - OUTSIDE;
    }

    public boolean isFallingAt(final int row, final int col) {
        int rowIndex = row + OUTSIDE;
        int colIndex = col + OUTSIDE;

        if (falling == null) {
            return false;
        }
        boolean isWithinRow = rowIndex >= fallingY &&
		      rowIndex < fallingY + falling.getSize();
	boolean isWithinCol = colIndex >= fallingX &&
		      colIndex < fallingX + falling.getSize();
	if (isWithinRow && isWithinCol) {
	    int fallingRow = rowIndex - fallingY;
	    int fallingCol = colIndex - fallingX;
	    return falling.getSquareTypeAt(fallingRow, fallingCol) != SquareType.EMPTY;
	} else {
	    return false;
	}
    }

    private enum Rotation {
	LEFT(3,1), RIGHT(1,3);

    	public final int times; // Times to rotate to complete rotation
            public final int timesBack; // Times to rotate to go back to initial rotation

            Rotation(final int times, final int timesBack) {
                this.times = times;
    	    	this.timesBack = timesBack;
    	}
    }

    private void rotateFallingClockwise() {
        if (falling != null) {
	    int size = falling.getSize();
	    Poly rotatedPoly = new Poly(new SquareType[size][size]);

	    for (int row = 0; row < size; row++) {
		for (int col = 0; col < size; col++) {
		    SquareType squareType = falling.getSquareTypeAt(row, col);
		    rotatedPoly.setSquareTypeAt(col, size - 1 - row, squareType);
		}
	    }
	    falling = rotatedPoly;
	}
    }

    private void rotateFalling(Rotation rotation) {
        if (falling != null) {
            for (int times = 0; times < rotation.times; times++) {
	    	rotateFallingClockwise();
            }
	    if (fallHandler.hasCollision(Move.ROTATION)) {
		for (int times = 0; times < rotation.timesBack; times++) {
		    rotateFallingClockwise();
		}
	    } else {
		notifyListeners();
	    }
	}
    }

    /**
     * Returns true if col can be compressed, i.e if there is an EMPTY square
     * below the startPos.
     **/
    public boolean canCompressColBelowPos(final Position startPos) {
        int lastRow = totalHeight;

	for (int row = startPos.getRow() + OUTSIDE; row < lastRow; row++) {
	    if (squares[row][startPos.getCol() + OUTSIDE] == SquareType.EMPTY) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Compresses col from startPos down to first EMPTY square.
     * **/
    public void compressColBelowPos(Position startPos) {
	int fromRow = startPos.getRow() + OUTSIDE;
        int col = startPos.getCol() + OUTSIDE;
        int lastRow = totalHeight;

	for (int row = fromRow; row < lastRow; row++) {
	    if (squares[row][col] == SquareType.EMPTY) {
		for (int shiftRow = row; shiftRow > fromRow; shiftRow--) {
		    squares[shiftRow][col] = squares[shiftRow - 1][col];
		}
		squares[fromRow][col] = SquareType.EMPTY;
		break;
	    }
	}
	notifyListeners();
    }

    public void addBoardListener(BoardListener boardListener) {
        boardListeners.add(boardListener);
    }

    private void notifyListeners() {
	for (BoardListener boardListener : boardListeners) {
	    boardListener.boardChanged();
	}
    }

    public void tick() {
        if (!gameOver) {
	    if (falling == null) {
		addNewFalling();
	    } else {
		move(Move.DOWN);
	    }
	}
    }

    private void move(Move move) {
    	if (falling != null) {
	    fallingX += move.deltaX;
	    fallingY += move.deltaY;
	    if (fallHandler.hasCollision(move)) {
		fallingX -= move.deltaX;
		fallingY -= move.deltaY;
		if (move == Move.DOWN) {
		    addFallingToBoard();
		    removeCompleteRows();
		    falling = null;
		}
	    }
	    notifyListeners();
	}
    }

    /****/
    public Action moveRight = new AbstractAction() {
    	@Override public void actionPerformed(final ActionEvent e) {
    	    move(Move.RIGHT);
    	}
    };

    /****/
    public Action moveLeft = new AbstractAction() {
	@Override public void actionPerformed(final ActionEvent e) {
	    move(Move.LEFT);
	}
    };

    /****/
    public Action moveDown = new AbstractAction() {
    	@Override public void actionPerformed(final ActionEvent e) {
    	    move(Move.DOWN);
    	}
    };

    /****/
    public Action rotateRight = new AbstractAction() {
	@Override public void actionPerformed(final ActionEvent e) {
	    rotateFalling(Rotation.RIGHT);
	}
    };

    /****/
    public Action rotateLeft = new AbstractAction() {
    	@Override public void actionPerformed(final ActionEvent e) {
    	    rotateFalling(Rotation.LEFT);
    	}
    };

    private void removeCompleteRows() {
    	int completeRows = 0;
    	while (removeCompleteRow()) {
    	    completeRows++;
	}
	incrementScore(completeRows);
	notifyListeners();
    }

    private boolean removeCompleteRow() {
	for (int row = OUTSIDE; row < height + OUTSIDE; row++) {
	    if (isCompleteRow(squares[row])) {
		for (int destRow = row; destRow > OUTSIDE; destRow--) {
		    SquareType[] rowAbove = squares[destRow - 1];
		    System.arraycopy(rowAbove, 0, squares[destRow], 0, rowAbove.length);
		}
		for (int col = OUTSIDE; col < width + OUTSIDE; col++) {
		    squares[OUTSIDE][col] = SquareType.EMPTY;
		}
		notifyListeners();
		return true;
	    }
	}
	return false;
    }

    private boolean isCompleteRow(SquareType[] row) {
    	for (final SquareType squareType : row) {
    	    if (squareType == SquareType.EMPTY) {
    		return false;
    	    }
    	}
    	return true;
    }

    private void incrementScore(final int completeRows) {
        switch (completeRows) {
	    case 1:
	        score += 100;
	        break;
	    case 2:
	        score += 300;
	        break;
	    case 3:
	        score += 500;
	        break;
	    case 4:
	        score += 800;
	        break;
	    default:
	        return;
	}

	if (score - lastPowerupScore >= POWERUP_EVERY) {
            activateRndPowerup();
	}
	notifyListeners();
    }

    private void activateRndPowerup() {
        if (numPowerups > 0) {
	    fallHandler = powerupFallHandlers.get(rnd.nextInt(numPowerups));
	    lastPowerupScore = score;
	    notifyListeners();
	}
    }

    public void restartGame() {
	setEmptyBoard();
	falling = null;
	score = 0;
	lastPowerupScore = 0;
	fallHandler = defaultFallHandler;
	gameOver = false;
	notifyListeners();
    }

}
