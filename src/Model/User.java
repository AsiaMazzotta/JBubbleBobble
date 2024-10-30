package Model;

import java.io.Serializable;

/**
 * The {@code User} class represents a player in the game, 
 * containing information about their avatar, nickname, and game statistics.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7373932083076708805L;

    // Static variable to keep track of the last assigned id
    private static int idCounter = 1;

    private int id;                // Unique identifier for the user
    private String avatar;         // User's avatar representation
    private String nickname;       // User's chosen nickname
    private int playedGames;       // Total number of games played by the user
    private int wonGames;          // Total number of games won by the user
    private int lostGames;         // Total number of games lost by the user
    private double level;          // User's current level in the game
    private int highestScore;      // User's highest score achieved

    /**
     * Constructs a new User with the specified nickname and avatar.
     *
     * @param nickname the nickname of the user
     * @param avatar   the avatar of the user
     */
    public User(String nickname, String avatar) {
        this.id = idCounter++; // Assign unique id and increment the counter
        this.avatar = avatar;
        this.nickname = nickname;
        this.playedGames = 0;
        this.wonGames = 0;
        this.lostGames = 0;
        this.level = 1;
        this.highestScore = 0;
    }

    // Getters and setters

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the avatar of the user.
     *
     * @return the user's avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Gets the nickname of the user.
     *
     * @return the user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the total number of games played by the user.
     *
     * @return the number of played games
     */
    public int getPlayedGames() {
        return playedGames;
    }

    /**
     * Sets the total number of games played by the user.
     *
     * @param playedGames the number of played games
     */
    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    /**
     * Gets the total number of games won by the user.
     *
     * @return the number of won games
     */
    public int getWonGames() {
        return wonGames;
    }

    /**
     * Sets the total number of games won by the user.
     *
     * @param wonGames the number of won games
     */
    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    /**
     * Gets the total number of games lost by the user.
     *
     * @return the number of lost games
     */
    public int getLostGames() {
        return lostGames;
    }

    /**
     * Sets the total number of games lost by the user.
     *
     * @param lostGames the number of lost games
     */
    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    /**
     * Gets the current level of the user.
     *
     * @return the user's level
     */
    public double getLevel() {
        return level;
    }

    /**
     * Sets the current level of the user.
     *
     * @param level the user's new level
     */
    public void setLevel(double level) {
        this.level = level;
    }

    /**
     * Gets the highest score achieved by the user.
     *
     * @return the user's highest score
     */
    public int getHighestScore() {
        return highestScore;
    }

    /**
     * Sets the highest score achieved by the user.
     *
     * @param highestScore the new highest score
     */
    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    /**
     * Increments the number of games won by the user and updates their level.
     */
    public void incrementWonGames() {
        wonGames++;
        playedGames++;
        if (playedGames % 10 == 0) levelUp();
    }

    /**
     * Increments the number of games lost by the user and updates their level.
     */
    public void incrementLostGames() {
        playedGames++;
        lostGames++;
        if (playedGames % 10 == 0) levelUp();
    }

    /**
     * Levels up the user by incrementing their level.
     */
    public void levelUp() {
        level++;
    }

    /**
     * Returns a string representation of the user, which is their nickname.
     *
     * @return the user's nickname
     */
    @Override
    public String toString() {
        return nickname;
    }

    /**
     * Sets the id counter to a specific value, typically used for loading users.
     *
     * @param id the new id counter value
     */
    public static void setIdCounter(int id) {
        idCounter = id;
    }

    /**
     * Gets the current value of the id counter.
     *
     * @return the current id counter value
     */
    public static int getIdCounter() {
        return idCounter;
    }
}
