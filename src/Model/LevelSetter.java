package Model;

/**
 * The {@code LevelSetter} interface defines a contract for creating initial game level components.
 * Implementing classes must provide a method to retrieve the starting objects for a level.
 */
public interface LevelSetter {
    
    /**
     * Returns the starting objects for a level, including walls and enemies.
     *
     * @return a {@link LevelComponent} containing the walls and enemies for the level
     */
    LevelComponent startingObjects();
}
