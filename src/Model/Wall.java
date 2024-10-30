package Model;

/**
 * The Wall class represents the physical wall of the game.
 * It extends the GameObject class and it has a type based on the level it's in.
 */
public class Wall extends GameObject {

    private WallType type;


    /**
     * Enumeration for wall types
     */
    public enum WallType {
        TYPE_ONE,
        TYPE_TWO,	
        TYPE_THREE,	
        TYPE_FOUR,
        TYPE_FIVE,
        TYPE_SIX,
        TYPE_SEVEN,
        TYPE_EIGHT
    }

    /**
     * Wall constructor
     * @param x
     * @param y
     * @param type
     */

    public Wall(double x, double y, WallType type) {
        super(x, y,18,18);
        this.type = type;
    }

    /**
     * Get wall type
     * @return
     */
    public WallType getType() {return type;}

}
