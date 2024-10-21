package View;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
 * The GameFont class is responsible for loading a custom font from
 * a specified file path and registering it with the local graphics
 * environment. It provides a fallback font in case of failure.
 */
public class GameFont {
    /** The custom font used throughout the game. */
    public static final Font CUSTOM_FONT;

    static {
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                new File("./res/Graphics/Nintendo-NES-Font.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("Serif", Font.PLAIN, 8); // Fallback font
        }
        CUSTOM_FONT = customFont;
    }
}
