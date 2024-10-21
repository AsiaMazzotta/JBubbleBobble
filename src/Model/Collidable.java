package Model;
/**
 * Interface to represent the characteristic of Collidable
 */
public interface Collidable {
    
	/**
     * Handles collisions between this object and another GameObject.
     *
     * @param obj the other GameObject with which this object collides
     */

	public void collision(Collidable obj);
}
