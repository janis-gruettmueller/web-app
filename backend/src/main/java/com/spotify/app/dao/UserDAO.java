package main.java.com.spotify.app.dao;

import main.java.com.spotify.app.model.User;
import java.sql.SQLException;
import java.util.Map;

public interface UserDAO {
    
    /**
     * Creates a new user in the database.
     * @param user The user object containing the user details.
     * @throws IllegalArgumentException if the user object is invalid.
     */
    void createUser(User user) throws IllegalArgumentException;

    /**
     * Updates an existing user in the database based on the provided fields.
     * @param id The ID of the user to update.
     * @param updates A map of fields to update and their new values.
     * @throws IllegalArgumentException if the user ID or updates are invalid.
     */
    void updateUser(Integer id, Map<String, Object> updates) throws IllegalArgumentException;

    /**
     * Deletes a user from the database by their ID.
     * @param id The ID of the user to delete.
     * @throws IllegalArgumentException if the user ID is invalid.
     */
    void deleteUser(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves a user from the database by their username.
     * @param username The username of the user to retrieve.
     * @return The user object if found, or null if not found.
     * @throws IllegalArgumentException if the username is invalid.
     * @throws SQLException if there is an issue accessing the database.
     */
    User getUserByUsername(String username) throws IllegalArgumentException, SQLException;

    /**
     * Retrieves a user from the database by their email.
     * @param email The email of the user to retrieve.
     * @return The user object if found, or null if not found.
     * @throws IllegalArgumentException if the email is invalid.
     * @throws SQLException if there is an issue accessing the database.
     */
    User getUserByEmail(String email) throws IllegalArgumentException, SQLException;

    /**
     * Retrieves a user from the database by their ID.
     * @param id The ID of the user to retrieve.
     * @return The user object if found, or null if not found.
     * @throws IllegalArgumentException if the user ID is invalid.
     * @throws SQLException if there is an issue accessing the database.
     */
    User getUserById(Integer id) throws IllegalArgumentException, SQLException;
}
