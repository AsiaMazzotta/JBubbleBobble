package Model;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Entity class represents a movable entity in the game that can jump, attack, 
 * and be observed by multiple observers. It extends the MovableObject class and 
 * implements the Observable interface.
 */
public abstract class Entity extends MovableObject implements Observable {

	protected boolean canAttack;
    /** Boolean flag for initiating a jump action. */
    protected boolean jump;
    
    /** Boolean flag for initiating an attack action. */
    protected boolean attack;
    
    /** Boolean flag indicating whether the entity is currently jumping. */
    protected boolean jumping;
    
    /** Boolean flag indicating whether the entity is currently falling. */
    protected boolean falling;
    
    /** Boolean flag indicating whether the entity is currently attacking. */
    protected boolean attacking;
    
    /** The speed of the entity when it jumps. */
    private double jumpSpeed;
    
    /** The time when the attack action started. */
    protected LocalTime attackStartTime;
    
    /** List of observers watching this entity. */
    private List<Observer> observers;

    /**
     * Constructs an Entity with the specified position and size, initializing its movement
     * and action-related variables.
     *
     * @param x the x-coordinate of the entity
     * @param y the y-coordinate of the entity
     * @param height the height of the entity
     * @param width the width of the entity
     */
    public Entity(double x, double y, int height, int width) {
        super(x, y, height, width);
        observers = new ArrayList<>();
        direction = Direction.RIGHT;
        jump = false;
        jumpSpeed = 1.9 * NORMAL_SPEED;
        falling = false;
        jumping = false;
        attack = false;
        attacking = false;
        attackStartTime = null;
        canAttack = false;
    }

    /**
     * Triggers the attack action for the entity. Sets the attack flag and records the start time.
     *
     * @return true if the attack was initiated successfully, false if attack was already true
     */
    public boolean attack() {
    	if(canAttack) {
    		if (!attack) {
                attack = true;
                attackStartTime = LocalTime.now();
                return true;
            }
    	}
        return false;
    }

    /**
     * Sets the entity to jump if it is not currently falling or jumping.
     */
    public void setJump() {
        if (!falling && !jumping && !jump) {
            jumping = true;
            jump = true;
        }
    }

    /**
     * Applies gravity to the entity and checks for collisions with other objects. 
     * If the entity hits the ground, it stops falling.
     *
     * @param others a list of other GameObjects for collision detection
     */
    @Override
    public void applyGravity(List<GameObject> others) {
        falling = true;
        verticalSpeed += gravity;
        if (verticalSpeed > terminalVelocity) {
            verticalSpeed = terminalVelocity;
        }

        Rectangle newHitbox = new Rectangle((int) x, (int) (y + verticalSpeed), (int) getWidth(), (int) getHeight());
        CollisionDetection detection = new CollisionDetection(false, null, null);
        for (GameObject obj : others) {
    		if(!(obj instanceof Entity || obj instanceof Wall)) continue;
            detection = CollisionDetection.collisionDetection(this, newHitbox, obj);
            if (detection.isCollisionDetected() && detection.getDirection() == CollisionDetection.CollisionDirection.BOTTOM) {
                if (verticalSpeed > 0) {
                    y = obj.getY() - getHeight();
                } else {
                    y = obj.getY() + obj.getHeight();
                }
                verticalSpeed = 0;
                jump = false;
                falling = false;
                break;
            }
        }
        if (!detection.isCollisionDetected()) {
            y += verticalSpeed;
        }

        if (this.y >= Utility.HEIGHT) {
            y = 0;
        }

        updateHitbox();
    }

    /**
     * Updates the entity's position and manages movement, gravity, and collision detection.
     *
     * @param others a list of other GameObjects for collision detection
     */
    @SuppressWarnings("incomplete-switch")
    public void updatePosition(List<GameObject> others) {
        if (attack) {
            attacking = true;
        }

        if (attackStartTime != null) {
            if (Duration.between(attackStartTime, LocalTime.now()).getSeconds() >= 1) {
                attack = false;
                attackStartTime = null;
            }
        }

        others.remove(this);
        moving = false;

        if (!jumping) {
            Rectangle newHitbox = new Rectangle(this.getHitbox());
            boolean movingRight = false;
            boolean movingLeft = false;

            if (right && !left) {
                newHitbox.setLocation((int) (this.x + speed), (int) this.y);
                direction = Direction.RIGHT;
                movingRight = true;
            } else if (!right && left) {
                newHitbox.setLocation((int) (this.x - speed), (int) this.y);
                direction = Direction.LEFT;
                movingLeft = true;
            }

            if (!newHitbox.equals(this.hitbox)) {
                for (GameObject obj : others) {
                	if(obj instanceof Boulder || obj instanceof Bubble) continue;
                    CollisionDetection det = CollisionDetection.collisionDetection(this, newHitbox, obj);
                    if (det.isCollisionDetected()) {
                    	if(obj instanceof Boulder || obj instanceof PowerUp || obj instanceof Bubble) continue;
                        switch (det.getDirection()) {
                            case LEFT -> newHitbox.setLocation((int) (obj.getX() + obj.getWidth()), newHitbox.y);
                            case RIGHT -> newHitbox.setLocation((int) (obj.getX() - newHitbox.width), newHitbox.y);
                        }
                    }
                }

                this.x = newHitbox.getX();
                moving = movingRight || movingLeft;
                updateHitbox();
            }

            applyGravity(others);
        } else {
            jump(others);
        }

        notifyObserver();
    }

    /**
     * Manages the jump action, including collision detection while in the air.
     *
     * @param others a list of other GameObjects for collision detection
     */
    private void jump(List<GameObject> others) {
        verticalSpeed += jumpSpeed;
        y -= verticalSpeed;
        jumpSpeed -= 0.55;

        if (jumpSpeed <= 0) {
            jumpSpeed = 1.9 * speed;
            verticalSpeed = 0;
            jumping = false;
            for (GameObject obj : others) {
                if (!this.equals(obj)) {
                    CollisionDetection det = CollisionDetection.collisionDetection(this, this.getHitbox(), obj);
                    if (det.isCollisionDetected()) {
                        if (this.y > obj.getY() + obj.getHeight() / 2) {
                            y = obj.getY() + obj.getHeight();
                        } else {
                            y = obj.getY() - getHeight();
                        }
                    }
                }
            }
        }
        updateHitbox();
    }

    /**
     * Adds an observer to the entity.
     *
     * @param obv the observer to add
     */
    @Override
    public void addObserver(Observer obv) {
        observers.add(obv);
    }

    /**
     * Removes an observer from the entity.
     *
     * @param obv the observer to remove
     */
    @Override
    public void removeObserver(Observer obv) {
        observers.remove(obv);
    }

    /**
     * Notifies all observers that the entity has been updated.
     */
    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}
