package View;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The MainFrame class represents the main window of the game.
 * It extends JFrame and utilizes a CardLayout to manage different game panels.
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = -1364196269001069655L;
    private JPanel content;
    private CardLayout cl;

    /**
     * Constructs a MainFrame and initializes the window properties.
     * It sets the title, close operation, and layout for managing panels.
     */
    public MainFrame() {
        setTitle("JBubbleBobble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cl = new CardLayout();
        content = new JPanel(cl);

        add(content);
    }

    /**
     * Adds a container (panel) to the main frame with a specified name.
     *
     * @param c the container (JPanel) to add
     * @param name the name to associate with this panel
     */
    public void addContainer(Container c, String name) {
        content.add(c, name);
    }

    /**
     * Shows the specified panel by its name in the main frame.
     *
     * @param name the name of the panel to show
     */
    public void show(String name) {
        cl.show(content, name);

        // Get the component that is currently visible
        Component shownComponent = null;
        for (Component comp : content.getComponents()) {
            if (comp.isVisible()) {
                shownComponent = comp;
                break;
            }
        }

        if (shownComponent != null) {
            shownComponent.setFocusable(true);
            shownComponent.requestFocusInWindow();
        }
    }
}
