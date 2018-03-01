package se.liu.ida.jenli414.tddd78.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;

/**
 * The actual tetris component
 * **/
public class TetrisComponent extends JComponent implements BoardListener
{
    private Board b;
    private Dimension preferredSize;
    private AbstractMap<SquareType, Color> colors;

    private Color borderColor;

    /** The size of a square in the b in pixels **/
    public final static int SQUARE_SIZE = 45;
    /** The size of the border around the squares in the b in pixels **/
    public final static int SQUARE_BORDER_SIZE = 3;

    public TetrisComponent(final Board b, final AbstractMap<SquareType, Color> colors) {
	this.b = b;

	int preferredWidth = b.getWidth() * (SQUARE_SIZE + SQUARE_BORDER_SIZE)
			     + SQUARE_BORDER_SIZE;
	int preferredHeight = b.getHeight() * (SQUARE_SIZE + SQUARE_BORDER_SIZE)
			      + SQUARE_BORDER_SIZE;
	preferredSize = new Dimension(preferredWidth, preferredHeight);

	this.colors = colors;

	setBorderColor();
	setKeyBindings();
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    private void setKeyBindings() {
	final InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
	inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
	inputMap.put(KeyStroke.getKeyStroke("UP"), "rotateRight");
	inputMap.put(KeyStroke.getKeyStroke("SPACE"), "rotateLeft");

	final ActionMap actionMap = getActionMap();
	actionMap.put("moveRight", b.moveRight);
	actionMap.put("moveLeft", b.moveLeft);
	actionMap.put("moveDown", b.moveDown);
	actionMap.put("rotateRight", b.rotateRight);
	actionMap.put("rotateLeft", b.rotateLeft);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        // Draw background
	g2d.setColor(borderColor);
	g2d.fillRect(0,0, (int) preferredSize.getWidth(),
		     (int) preferredSize.getHeight());

	// Draw b and falling
	for (int row = 0; row < b.getHeight(); row++) {
	    for (int col = 0; col < b.getWidth(); col++) {
		SquareType squareType = b.getSquareTypeAt(row, col);
		g2d.setColor(colors.get(squareType));
		g2d.fillRect(getPixelPos(col), getPixelPos(row), SQUARE_SIZE, SQUARE_SIZE);
	    }
	}

    }

    public static int getPixelPos(final int boardPos) {
        return boardPos * (SQUARE_SIZE + SQUARE_BORDER_SIZE) + SQUARE_BORDER_SIZE;
    }

    @Override public void boardChanged() {
	setBorderColor();
        repaint();
    }

    private void setBorderColor() {
        switch (b.getFallHandler().getDescription()) {
	    case "No powerup":
	        borderColor = new Color(187, 191, 192);
	        break;
	    case "Fallthrough!":
	        borderColor = new Color(102, 176, 40);
	        break;
	    case "Heavy!":
		borderColor = new Color(124, 10, 118);
		break;
	    default:
	        throw new IllegalArgumentException("Invalid fall handler!");

	}
    }
}
