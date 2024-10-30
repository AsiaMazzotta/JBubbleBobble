package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import Controller.GameController.GameState;
import Model.User;

/**
 * KeyHandler handles keyboard input for various game states. It listens for key events and interacts
 * with the GameController to manage the game flow, such as moving the player, navigating menus,
 * and handling user registration and login.
 */
public class KeyHandler implements KeyListener {

    // Singleton instance of KeyHandler
    private static KeyHandler keyHandler;
    private GameController gc;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private KeyHandler() {
    }

    /**
     * Sets the GameController instance for this KeyHandler.
     *
     * @param gc The GameController instance.
     */
    public void setGameController(GameController gc) {
        this.gc = gc;
    }

    /**
     * Returns the singleton instance of KeyHandler.
     *
     * @return The single instance of KeyHandler.
     */
    public static KeyHandler getInstance() {
        if (keyHandler == null) keyHandler = new KeyHandler();
        return keyHandler;
    }

    /**
     * Handles key typed events. Appends characters to the nickname field during the registration process.
     *
     * @param e The KeyEvent triggered when a key is typed.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (gc != null && gc.getState() == GameState.REGISTER_STATE) {
            JTextField nicknameField = gc.getRegisterPanel().getNicknameField();
            char keyChar = e.getKeyChar();
            // Append character to the JTextField unless it's a backspace or control character
            if (!Character.isISOControl(keyChar)) {
                nicknameField.setText(nicknameField.getText() + keyChar);
            }
        }
    }

    /**
     * Handles key pressed events, which control game actions and navigation across different game states.
     *
     * @param e The KeyEvent triggered when a key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gc != null) {
            switch (gc.getState()) {
                case GAME_OVER:
                    if (code == KeyEvent.VK_ENTER) gc.changePanel(GameState.RANK);
                    break;
                case LOGIN_STATE:
                    handleLoginState(e);
                    break;
                case MENU_STATE:
                    handleMenuState(e);
                    break;
                case PAUSE:
                    if (code == KeyEvent.VK_P) gc.changePanel(GameState.RUNNING);
                    break;
                case RANK:
                    if (code == KeyEvent.VK_ENTER) gc.changePanel(GameState.START_STATE);
                    break;
                case REGISTER_STATE:
                    handleRegisterState(e);
                    break;
                case RUNNING:
                    handleRunningState(e);
                    break;
                case START_STATE:
                    if (code == KeyEvent.VK_ENTER) gc.changePanel(GameState.MENU_STATE);
                    break;
                case VICTORY:
                    if (code == KeyEvent.VK_ENTER) gc.changePanel(GameState.RANK);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles key released events for movement control when the game is in the RUNNING state.
     *
     * @param e The KeyEvent triggered when a key is released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (gc.getState() == GameState.RUNNING) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                gc.movePlayerLeft(false);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                gc.movePlayerRight(false);
            }
        }
    }

    /**
     * Handles key events when the game is in the LOGIN_STATE.
     *
     * @param e The KeyEvent triggered by the user.
     */
    private void handleLoginState(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_DOWN) {
            gc.getLoginPanel().moveSelectionDown();
        } else if (code == KeyEvent.VK_UP) {
            gc.getLoginPanel().moveSelectionUp();
        } else if (code == KeyEvent.VK_N) {
            gc.changePanel(GameState.MENU_STATE);
        } else if (code == KeyEvent.VK_ENTER) {
            User u = gc.getLoginPanel().getSelectedUser();
            if (u != null) {
                gc.setCurrentUser(u);
                gc.startGame();
                gc.changePanel(GameState.RUNNING);
            }
        }
    }

    /**
     * Handles key events when the game is in the MENU_STATE.
     *
     * @param e The KeyEvent triggered by the user.
     */
    private void handleMenuState(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            gc.getMenuPanel().moveSelectionUp();
        } else if (code == KeyEvent.VK_DOWN) {
            gc.getMenuPanel().moveSelectionDown();
        } else if (code == KeyEvent.VK_ENTER) {
            int opt = gc.getMenuPanel().getSelectedOption();
            switch (opt) {
                case 0 -> gc.changePanel(GameState.REGISTER_STATE);
                case 1 -> gc.changePanel(GameState.LOGIN_STATE);
                case 2 -> gc.exitGame();
            }
        } else if (code == KeyEvent.VK_N) {
            gc.changePanel(GameState.START_STATE);
        }
    }

    /**
     * Handles key events when the game is in the REGISTER_STATE, including text input and navigation.
     *
     * @param e The KeyEvent triggered by the user.
     */
    private void handleRegisterState(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            gc.getRegisterPanel().moveSelectionUp();
        } else if (code == KeyEvent.VK_LEFT) {
            gc.getRegisterPanel().moveSelectionDown();
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            handleBackspace();
        } else if (code == KeyEvent.VK_DELETE) {
            handleDelete();
        } else if (code == KeyEvent.VK_ENTER) {
            gc.addUser(gc.getRegisterPanel().getNickname(), gc.getRegisterPanel().getCurrentImagePath());
            gc.startGame();
            gc.changePanel(GameState.RUNNING);
        } else if (code == KeyEvent.VK_N) {
            gc.changePanel(GameState.MENU_STATE);
        }
    }

    /**
     * Handles key events when the game is in the RUNNING state, including movement and actions.
     *
     * @param e The KeyEvent triggered by the user.
     */
    private void handleRunningState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            gc.changePanel(GameState.PAUSE);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gc.playerJump();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gc.movePlayerLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gc.movePlayerRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            gc.playerBubble();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            gc.changeLevel();
        }
    }

    /**
     * Handles backspace key press during nickname input in the registration process.
     */
    private void handleBackspace() {
        JTextField nicknameField = gc.getRegisterPanel().getNicknameField();
        Document doc = nicknameField.getDocument();
        int caretPosition = nicknameField.getCaretPosition();
        if (caretPosition > 0) {
            try {
                doc.remove(caretPosition - 1, 1);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles delete key press during nickname input in the registration process.
     */
    private void handleDelete() {
        JTextField nicknameField = gc.getRegisterPanel().getNicknameField();
        Document doc = nicknameField.getDocument();
        int caretPosition = nicknameField.getCaretPosition();
        if (caretPosition < doc.getLength()) {
            try {
                doc.remove(caretPosition, 1);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
