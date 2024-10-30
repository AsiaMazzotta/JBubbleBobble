package Model;

import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Player} class represents a player entity in the game.
 * It extends the {@code Entity} class and implements functionalities
 * such as managing player lives, handling invincibility, and notifying observers.
 */
public class Player extends Entity {

    private int lives; // Number of lives the player has
    private List<PlayerObserver> pObv; // List of player observers
    private boolean isInvincible; // Flag to prevent multiple hits
    private int invincibilityTimer; // Timer for invincibility in frames

    private final int INVINCIBILITY_DURATION = 90; // Duration of invincibility in frames (3 seconds at 30 FPS)

    /**
     * Constructs a new Player object at a specified position with a default size.
     */
    public Player() {
        super(20, Utility.HEIGHT - 40, 24, 24);
        canAttack = true;
        lives = 2; // Initial lives
        pObv = new ArrayList<PlayerObserver>();
        isInvincible = false;
        invincibilityTimer = 0; // Initial state is not invincible
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        notifyObserver(); // Notify observers when the position changes
    }
    
    @Override
    public void setY(double y) {
        super.setY(y);
        notifyObserver(); // Notify observers when the position changes
    }
    
    /**
     * Returns the number of lives the player has.
     *
     * @return the number of lives
     */
    public int getLives() {
        return lives;
    }
    
    /**
     * Increases the number of lives
     */
    public void increaseLives() {
    	lives++;
    	notifyLivesChange();
    }
    
    /**
     * Adds a player observer to the list.
     *
     * @param obv the observer to add
     */
    public void addPlayerObserver(PlayerObserver obv) {
        pObv.add(obv);
    }

    /**
     * Notifies all observers that the player's lives have decreased.
     */
    private void notifyLivesChange() {
        for (PlayerObserver observer : pObv) {
            observer.onLivesChange();
        }
    }

    /**
     * Checks if the player is dead.
     *
     * @return true if the player has no lives left, otherwise false
     */
    public boolean isDead() {
        return lives <= 0;
    }

    /**
     * Handles the logic when the player is hit by an enemy.
     * Decreases lives and starts the invincibility timer.
     */
    public void isHit() {
        if (!isInvincible) {
            isInvincible = true;
            lives--;
            notifyLivesChange();
            invincibilityTimer = INVINCIBILITY_DURATION; // Start the invincibility timer
        }
    }

    public boolean isInvincible() {return isInvincible;}
    
    /**
     * Updates the invincibility status based on the timer.
     */
    public void updateInvincibility() {
        if (isInvincible) {
            invincibilityTimer--;
            if (invincibilityTimer <= 0) {
                isInvincible = false; // Reset invincibility after the timer expires
            }
        }
    }

    @Override
    public void collision(Collidable obj) {
    		obj.collision(this);
    }

    @Override
    public void updatePosition(List<GameObject> others) {
        super.updatePosition(others);
        updateInvincibility(); // Update the invincibility timer here
    }
}
