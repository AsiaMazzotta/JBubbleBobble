package Model;

import java.awt.*;

/**
 * The CollisionDetection class is responsible for detecting collisions between game objects.
 * It determines whether a collision has occurred, identifies the object that was hit, and
 * specifies the direction of the collision.
 */
public class CollisionDetection {

    /**
     * Enum representing the direction of a collision.
     */
    public enum CollisionDirection {
        TOP, BOTTOM, RIGHT, LEFT;
    }

    /** Indicates if a collision was detected. */
    private boolean collisionDetected;

    /** The name of the object that was collided with. */
    private String objectDetected;

    /** The direction of the collision. */
    private CollisionDirection direction;

    /**
     * Constructs a CollisionDetection object with the given collision status, object detected, and direction.
     *
     * @param collisionDetected true if a collision was detected, false otherwise
     * @param objectDetected the name of the object that was collided with
     * @param direction the direction of the collision
     */
    public CollisionDetection(boolean collisionDetected, String objectDetected, CollisionDirection direction) {
        this.collisionDetected = collisionDetected;
        this.objectDetected = objectDetected;
        this.direction = direction;
    }

    /**
     * Returns whether a collision was detected.
     *
     * @return true if a collision was detected, false otherwise
     */
    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    /**
     * Returns the name of the object that was collided with.
     *
     * @return the name of the object that was collided with
     */
    public String getObjectDetected() {
        return objectDetected;
    }

    /**
     * Returns the direction of the collision.
     *
     * @return the direction of the collision
     */
    public CollisionDirection getDirection() {
        return direction;
    }

    /**
     * Detects whether a collision occurred between the movable object and another game object.
     * If a collision is detected, it returns a CollisionDetection object with the collision details.
     * The method also triggers the collision handling of the movable object.
     *
     * @param obj the movable object whose collision is being checked
     * @param newHitbox the hitbox of the movable object in its new position
     * @param other the other game object to check for a collision
     * @return a CollisionDetection object containing the collision details
     */
    public static CollisionDetection collisionDetection(Collidable obj, Rectangle newHitbox, GameObject other) {
        if (newHitbox.intersects(other.getHitbox())) {
            CollisionDirection dir = calculateDirection(newHitbox, other.getHitbox());
            if(other instanceof Collidable) obj.collision((Collidable)other);
            return new CollisionDetection(true, other.getClass().getSimpleName(), dir);
        }
        return new CollisionDetection(false, null, null);
    }

    /**
     * Calculates the direction of a collision based on the positions of two hitboxes.
     *
     * @param newHitbox the hitbox of the movable object in its new position
     * @param other the hitbox of the other game object
     * @return the direction of the collision (TOP, BOTTOM, RIGHT, LEFT)
     */
    private static CollisionDirection calculateDirection(Rectangle newHitbox, Rectangle other) {
        double centerX1 = newHitbox.getCenterX();
        double centerY1 = newHitbox.getCenterY();
        double centerX2 = other.getCenterX();
        double centerY2 = other.getCenterY();

        double deltaX = centerX2 - centerX1;
        double deltaY = centerY2 - centerY1;

        // Determines the collision direction based on the relative position of the hitboxes
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                return CollisionDirection.RIGHT;
            } else {
                return CollisionDirection.LEFT;
            }
        } else {
            if (deltaY > 0) {
                return CollisionDirection.BOTTOM;
            } else {
                return CollisionDirection.TOP;
            }
        }
    }
}
