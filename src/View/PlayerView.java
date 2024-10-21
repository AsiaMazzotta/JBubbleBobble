package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import Model.MovableObject.Direction;
import Model.Observer;
import Model.Player;
import javax.swing.Timer;

/**
 * Represents the visual representation of a player in the game.
 */
public class PlayerView extends JLabel implements Observer {

    private static final long serialVersionUID = -574423983970792683L;

    private Image[] playerMovingRight;
    private Image[] playerMovingLeft;
    private Image[] playerIdleLeft;
    private Image[] playerIdleRight;
    private Image[] animation;
    private int currentImageIndex;
    private boolean invincible;

    private Timer animationTimer;

    /**
     * Constructs a new PlayerView for the specified player.
     *
     * @param p The player associated with this view.
     */
    public PlayerView(Player p) {
        initializeImages();
        this.setBounds((int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight());
        animation = playerIdleRight;

        // Setup animation timer
        animationTimer = new Timer(100, e -> {
            // Update the image index
            currentImageIndex = (currentImageIndex + 1) % playerIdleRight.length;
            // Redraw the component
            repaint();
        });
        animationTimer.start();
        invincible = false;
    }

    /**
     * Initializes the images for player animations.
     */
    private void initializeImages() {
        playerIdleLeft = new Image[]{
            GameImage.BOB_LEFT_ONE.getImage(),
            GameImage.BOB_LEFT_TWO.getImage()
        };

        playerIdleRight = new Image[]{
            GameImage.BOB_RIGHT_ONE.getImage(),
            GameImage.BOB_RIGHT_TWO.getImage()
        };

        playerMovingRight = new Image[]{
            GameImage.BOB_MOVING_R1.getImage(),
            GameImage.BOB_MOVING_R2.getImage()
        };

        playerMovingLeft = new Image[]{
            GameImage.BOB_MOVING_L1.getImage(),
            GameImage.BOB_MOVING_L2.getImage()
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animation[currentImageIndex], 0, 0, getWidth(), getHeight(), this);
        if (invincible) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Updates the view based on changes in the player state.
     *
     * @param obj The object containing player information.
     */
    @Override
    public void update(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            this.setBounds((int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight());

            // Update the image based on the player's state
            if (p.isMoving()) {
                if (p.getDirection() == Direction.RIGHT) {
                    animation = playerMovingRight;
                } else {
                    animation = playerMovingLeft;
                }
            } else {
                if (p.getDirection() == Direction.RIGHT) {
                    animation = playerIdleRight;
                } else {
                    animation = playerIdleLeft;
                }
            }

            invincible = p.isInvincible();
            // Redraw the component
            repaint();
        }
    }
}
