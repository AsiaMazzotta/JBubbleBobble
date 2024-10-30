package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 * VictoryPanel is displayed when the player wins the game, showing a victory message.
 */
public class VictoryPanel extends BasePanel {

    private static final long serialVersionUID = 8303430882316678386L;

    /**
     * Constructs a VictoryPanel with a message indicating the player has won.
     */
    public VictoryPanel() {
        setName("VictoryPanel");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel j = new JLabel("YOU WON!!");
        j.setFont(GameFont.CUSTOM_FONT);
        j.setForeground(Color.WHITE);

        add(j, gbc);
    }
}
