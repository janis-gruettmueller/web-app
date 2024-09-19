package main.java.com.spotify.app.service;

import java.sql.SQLException;

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
    public User getUser(String loginIdentifier) throws IllegalArgumentException, SQLException {
        if (isValidUsername(loginIdentifier)) {
            return userDAO.getUserByUsername(loginIdentifier);
        } else {
            return userDAO.getUserByEmail(loginIdentifier);
        }
    }

    @Override
    public void registerUser(String username, String email, String password) throws IllegalArgumentException, SQLException {
        if (username == null || username.isEmpty() || !this.isValidUsername(username)) {
            throw new IllegalArgumentException("invalid username!");
        }
        if (email == null || email.isEmpty() || !this.isValidEmail(email)) {
            throw new IllegalArgumentException("invalid email!");
        }
        if (password == null || password.isEmpty() || !this.isValidPassword(password)) {
            throw new IllegalArgumentException("invalid password!");
        }


        if (userDAO.getUserByUsername(username) != null || userDAO.getUserByEmail(email) != null) {
            throw new IllegalArgumentException("user already exists!");
        }

        userDAO.createUser(new User(username, this.hashPassword(password), email));
    }

    @Override
    public String authenticateUser(String loginIdentifier, String plainTextPassword) throws IllegalArgumentException, SQLException {
        if (loginIdentifier == null || loginIdentifier.isEmpty() || (!this.isValidUsername(loginIdentifier) && !this.isValidEmail(loginIdentifier))) {
            throw new IllegalArgumentException("invalid login identifier!");
        }     

        // Fetch the user based on the identifier
        User user = this.getUser(loginIdentifier);
        
        // Check if user exists
        if (user == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }

        // Validate password
        if (!checkPassword(plainTextPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password!");
        }

        // Generate session token
        // Session session = new Session(user);
        // return session.generateToken();
        return "success";
    }
}
