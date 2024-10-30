package Model;

import java.awt.Rectangle;
import java.util.List;

/**
 * The Boulder class represents a movable object that behaves as a projectile
 * within the game. It is a subclass of {@link MovableObject} and moves 
 * horizontally until it either collides with a {@link Wall} or a {@link Player}.
 * Boulders have no gravity effect and are destroyed upon collision with 
 * specific objects.
 */
public class Boulder extends MovableObject {

    /**
     * Constructs a Boulder object with the specified coordinates and direction.
     *
     * @param x the X-coordinate of the boulder
     * @param y the Y-coordinate of the boulder
     * @param direction the direction in which the boulder moves
     */
    public Boulder(double x, double y, Direction direction) {
        super(x, y, 20, 20);
        this.direction = direction;
        setSpeed(NORMAL_SPEED * 1.5);
    }

    /**
     * Updates the position of the boulder by moving it either to the left 
     * or right based on its current direction. It also checks for collisions 
     * with other game objects, and destroys itself if it collides with a 
     * {@link Wall}.
     *
     * @param others a list of other game objects to check for collisions
     */
    @Override
    public void updatePosition(List<GameObject> others) {
        if (getDirection() == Direction.LEFT) {
            right = false;
            left = true;
        } else {
            left = false;
            right = true;
        }

        double velocity = 0;
        if (right) velocity = getSpeed() * 1;
        if (left) velocity = getSpeed() * -1;

        // Create a new hitbox for collision detection
        Rectangle newHitbox = new Rectangle(
            (int) (getX() + velocity), 
            (int) getY(), 
            (int) getWidth(), 
            (int) getHeight()
        );
        CollisionDetection detection = new CollisionDetection(false, null, null);

        // Check for collisions with other game objects
        for (GameObject obj : others) {
            if (obj.equals(this)) {
                continue; // Skip collision with itself
            }
            detection = CollisionDetection.collisionDetection(this, newHitbox, obj);
            if (detection.isCollisionDetected()) {
                if (obj instanceof Wall) {
                    destroy();
                }
            }
        }

        x = newHitbox.getX();
        y = newHitbox.getY();
        updateHitbox();
    }

    /**
     * Boulders do not apply gravity, so this method is overridden to do nothing.
     *
     * @param others a list of other game objects (ignored in this method)
     */
    @Override
    public void applyGravity(List<GameObject> others) {
        // No gravity effect for boulders
    }

    /**
     * Handles collision with another object. If the boulder collides with a
     * {@link Player}, it damages the player and destroys itself.
     *
     * @param obj the object this boulder has collided with
     */
    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            p.isHit();
            destroy();
        }
    }
}
