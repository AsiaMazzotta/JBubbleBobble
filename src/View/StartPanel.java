package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StartPanel is the initial panel displayed at the start of the game,
 * featuring a title image and a "PRESS START" message.
 */
public class StartPanel extends BasePanel {

    private static final long serialVersionUID = -3651333001011074104L;

    /**
     * Constructs a StartPanel with a title image and start prompt.
     */
    public StartPanel() {
        setName("Start");
        setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.BLACK);

        ImageIcon gif = new ImageIcon(GameImage.TITLE_ICON.getImage());
        JLabel title = new JLabel(gif);
        JLabel text = new JLabel("PRESS START");
        text.setFont(GameFont.CUSTOM_FONT);
        text.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 50, 0); // Add some vertical space around the text
        container.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.anchor = GridBagConstraints.CENTER; // Horizontally center the text
        container.add(text, gbc);

        add(container, BorderLayout.CENTER);
    }
}
