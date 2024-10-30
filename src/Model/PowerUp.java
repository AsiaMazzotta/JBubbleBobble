package Model;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

/**
 * The {@code PowerUp} class represents a collectible item in the game 
 * that provides points or other benefits to the player when collected.
 */
public class PowerUp extends Entity {

    public enum Type {
        MUSHROOM, CHERRY, GREEN_PEPPER, GRAPE, CARROT, ICE_CREAM, WATERMELON, TURNIP, HAMBURGER, CAKE;
    }

    private int points; // Points awarded when this power-up is collected
    private Type type;  // Type of the power-up

    private PowerUp(double x, double y, int points, Type type) {
        super(x, y, 20, 20);
        this.points = points;
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public Type getType() {
        return type;
    }

    public void applyGravity(List<GameObject> others) {
        falling = true;
        verticalSpeed += gravity;
        if (verticalSpeed > terminalVelocity) {
            verticalSpeed = terminalVelocity;
        }

        Rectangle newHitbox = new Rectangle((int) x, (int) (y + verticalSpeed), (int) getWidth(), (int) getHeight());
        CollisionDetection detection = new CollisionDetection(false, null, null);

        for (GameObject obj : others) {
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

        if (this.y >= Utility.HEIGHT) y = 0;

        updateHitbox();
    }

    @Override
    public void collision(Collidable obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            if (type == Type.CAKE || type == Type.GRAPE || type == Type.CARROT || type == Type.TURNIP) {
                p.increaseLives();
            }
            destroy();
        }
    }

    /**
     * Factory class for creating instances of {@code PowerUp}.
     */
    public static class PowerUpFactory {
        public static PowerUp createPowerUp(double x, double y, Type type) {
            switch (type) {
                case MUSHROOM:
                    return new PowerUp(x, y, 100, type);
                case CHERRY:
                    return new PowerUp(x, y, 700, type);
                case GREEN_PEPPER:
                    return new PowerUp(x, y, 10, type);
                case GRAPE:
                    return new PowerUp(x, y, 4000, type);
                case CARROT:
                    return new PowerUp(x, y, 30, type);
                case ICE_CREAM:
                    return new PowerUp(x, y, 950, type);
                case WATERMELON:
                    return new PowerUp(x, y, 600, type);
                case TURNIP:
                    return new PowerUp(x, y, 60, type);
                case HAMBURGER:
                    return new PowerUp(x, y, 2000, type);
                case CAKE:
                    return new PowerUp(x, y, 2000, type);
                default:
                    throw new IllegalArgumentException("Invalid power-up type: " + type);
            }
        }

        public static PowerUp createRandomPowerUp(double x, double y) {
            Random random = new Random();
            Type[] types = Type.values();
            return createPowerUp(x, y, types[random.nextInt(types.length)]);
        }
    }
}
