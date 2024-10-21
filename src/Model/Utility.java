package Model;

import java.util.List;
import java.util.Random;

/**
 * The {@code Utility} class provides various utility methods and constants 
 * used throughout the game.
 */
public class Utility {
    
    /** The width of the game area. */
    public static final int WIDTH = 576;

    /** The height of the game area. */
    public static final int HEIGHT = 432;

    /** The size of each tile in the game grid. */
    public static final int TILE_SIZE = 18;

    /**
     * Generates a random position within the game area that does not 
     * collide with any existing game objects.
     *
     * @param objects a list of game objects to check for collisions
     * @return an array containing the x and y coordinates of the random position
     */
    public static int[] randomPosition(List<GameObject> objects) {
        Random r = new Random();
        while (true) {
            int x = r.nextInt(WIDTH);
            int y = r.nextInt(HEIGHT);
            
            // Check if the random position collides with any game object's hitbox
            if (!objects.stream().anyMatch(obj -> obj.getHitbox().contains(x, y))) {
                return new int[]{x, y}; // Return the valid random position
            }
        }
    }
}
