package View;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import Model.Utility;

/**
 * The BasePanel class is an abstract class that extends JPanel,
 * providing a base for game panels with a predefined background color
 * and preferred size based on the game's dimensions.
 */
public abstract class BasePanel extends JPanel {

    private static final long serialVersionUID = -5825286909506443965L;

    /** The size of a tile in the game. */
    final int tileSize = Utility.TILE_SIZE;

    /** The width of the game screen including padding. */
    final int screenWidth = Utility.WIDTH + 100;

    /** The height of the game screen including padding. */
    final int screenHeight = Utility.HEIGHT + 100;

    /**
     * Constructs a BasePanel and sets the background color to black
     * and the preferred size based on the screen dimensions.
     */
    public BasePanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    /**
     * Returns the size of a tile.
     *
     * @return the size of a tile
     */
    public int getTileSize() {
        return tileSize;
    }
}
