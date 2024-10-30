package Model;

import java.util.List;

/**
 * The LevelComponent class represents a component of a game level, consisting of walls and enemies.
 * This class holds and provides access to the walls and enemies present in a particular level.
 */
public class LevelComponent {

    /** A list of walls in the level. */
    private List<Wall> walls;

    /** A list of enemies in the level. */
    private List<Enemy> enemies;

    /**
     * Constructs a LevelComponent with the specified lists of walls and enemies.
     *
     * @param walls the list of walls present in the level
     * @param enemies the list of enemies present in the level
     */
    public LevelComponent(List<Wall> walls, List<Enemy> enemies) {
        this.walls = walls;
        this.enemies = enemies;
    }

    /**
     * Returns the list of walls in the level.
     *
     * @return the list of walls
     */
    public List<Wall> getWalls() {
        return walls;
    }

    /**
     * Returns the list of enemies in the level.
     *
     * @return the list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
