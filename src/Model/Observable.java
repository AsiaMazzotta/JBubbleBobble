package Model;

/**
 * The {@code Observable} interface defines a contract for observable objects 
 * in a subject-observer design pattern. Implementing classes can manage 
 * observers and notify them of changes.
 */
public interface Observable {
    
    /**
     * Adds an observer to the list of observers.
     *
     * @param obv the observer to be added
     */
    void addObserver(Observer obv);
    
    /**
     * Removes an observer from the list of observers.
     *
     * @param obv the observer to be removed
     */
    void removeObserver(Observer obv);
    
    /**
     * Notifies all registered observers of a change.
     */
    void notifyObserver();
}
