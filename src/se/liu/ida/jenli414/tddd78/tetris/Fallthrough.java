package se.liu.ida.jenli414.tddd78.tetris;

/**
 * Fall handler for when no Powerup is active.
 * **/
public class Fallthrough implements FallHandler
{
    private Board b;

    public Fallthrough(final Board b) {
        this.b = b;
    }

    @Override public boolean hasCollision(Move move) {
	return b.hasBorderCollision();
    }

    @Override public String getDescription() {
	return "Fallthrough!";
    }
}
