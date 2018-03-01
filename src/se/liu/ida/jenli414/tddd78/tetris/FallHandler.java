package se.liu.ida.jenli414.tddd78.tetris;


/**
 * Interface for FallHandlers, i.e. classes that determine if there have been collisions in board.
 * **/
public interface FallHandler
{
    public boolean hasCollision(Move move);

    public String getDescription();
}
