package main.java.com.spotify.app.dao;

import main.java.com.spotify.app.model.User;
import main.java.com.spotify.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    // Create a new user
    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement SQLstatement = c.prepareStatement(sql)) {

            SQLstatement.setString(1, user.getUsername());
            SQLstatement.setString(2, user.getEmail());
            SQLstatement.setString(3, user.getPasswordHash());
            SQLstatement.executeUpdate();

            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        // TODO Auto-generated method stub
    }

    @Override
    public User getUserById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateUser(User user) {
        // TODO Auto-generated method stub
    }
}
