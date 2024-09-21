package main.java.com.spotify.app.service;

import java.sql.SQLException;
import main.java.com.spotify.app.model.User;

public interface UserService {
    
    /**
     * Validates if the username meets the required format.
     * @param username The username to be validated.
     * @return True if valid, false otherwise.
     */
    boolean isValidUsername(String username);

    /**
     * Validates if the email address meets the required format.
     * @param email The email to be validated.
     * @return True if valid, false otherwise.
     */
    boolean isValidEmail(String email);

    /**
     * Validates if the password meets the required format.
     * @param password The password to be validated.
     * @return True if valid, false otherwise.
     */
    boolean isValidPassword(String password);

    /**
     * Hashes the plain text password using BCrypt.
     * @param plainTextPassword The plain text password.
     * @return The hashed password.
     */
    String hashPassword(String plainTextPassword);

    /**
     * Checks if the provided plain text password matches the hashed password.
     * @param plainTextPassword The plain text password.
     * @param hashedPassword The hashed password.
     * @return True if passwords match, false otherwise.
     */
    boolean checkPassword(String plainTextPassword, String hashedPassword);

    /**
     * Fetches a user by username or email.
     * @param loginIdentifier The username or email to search for.
     * @return The User object if found, null otherwise.
     * @throws IllegalArgumentException if loginIdentifier is invalid.
     * @throws SQLException if there is an issue accessing the database.
     */
    User getUser(String loginIdentifier) throws IllegalArgumentException, SQLException;

    /**
     * Registers a new user with the provided username, email, and password.
     * @param username The username of the new user.
     * @param email The email of the new user.
     * @param password The password of the new user.
     * @throws IllegalArgumentException if any provided parameter is invalid.
     * @throws SQLException if there is an issue accessing the database.
     */
    void registerUser(String username, String email, String password) throws IllegalArgumentException, SQLException;

    /**
     * Authenticates a user with the provided login identifier and password.
     * @param loginIdentifier The username or email for login.
     * @param plainTextPassword The plain text password for authentication.
     * @return The User object if authentication is successful, and null if not.
     * @throws SQLException if there is an issue accessing the database.
     */
    User authenticateUser(String loginIdentifier, String plainTextPassword) throws SQLException;
}
