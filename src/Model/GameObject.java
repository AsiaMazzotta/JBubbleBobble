package Model;

import java.awt.*;
import java.util.Objects;

/**
 * The GameObject class represents a generic game object in a 2D space with
 * position, size, visibility, and destruction status. It also manages a hitbox 
 * for collision detection.
 */
public abstract class GameObject {

    /** The x-coordinate of the object. */
    protected double x;
    
    /** The y-coordinate of the object. */
    protected double y;
    
    /** The width of the object. */
    private double width;
    
    /** The height of the object. */
    private double height;
    
    /** The hitbox used for collision detection. */
    protected Rectangle hitbox;
    
    /** Visibility state of the object. */
    private boolean isVisible;
    
    /** Destruction state of the object. */
    private boolean toDestroy;

    /**
     * Constructs a GameObject with the specified position, width, and height.
     *
     * @param x the initial x-coordinate of the object
     * @param y the initial y-coordinate of the object
     * @param height the height of the object
     * @param width the width of the object
     */
    public GameObject(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        isVisible = true;
        toDestroy = false;
        initHitbox();
    }

    /**
     * Initializes the hitbox based on the current position and size.
     */
    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
    
    /**
     * Returns the x-coordinate of the object.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the object.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the object.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the object.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Checks if the object is marked for destruction.
     *
     * @return true if the object is to be destroyed, false otherwise
     */
    public boolean isToDestroy() {
        return toDestroy;
    }

    /**
     * Marks the object for destruction.
     */
    public void destroy() {
        toDestroy = true;
    }

    /**
     * Returns the visibility state of the object.
     *
     * @return true if the object is visible, false otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the visibility state of the object.
     *
     * @param visible true to make the object visible, false to hide it
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Updates the hitbox position based on the current x and y coordinates.
     */
    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    /**
     * Returns the hitbox of the object.
     *
     * @return the hitbox as a Rectangle
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Returns the width of the object.
     *
     * @return the width of the object
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the object.
     *
     * @return the height of the object
     */
    public double getHeight() {
        return height;
    }

    /**
     * Compares this GameObject to another object for equality.
     *
     * @param o the other object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return Objects.equals(hitbox, that.hitbox);
    }

    /**
     * Returns a hash code for the GameObject based on its hitbox.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(hitbox);
    }

    /**
     * Returns a string representation of the GameObject.
     *
     * @return a string describing the object with its class name and coordinates
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " : (" + getX() + " , " + getY() + ")";
    }
}
