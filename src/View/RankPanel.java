package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Model.User;

/**
 * Represents a ranking panel that displays a list of users and their scores.
 */
public class RankPanel extends JScrollPane {

    private static final long serialVersionUID = 5437408835806800954L;
    private List<User> users;
    private JPanel parentPanel;

    /**
     * Constructs a new RankPanel with the specified list of users.
     *
     * @param userList The list of users to display.
     */
    public RankPanel(List<User> userList) {
        setUsers(userList);
        createDisplay();
    }

    /**
     * Creates the display for the ranking panel.
     */
    private void createDisplay() {
        if (users == null || users.isEmpty()) {
            JLabel noUsersLabel = new JLabel("No users available");
            setViewportView(noUsersLabel);
            return;
        }

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getHighestScore(), u1.getHighestScore()); // Descending order
            }
        });

        setName("Rank");

        JLabel title = new JLabel("RANKING");
        title.setFont(GameFont.CUSTOM_FONT);
        title.setForeground(Color.WHITE);

        parentPanel = new BasePanel() {
            private static final long serialVersionUID = 7754418728120650760L;
        };

        int scrollPaneWidth = 400;
        int totalHeight = (users.size() + 1) * (50 + 10);
        parentPanel.setPreferredSize(new Dimension(scrollPaneWidth, totalHeight));
        parentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        parentPanel.add(title, gbc);
        gbc.gridy++;

        JLabel instructionLabel = new JLabel("Press ENTER to EXIT");
        instructionLabel.setFont(GameFont.CUSTOM_FONT);
        instructionLabel.setForeground(Color.WHITE);
        parentPanel.add(instructionLabel, gbc);
        gbc.gridy++;

        for (User u : users) {
            JPanel userPanel = new JPanel();

            JLabel nickname = new JLabel(u.getNickname());
            JLabel image = new JLabel(new ImageIcon(u.getAvatar()));
            JLabel level = new JLabel("LV " + (int) u.getLevel());
            JLabel score = new JLabel("Score: " + u.getHighestScore());

            userPanel.add(image);
            userPanel.add(nickname);
            userPanel.add(level);
            userPanel.add(score);

            parentPanel.add(userPanel, gbc);
            gbc.gridy++;
        }

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setFocusable(true);
        requestFocusInWindow();

        setViewportView(parentPanel);
    }

    /**
     * Sets the list of users and refreshes the display.
     *
     * @param users The list of users to set.
     */
    public void setUsers(List<User> users) {
        this.users = users;
        createDisplay();
    }
}
