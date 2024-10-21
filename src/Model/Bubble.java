package Model;

import java.awt.*;
import java.util.List;

/**
 * The {@code Bubble} class represents a movable object that can catch enemies 
 * and interact with players in the game. 
 */
public class Bubble extends MovableObject {

    private final double MAX_OFFSET = 4 * Utility.TILE_SIZE; // Maximum horizontal movement offset
    private boolean canCatch; // Indicates if the bubble can catch enemies
    private boolean UP; // Indicates if the bubble is moving upwards
    private double offset; // Tracks the current horizontal offset
    private Enemy caughtEnemy; // The enemy currently caught by the bubble

    private boolean reachedTopMiddle = false; // Indicates if the bubble has reached the top middle position
    private long reachedTime = 0; // The time when the bubble reached the top middle
    private final long FIVE_SECONDS = 5000; // Duration after which the bubble destroys itself

    /**
     * Constructs a new {@code Bubble} instance at the specified position and direction.
     *
     * @param x         the x-coordinate of the bubble's position
     * @param y         the y-coordinate of the bubble's position
     * @param direction the initial direction the bubble moves
     */
    public Bubble(double x, double y, Direction direction) {
        super(x, y, 20, 20); // Calls the constructor of the superclass (MovableObject)
        offset = 0;
        canCatch = true; // The bubble can initially catch enemies
        this.direction = direction; // Set the initial direction
    }

    @Override
    public void updatePosition(List<GameObject> others) {
        if (offset < MAX_OFFSET) {
            // Bubble moves horizontally until MAX_OFFSET is reached
            UP = false;
            if (getDirection() == Direction.RIGHT) {
                right = true;
                left = false;
            } else {
                left = true;
                right = false;
            }
        } else {
            canCatch = false; // The bubble can no longer catch enemies
            // After reaching MAX_OFFSET, bubble changes behavior
            if (this.y <= 4 * Utility.TILE_SIZE) {
                // Move horizontally until reaching y > 4 * Utility.TILE_SIZE
                UP = false;
                if (this.x < Utility.WIDTH / 2) {
                    left = false;
                    right = true;
                } else if (this.x > Utility.WIDTH / 2) {
                    left = true;
                    right = false;
                } else {
                    left = false;
                    right = false;
                    if (!reachedTopMiddle) {
                        reachedTopMiddle = true; // Mark that the bubble has reached the top middle
                        reachedTime = System.currentTimeMillis(); // Record the time of reaching
                    }
                }
            } else {
                // Move vertically upwards
                UP = true;
                left = false;
                right = false;
            }
        }

        // Update position based on movement direction
        offset += speed;
        double velocity = 0;
        if (right) velocity = getSpeed() * 1;
        if (left) velocity = getSpeed() * -1;

        // Create a new hitbox for collision detection
        Rectangle newHitbox = new Rectangle((int) (getX() + velocity), (int) (getY() - ((UP) ? getSpeed() : 0)), (int) getWidth(), (int) getHeight());
        CollisionDetection detection = new CollisionDetection(false, null, null);

        // Check for collisions with other game objects
        for (GameObject obj : others) {
            if (obj.equals(this) || obj instanceof Boulder) {
                continue; // Skip collision with itself
            }
            detection = CollisionDetection.collisionDetection(this, newHitbox, obj);
            if (detection.isCollisionDetected()) {
                // Handle wall or bubble collision logic
                if (detection.getDirection() == CollisionDetection.CollisionDirection.LEFT || detection.getDirection() == CollisionDetection.CollisionDirection.RIGHT) {
                    switch (detection.getDirection()) {
                        case LEFT -> newHitbox.setLocation((int) (obj.getX() + obj.getWidth()), (int) newHitbox.getY());
                        case RIGHT -> newHitbox.setLocation((int) (obj.getX() - newHitbox.getWidth()), (int) newHitbox.getY());
                        default -> throw new IllegalArgumentException("Unexpected value: " + detection.getDirection());
                    }
                }
            }
        }

        // Update the bubble's position
        x = newHitbox.getX();
        y = newHitbox.getY();

        if (caughtEnemy != null) {
            caughtEnemy.setX(getX());
            caughtEnemy.setY(getY());
        }

        updateHitbox(); // Update the bubble's hitbox

        // If 5 seconds have passed after reaching the top middle
        if (reachedTopMiddle && System.currentTimeMillis() - reachedTime >= FIVE_SECONDS) {
            if (caughtEnemy != null) {
                caughtEnemy.free(); // Free the caught enemy
            }
            destroy(); // Destroy the bubble
        }
    }
    
    /**
     * Gets the bubble's caught enemy
     * @return Enemy caughtEnemy
     */
    public Enemy getCaughtEnemy() {
		return caughtEnemy;
	}

    /**
     * Handles collisions between this object and another GameObject.
     *
     * @param obj the other GameObject with which this object collides
     */
    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Enemy) {
            Enemy e = (Enemy) obj;
            if (canCatch) {
                e.caught(); // Catch the enemy
                caughtEnemy = e; // Set the caught enemy
                canCatch = false; // Disable further catches
            }
        } else if (obj instanceof Player) {
        	if (caughtEnemy != null) {
                caughtEnemy.destroy(); // Destroy the caught enemy
                destroy(); // Destroy the bubble
            }       	
        }
    }

    @Override
    public void applyGravity(List<GameObject> others) {
        // NO GRAVITY
    }
}
