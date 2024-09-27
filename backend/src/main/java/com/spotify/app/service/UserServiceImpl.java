package main.java.com.spotify.app.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import main.java.com.spotify.app.dao.UserDAO;
import main.java.com.spotify.app.dao.UserDAOImpl;
import main.java.com.spotify.app.model.User;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean isValidUsername(String username) {
        String regex_pattern = "^[a-zA-Z0-9_-]{5,20}$";
        return username.matches(regex_pattern);
    }

    @Override
    public boolean isValidEmail(String email) {
        String regex_pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex_pattern);
    }

    @Override
    public boolean isValidPassword(String password) {
        /** 
         * minimum length of 8 characters
         * at least one lowercase letter
         * at least one uppercase letter
         * at least one special character
         * at least one digit
         * no whitespaces 
         */
        String regex_pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)[A-Za-z\\d!@#$%^&*]{8,}$";
        return password.matches(regex_pattern);
    }

    @Override
    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    @Override
    public User getUser(String loginIdentifier) throws SQLException {
        if (loginIdentifier == null || loginIdentifier.isEmpty()) {
            return null;
        }

        if (isValidUsername(loginIdentifier)) {
            return userDAO.getUserByUsername(loginIdentifier);

        } else if (isValidEmail(loginIdentifier)) {
            return userDAO.getUserByEmail(loginIdentifier);
        }

        return null;
    }

    @Override
    public void registerUser(String username, String email, String password) throws IllegalArgumentException, SQLException {
        if (username.isEmpty() || !this.isValidUsername(username)) {
            throw new IllegalArgumentException("invalid username!");
        }
        if (email.isEmpty() || !this.isValidEmail(email)) {
            throw new IllegalArgumentException("invalid email!");
        }
        if (password.isEmpty() || !this.isValidPassword(password)) {
            throw new IllegalArgumentException("invalid password!");
        }


        if (userDAO.getUserByUsername(username) != null || userDAO.getUserByEmail(email) != null) {
            throw new IllegalArgumentException("user already exists!");
        }

        userDAO.createUser(new User(username, this.hashPassword(password), email));
    }

    @Override
    public boolean authenticateUser(User user, String plainTextPassword) throws SQLException {
        if (user == null) {
            return false;
        }

        if (!checkPassword(plainTextPassword, user.getPasswordHash())) {
            return false;
        }

        return true;
    }

    @Override
    public void incrementNumFailedAttempts(Integer userId) throws SQLException {
        Map<String, Object> update = new HashMap<>();
        update.put("num_failed_attempts", "num_failed_attempts + 1");

        userDAO.updateUser(userId, update);
    }

    @Override
    public void resetNumFailedAttempts(Integer userId) throws SQLException {
        Map<String, Object> update = new HashMap<>();
        update.put("num_failed_attempts", 0);

        userDAO.updateUser(userId, null);
    }

    @Override
    public void lockUser(Integer userId) throws SQLException {
        Map<String, Object> update = new HashMap<>();
        update.put("is_locked", true);

        userDAO.updateUser(userId, update);
    }
}
