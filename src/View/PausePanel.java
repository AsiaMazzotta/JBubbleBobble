package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;

/**
 * Represents a pause panel that displays a pause message and instructions for resuming the game.
 */
public class PausePanel extends BasePanel {

    private static final long serialVersionUID = -6580138389804004827L;

    /**
     * Constructs a new PausePanel.
     */
    public PausePanel() {
        setName("PausePanel");
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel label1 = new JLabel("", JLabel.CENTER);
        JLabel label2 = new JLabel("", JLabel.CENTER);
        label1.setFont(GameFont.CUSTOM_FONT);
        label1.setText("GIOCO IN PAUSA!");
        label1.setForeground(Color.WHITE);
        add(label1, gbc);
        gbc.gridy = 1;
        label2.setFont(GameFont.CUSTOM_FONT);
        label2.setText("Premi P per riprendere");
        label2.setForeground(Color.WHITE);
        add(label2, gbc);
    }
}
