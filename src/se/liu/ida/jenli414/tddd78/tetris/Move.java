package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Enum for how a tetris block is moving.
 **/
public enum Move
{
    /** **/
    RIGHT(1,0),

    /** **/
    LEFT(-1,0),

    /** **/
    DOWN(0,1),

    /** **/
    ROTATION(0,0);

    /** **/
    public final int deltaX;
    /** **/
    public final int deltaY;

    Move(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
	this.deltaY = deltaY;
    }
}
