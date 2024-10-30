package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * The MenuPanel class represents the main menu of the game,
 * allowing players to navigate between options such as "NEW GAME",
 * "CONTINUE", and "EXIT". It extends BasePanel and utilizes
 * a GridBagLayout for positioning menu items.
 */
public class MenuPanel extends BasePanel {

    private static final long serialVersionUID = -5220126280791498096L;

    /** The index of the currently selected menu option. */
    private int selectedOption;

    /** The array of menu option labels. */
    private JLabel[] menuOptions;

    /** The icon displayed next to the selected menu option. */
    private ImageIcon selectionIcon;

    /**
     * Constructs a MenuPanel and initializes the layout and menu options.
     */
    public MenuPanel() {
        selectedOption = 0;
        setName("Menu");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        menuOptions = new JLabel[3];

        gbc.gridy = 0;
        menuOptions[0] = new JLabel("NEW GAME");
        addMenuOption(menuOptions[0], gbc);

        gbc.gridy = 1;
        menuOptions[1] = new JLabel("CONTINUE");
        addMenuOption(menuOptions[1], gbc);

        gbc.gridy = 2;
        menuOptions[2] = new JLabel("EXIT");
        addMenuOption(menuOptions[2], gbc);

        selectionIcon = new ImageIcon(GameImage.SELECTION_ICON.getImage());

        updateSelection();
        setFocusable(true);
    }

    /**
     * Returns the index of the currently selected menu option.
     *
     * @return the index of the selected option
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    /**
     * Adds a menu option label to the panel with the specified layout constraints.
     *
     * @param label the JLabel representing the menu option
     * @param gbc the GridBagConstraints used for positioning
     */
    private void addMenuOption(JLabel label, GridBagConstraints gbc) {
        label.setFont(GameFont.CUSTOM_FONT);
        label.setForeground(Color.WHITE);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label, gbc);
    }

    /**
     * Moves the selection up in the menu, wrapping around if necessary.
     */
    public void moveSelectionUp() {
        selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        updateSelection();
    }

    /**
     * Moves the selection down in the menu, wrapping around if necessary.
     */
    public void moveSelectionDown() {
        selectedOption = (selectedOption + 1) % menuOptions.length;
        updateSelection();
    }

    /**
     * Updates the menu options to reflect the current selection.
     * The selected option will display the selection icon.
     */
    private void updateSelection() {
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                menuOptions[i].setIcon(selectionIcon);
            } else {
                menuOptions[i].setIcon(null); // Default item color
            }
        }
    }
}
