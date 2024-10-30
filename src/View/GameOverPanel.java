package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 * The GameOverPanel class represents the panel displayed when the game is over.
 * It extends BasePanel and uses a GridBagLayout to center the "GAME OVER!" message
 * on the screen.
 */
public class GameOverPanel extends BasePanel {

    private static final long serialVersionUID = -8578088568260042632L;

    /**
     * Constructs a GameOverPanel and sets up the layout and components
     * to display the "GAME OVER!" message.
     */
    public GameOverPanel() {
        setName("GameOverPanel");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel j = new JLabel("GAME OVER!");
        j.setFont(GameFont.CUSTOM_FONT);
        j.setForeground(Color.WHITE);

        add(j, gbc);
    }
}
