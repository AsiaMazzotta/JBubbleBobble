package Model;

/**
 * The ZenChan class represents a type of enemy in the game that moves towards the player
 * if the player is within a specified range. ZenChan is a subclass of {@link Enemy} 
 * and has specific behavior for detecting whether the player is within range.
 */
public class ZenChan extends Enemy{
    public ZenChan(double x, double y) {
        super(x, y, 20, 20);
    }

}
