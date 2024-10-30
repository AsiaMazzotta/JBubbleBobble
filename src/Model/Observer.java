package Model;

/**
 * The {@code Observer} interface defines a contract for observer objects 
 * in a subject-observer design pattern. Implementing classes must provide 
 * an update mechanism to respond to changes in observable objects.
 */
public interface Observer {
    
    /**
     * Updates the observer with information from the observable object.
     *
     * @param obj the observable object that has changed
     */
    void update(Object obj);
}
