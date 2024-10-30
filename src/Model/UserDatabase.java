package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDatabase} class is a singleton that manages user data,
 * allowing for saving, loading, and managing user records.
 */
public class UserDatabase {

    private List<User> users;
    private static UserDatabase db;

    private UserDatabase() {
        users = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of UserDatabase.
     *
     * @return the instance of UserDatabase
     */
    public static UserDatabase getInstance() {
        if (db == null) {
            db = new UserDatabase();
        }
        return db;
    }

    /**
     * Saves the current user data to the specified file.
     *
     * @param file the file to save user data
     * @throws IOException if an I/O error occurs
     */
    public void saveUsersData(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                writer.write(formatUser(user));
                writer.newLine();
            }
        }
    }

    /**
     * Loads user data from the specified file.
     *
     * @param file the file to load user data from
     * @throws IOException if an I/O error occurs
     */
    public void loadUsersData(File file) throws IOException {
        if (!file.exists() || file.length() == 0) {
            System.out.println("File does not exist or is empty.");
            return;
        }

        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int highestId = 0;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                    // Track the highest ID loaded
                    if (user.getId() > highestId) {
                        highestId = user.getId();
                    }
                }
            }
            // Update idCounter in User class to continue from the highest ID
            User.setIdCounter(highestId + 1);
        }
    }

    private String formatUser(User user) {
        // Format: id,nickname,avatar,playedGames,wonGames,lostGames,level,highestScore
        return String.join(",",
                String.valueOf(user.getId()),  // Save the id as well
                user.getNickname(),
                user.getAvatar(),
                String.valueOf(user.getPlayedGames()),
                String.valueOf(user.getWonGames()),
                String.valueOf(user.getLostGames()),
                String.valueOf(user.getLevel()),
                String.valueOf(user.getHighestScore())
        );
    }

    private User parseUser(String line) {
        String[] parts = line.split(",");
        if (parts.length != 8) {
            return null;
        }
        try {
            int id = Integer.parseInt(parts[0]); // Read the id
            String nickname = parts[1];
            String avatar = parts[2];
            int playedGames = Integer.parseInt(parts[3]);
            int wonGames = Integer.parseInt(parts[4]);
            int lostGames = Integer.parseInt(parts[5]);
            double level = Double.parseDouble(parts[6]);
            int highestScore = Integer.parseInt(parts[7]);

            // Create the user object
            User user = new User(nickname, avatar);
            user.setPlayedGames(playedGames);
            user.setWonGames(wonGames);
            user.setLostGames(lostGames);
            user.setLevel(level);
            user.setHighestScore(highestScore);

            // Manually set the user's id (since the constructor auto-increments it)
            User.setIdCounter(id + 1);

            return user;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing user data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes a user from the database by ID.
     *
     * @param id the ID of the user to remove
     */
    public void removeUserById(int id) {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);
        }
    }

    /**
     * Retrieves the list of all users in the database.
     *
     * @return a list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if not found
     */
    public User getUserById(int id) {
        return users.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .orElse(null);
    }
}
