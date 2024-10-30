package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import Model.User;

/**
 * ScrollLogin is a JScrollPane that displays a list of users,
 * allowing the selection of a user to log in to a game.
 */
public class ScrollLogin extends JScrollPane {

    private static final long serialVersionUID = -6809673740167084627L;
    private List<User> users;  // List of users to be displayed
    private int selectedUser;   // Index of the currently selected user
    private JPanel[] userLabels; // Array to hold user panels

    /**
     * Constructs a ScrollLogin with the specified list of users.
     *
     * @param users the list of users to be displayed in the login panel.
     */
    public ScrollLogin(List<User> users) {
        this.users = users;
        selectedUser = 0;
        userLabels = new JPanel[users.size()];
        createUserPanels(); // Initialize the user panels
    }

    /**
     * Creates and initializes the user panels for each user in the list.
     * If the users list is empty, a message indicating no saved player is displayed.
     */
    private void createUserPanels() {
        // Check if the users list is empty
        if (users == null || users.isEmpty()) {
            JLabel noUsersLabel = new JLabel("No saved player.");
            noUsersLabel.setFont(GameFont.CUSTOM_FONT);
            noUsersLabel.setForeground(Color.WHITE);
            setViewportView(noUsersLabel);
            return; // Exit early if no users to display
        }

        setName("Login");

        JLabel title = new JLabel("SELECT A GAME");
        title.setFont(GameFont.CUSTOM_FONT);
        title.setForeground(Color.WHITE);

        BasePanel parentPanel = new BasePanel() {
            private static final long serialVersionUID = 7754418728120650760L;
        };

        // Set the preferred width to prevent horizontal scrolling
        int scrollPaneWidth = 400; // Example width, adjust accordingly
        int totalHeight = (users.size() + 1) * (50 + 10); // 50 for element height + 10 for insets
        parentPanel.setPreferredSize(new Dimension(scrollPaneWidth, totalHeight));

        parentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        parentPanel.add(title, gbc);
        gbc.gridy++;

        userLabels = new JPanel[users.size()]; // Re-initialize userLabels array

        // Loop to add users
        int i = 0;
        for (User u : users) {
            JPanel p = new JPanel();

            JLabel nickname = new JLabel(u.getNickname());
            JLabel image = new JLabel(new ImageIcon(u.getAvatar()));
            JLabel level = new JLabel("LV " + (int) u.getLevel());

            p.add(image);
            p.add(nickname);
            p.add(level);

            if (i == 0) {
                p.setBorder(new LineBorder(Color.GRAY, 4));
            }

            userLabels[i++] = p;
            parentPanel.add(p, gbc);
            gbc.gridy++;
        }

        // Configure JScrollPane
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setFocusable(true);
        requestFocusInWindow();

        setViewportView(parentPanel);
    }

    /**
     * Updates the list of users and refreshes the user panels.
     *
     * @param users the new list of users to be displayed.
     */
    public void setUsers(List<User> users) {
        this.users = users;
        selectedUser = 0; // Reset selected user index
        createUserPanels(); // Refresh the user panels
    }

    /**
     * Gets the currently selected user.
     *
     * @return the selected User object.
     */
    public User getSelectedUser() {
        return users.get(selectedUser);
    }

    /**
     * Moves the selection up to the previous user in the list.
     */
    public void moveSelectionUp() {
        if (selectedUser > 0) {
            selectedUser--;
            updateSelection();
        }
    }

    /**
     * Moves the selection down to the next user in the list.
     */
    public void moveSelectionDown() {
        if (selectedUser < userLabels.length - 1) {
            selectedUser++;
            updateSelection();
        }
    }

    /**
     * Updates the selection by changing the border of the user panels
     * and ensuring the selected panel is visible in the viewport.
     */
    private void updateSelection() {
        // Update borders of user panels
        for (int i = 0; i < userLabels.length; i++) {
            if (i == selectedUser) {
                userLabels[i].setBorder(new LineBorder(Color.GRAY, 4));
            } else {
                userLabels[i].setBorder(null);
            }
        }

        // Ensure the layout is updated
        this.revalidate();
        this.repaint();

        // Ensure the selected panel is visible
        JPanel viewportView = (JPanel) this.getViewport().getView();
        if (viewportView != null) {
            // Calculate the rectangle of the selected panel relative to the parentPanel
            java.awt.Rectangle rect = userLabels[selectedUser].getBounds();
            // Convert the rectangle to be relative to the viewport
            java.awt.Rectangle viewRect = SwingUtilities.convertRectangle(userLabels[selectedUser].getParent(), rect, this.getViewport());

            // Scroll to ensure the selected panel is visible in the viewport
            this.getViewport().scrollRectToVisible(viewRect);
        }
    }
}
