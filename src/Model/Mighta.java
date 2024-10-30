package Model;

import java.util.List;

/**
 * The Mighta class represents a type of enemy in the game that can attack the player
 * if the player is within a specified range. Mighta is a subclass of {@link Enemy} 
 * and has specific behavior for detecting whether the player is within attack range.
 */
public class Mighta extends Enemy {

    /** The range within which the Mighta can attack the player. */
    private final double ATTACK_RANGE = Utility.TILE_SIZE * 10;

    /**
     * Constructs a Mighta object with the specified initial coordinates.
     *
     * @param x the X-coordinate of the Mighta
     * @param y the Y-coordinate of the Mighta
     */
    public Mighta(double x, double y) {
        super(x, y, 20, 20);
    }

    /**
     * Moves the Mighta based on the position of the player and other game objects.
     * If the player is within attack range and the Mighta is not in the "caught" state,
     * the Mighta is allowed to attack.
     *
     * @param others a list of other game objects in the game
     * @param player the player object whose position is used for determining attack range
     */
    @Override
    public void move(List<GameObject> others, Player player) {
        if (isPlayerInAttackRange(player) && state != State.CAUGHT) {
            canAttack = true;
        } else {
            canAttack = false;
        }
        super.move(others, player);
    }

    /**
     * Checks if the player is within the attack range of the Mighta. The Mighta
     * can only attack if the player is horizontally within the attack range and 
     * vertically aligned with the Mighta's Y-coordinate.
     *
     * @param player the player object whose position is checked
     * @return {@code true} if the player is in attack range; {@code false} otherwise
     */
    private boolean isPlayerInAttackRange(Player player) {
        if ((player.getY() + 20) >= this.getY() && this.getY() >= player.getY()) {
            if (Math.abs(player.getX() - this.getX()) < ATTACK_RANGE) {
                return true;
            }
        }
        return false;
    }
}
