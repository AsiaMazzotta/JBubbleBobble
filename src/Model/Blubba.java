package Model;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;


/**
 * The {@code Blubba} class represents a specific type of enemy in the game. 
 * Blubba has free movement in all directions (up, down, left, right) and is not affected by gravity or jumping.
 */
public class Blubba extends Enemy {

	private boolean down,up;
    /**
     * Constructs a new {@code Blubba} instance at the specified position.
     *
     * @param x the x-coordinate of the Blubba's position
     * @param y the y-coordinate of the Blubba's position
     */
    public Blubba(double x, double y) {
        super(x, y, 20, 20); // Calls the constructor of the superclass (Enemy)
    }
    
    @Override
    public void move(List<GameObject> others, Player player) {
        Random r = new Random();
        double n = r.nextDouble() * 100;  
        
        // Random movement logic
        if (down && n < 90) {
            // 90% chance to continue moving right
            up = false;
        } else if (up && n < 90) {
            // 90% chance to continue moving left
            down = false;
        } else if (!up && !down && n < 50) {
            // 50% chance to start moving right if idle
            down = true;
        } else if (!up && !down && n < 100) {
            // 50% chance to start moving left if idle
            up = true;
        } else {
            // No movement
            down = false;
            up = false;
        }
        if(state == State.CAUGHT) {
        	down = false;
        	up = false;
        }
        super.move(others, player);
    }

    @Override
    public void updatePosition(List<GameObject> others) {
    	
    	others.remove(this);
        moving = false;

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
        
        if(up && !down) {
            newHitbox.setLocation((int) newHitbox.x,(int) (this.y - speed));
        }else if (!up && down) {
            newHitbox.setLocation((int) newHitbox.x,(int) (this.y + speed));
        }

        if (!newHitbox.equals(this.hitbox)) {
            for (GameObject obj : others) {
            	if(obj instanceof Boulder || obj instanceof Bubble) continue;
                CollisionDetection det = CollisionDetection.collisionDetection(this, newHitbox, obj);
                if (det.isCollisionDetected()) {
                	if(obj instanceof Boulder || obj instanceof Bubble) continue;
                    switch (det.getDirection()) {
                        case LEFT -> newHitbox.setLocation((int) (obj.getX() + obj.getWidth()), newHitbox.y);
                        case RIGHT -> newHitbox.setLocation((int) (obj.getX() - newHitbox.width), newHitbox.y);
                        case BOTTOM -> newHitbox.setLocation((int) newHitbox.x, (int) (obj.getY() - getHeight()));
                        case TOP -> newHitbox.setLocation((int) newHitbox.x, (int) (obj.getY() + obj.getHeight()));
                    }
                }
            }

            this.x = newHitbox.getX();
            this.y = newHitbox.getY();
            moving = movingRight || movingLeft;
            updateHitbox();
        }
   
        notifyObserver();    
    }

    /**
     * Overrides the {@code setJump} method from the {@code Enemy} class. 
     * Blubba cannot jump, so this method is intentionally left empty.
     */
    @Override
    public void setJump() {
        // Blubba can't jump
    }
    
    /**
     * Overrides the {@code applyGravity} method from the {@code MovableObject} class. 
     * Blubba is not affected by gravity, so this method is intentionally left empty.
     */
    @Override
    public void applyGravity(List<GameObject> others) {
        // Blubba is not affected by gravity
    }
}
