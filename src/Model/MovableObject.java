package Model;

import java.util.List;

/**
 * The MovableObject class represents an object in the game that can move 
 * and is affected by gravity. It extends the GameObject class 
 * and adds properties like speed, gravity, and movement direction.
 */
public abstract class MovableObject extends GameObject implements Collidable{

    /**
     * Enum representing the direction of movement (left or right).
     */
    public enum Direction {
        LEFT, RIGHT;
    }

    /** Normal movement speed of the object. */
    protected final double NORMAL_SPEED = 2.0;
    
    /** The gravity applied to the object. */
    protected double gravity;
    
    /** The vertical speed of the object, affected by gravity. */
    protected double verticalSpeed;
    
    /** The maximum speed at which the object can fall due to gravity. */
    protected double terminalVelocity;
    
    /** The current horizontal speed of the object. */
    protected double speed;
    
    /** The direction in which the object is moving. */
    protected Direction direction;
    
    /** Boolean flag indicating if the object is going to move to the right. */
    protected boolean right;
    
    /** Boolean flag indicating if the object is going to move to the left. */
    protected boolean left;
    /** Boolean flag indicating if the object is currently moving. */
    protected boolean moving;

    /**
     * Constructs a MovableObject with the specified position and size, initializing movement-related variables.
     *
     * @param x the x-coordinate of the object
     * @param y the y-coordinate of the object
     * @param height the height of the object
     * @param width the width of the object
     */
    public MovableObject(double x, double y, int height, int width) {
        super(x, y, height, width);
        speed = NORMAL_SPEED;
        gravity = 1.5 * NORMAL_SPEED;
        verticalSpeed = 0;
        terminalVelocity = 2.0 * NORMAL_SPEED;
        right = false;
        left = false;
        moving = false;
        direction = Direction.RIGHT;
    }

    /**
     * Gets the current movement direction of the object.
     *
     * @return the movement direction (left or right)
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the movement direction of the object.
     *
     * @param direction the new movement direction (left or right)
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Sets the speed of the object.
     *
     * @param speed the new speed of the object
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the current speed of the object.
     *
     * @return the current speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the object to move to the right.
     *
     * @param bool true if the object should move to the right, false otherwise
     */
    public void setRight(boolean bool) {
        this.right = bool;
    }

    /**
     * Sets the object to move to the left.
     *
     * @param bool true if the object should move to the left, false otherwise
     */
    public void setLeft(boolean bool) {
        this.left = bool;
    }

    /**
     * Checks if the object is currently moving.
     *
     * @return true if the object is moving, false otherwise
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Updates the position of the object based on its current speed and direction,
     * considering possible collisions with other objects.
     *
     * @param others a list of other GameObjects for collision detection
     */
    public abstract void updatePosition(List<GameObject> others);

    /**
     * Applies gravity to the object, adjusting its vertical speed and ensuring
     * it doesn't exceed terminal velocity. Also checks for collisions during the fall.
     *
     * @param others a list of other GameObjects for collision detection
     */
    public abstract void applyGravity(List<GameObject> others);

}
