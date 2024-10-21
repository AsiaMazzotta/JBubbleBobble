package Model;

/**
 * The {@code PlayerObserver} interface defines a contract for 
 * observers that wish to be notified when the player's lives decrease.
 */
public interface PlayerObserver {
    /**
     * Called when the player's lives decrease.
     */
    void onLivesChange();
}
