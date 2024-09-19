package main.java.com.spotify.app.dao;

import main.java.com.spotify.app.model.User;
import java.sql.SQLException;
import java.util.Map;

public interface UserDAO {
    // Exception handling needs to be adjusted
    void createUser(User user) throws IllegalArgumentException;
    void updateUser(Integer id, Map<String, Object> updates) throws IllegalArgumentException;
    
    User getUserByUsername(String username) throws SQLException, IllegalArgumentException;
    User getUserByEmail(String email) throws SQLException, IllegalArgumentException;
    User getUserById(Integer id) throws SQLException, IllegalArgumentException;

    void deleteUser(Integer id) throws IllegalArgumentException;
}
