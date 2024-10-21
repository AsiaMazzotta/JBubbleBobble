package Model;

import java.util.List;
import java.util.Random;

/**
 * The Enemy class represents a hostile entity in the game that can move, patrol, be caught, or freed. 
 * It extends the Entity class and adds behavior specific to enemies, such as reacting to player movements.
 */
public abstract class Enemy extends Entity {

    /** The direction of the enemy's movement along the x-axis. */
    protected int directionX;
    
    /** The direction of the enemy's movement along the y-axis. */
    protected int directionY;

    /** Enum representing the various states the enemy can be in. */
    public enum State {
        PATROL, CAUGHT, FREED, ATTACK
    }

    /** The current state of the enemy. */
    protected State state;

    /**
     * Constructs an Enemy with the specified position and size, and sets its initial state to PATROL.
     *
     * @param x the x-coordinate of the enemy
     * @param y the y-coordinate of the enemy
     * @param height the height of the enemy
     * @param width the width of the enemy
     */
    public Enemy(double x, double y, int height, int width) {
        super(x, y, height, width);
        patrol();
    }

    /**
     * Changes the enemy's state to CAUGHT, hides the enemy by setting its visibility to false.
     */
    public void caught() {
        state = State.CAUGHT;
        setVisible(false);
    }

    /**
     * Frees the enemy, changing its state to FREED, increasing its speed, and setting its visibility to true.
     */
    public void free() {
        state = State.FREED;
        speed = NORMAL_SPEED * 1.3;
        setVisible(true);
    }

    /**
     * Sets the enemy's state to PATROL and resets its speed to normal.
     */
    public void patrol() {
        state = State.PATROL;
        speed = NORMAL_SPEED - (NORMAL_SPEED*0.10);
    }

    /**
     * Gets the current state of the enemy.
     *
     * @return the current state of the enemy
     */
    public State getState() {
        return state;
    }

    /**
     * Moves the enemy based on its interaction with the player and other game objects.
     * If the enemy is on the same plane (y-coordinate) as the player, it moves towards the player.
     * Otherwise, it exhibits random movement behavior with the chance to jump.
     *
     * @param others a list of other GameObjects for collision detection
     * @param player the player the enemy interacts with
     */
    public void move(List<GameObject> others, Player player) {
        Random r = new Random();
        double n = r.nextDouble() * 100;  // Generate a random value between 0 and 100

        if ((player.getY() + 20) >= getY() && getY() >= player.getY()) {
            // Enemy is on the same y-plane as the player, so it moves toward the player
            double dx = player.getX() - getX();
            directionX = (int) Math.signum(dx);

            // Move based on direction
            if (directionX > 0) {
                right = true;
                left = false;
            } else if (directionX < 0) {
                left = true;
                right = false;
            }
        } else {
            // Check if the enemy is below the player within 4 tiles
            if (getY() > player.getY() + player.getHeight() && getY() <= player.getY() + 4 * Utility.TILE_SIZE) {
                if (n < 50) {
                    setJump();  // Random chance to jump if below the player
                }
            } else {
                if (n < 0.5) {
                    setJump();  // Small chance to jump randomly
                }
            }

            // Random movement logic
            if (right && n < 90) {
                // 90% chance to continue moving right
                left = false;
            } else if (left && n < 90) {
                // 90% chance to continue moving left
                right = false;
            } else if (!right && !left && n < 50) {
                // 50% chance to start moving right if idle
                right = true;
            } else if (!right && !left && n < 100) {
                // 50% chance to start moving left if idle
                left = true;
            } else {
                // No movement
                right = false;
                left = false;
            }
        }
        if(state==State.CAUGHT) {
        	right = false;
        	left = false;
        }
        updatePosition(others);
    }

    /**
     * Handles the collision event when the enemy collides with another GameObject.
     * If the collision is with the player, the player takes a hit if the enemy is not in the CAUGHT state.
     *
     * @param obj the GameObject that the enemy collides with
     */
    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Player) {
            if (state != State.CAUGHT) {
                Player p = (Player) obj;
                p.isHit();
            }
        }
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString()+"-"+state;
    }
}
