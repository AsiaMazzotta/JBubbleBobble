package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LevelMap} enum defines different levels in the game, each associated with a set of walls and enemies.
 * Each level reads its structure from a corresponding text file and creates the necessary game objects.
 */
public enum LevelMap implements LevelSetter {

    /**
     * Level one of the game, read from "levelOne.txt".
     */
    LEVEL_ONE {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelOne.txt", Wall.WallType.TYPE_ONE);
        }
    },

    /**
     * Level two of the game, read from "levelTwo.txt".
     */
    LEVEL_TWO {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelTwo.txt", Wall.WallType.TYPE_TWO);
        }
    },

    /**
     * Level three of the game, read from "levelThree.txt".
     */
    LEVEL_THREE {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelThree.txt", Wall.WallType.TYPE_THREE);
        }
    },
    
    /**
     * Level four of the game, read from "levelFour.txt".
     */
    LEVEL_FOUR {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelFour.txt", Wall.WallType.TYPE_FOUR);
        }
    },

    /**
     * Level five of the game, read from "levelFive.txt".
     */
    LEVEL_FIVE {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelFive.txt", Wall.WallType.TYPE_FIVE);
        }
    },
    
    /**
     * Level six of the game, read from "levelSix.txt".
     */
    LEVEL_SIX {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelSix.txt", Wall.WallType.TYPE_SIX);
        }
    },
    
    /**
     * Level seven of the game, read from "levelSeven.txt".
     */
    LEVEL_SEVEN {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelSeven.txt", Wall.WallType.TYPE_SEVEN);
        }
    },
    /**
     * Level eight of the game, read from "levelEight.txt".
     */
    LEVEL_EIGHT {
        @Override
        public LevelComponent startingObjects() {
            return readFromFile("./res/Graphics/LevelsMap/levelEight.txt", Wall.WallType.TYPE_EIGHT);
        }
    };

    /**
     * Reads the level layout from a file and generates the walls and enemies based on the content.
     * The file specifies where to place walls ('W'), ZenChan enemies ('Z'), and Mighta enemies ('M').
     *
     * @param filename the name of the file containing the level layout
     * @param type the wall type used for the walls in this level
     * @return a {@link LevelComponent} containing the walls and enemies in the level, or null if an error occurs
     */
    private static LevelComponent readFromFile(String filename, Wall.WallType type) {
        List<Enemy> enemies = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                char[] c = line.toCharArray();
                int x = 0;
                for (int col = 0; col < c.length; col++) {
                    if (c[col] == 'W') {
                        walls.add(new Wall(x, y, type));
                    } else if (c[col] == 'Z') {
                        enemies.add(new ZenChan(x, y));
                    } else if (c[col] == 'M') {
                        enemies.add(new Mighta(x, y));
                    } else if (c[col] == 'B') {
                    	enemies.add(new Blubba(x, y));
                    }
                    x += 18; // Assuming 18 is the tile size
                }
                y += 18;
            }
            return new LevelComponent(walls, enemies);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
