package main.java.com.spotify.app.dao;

import main.java.com.spotify.app.model.User;
import main.java.com.spotify.app.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDAOImpl implements UserDAO {
    private final String db_name = "dev_spotify";

    // Create a new user
    @Override
    public void createUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("IllegalArgument: Invalid user object.");
        }

        String sql = "INSERT INTO user (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql)) {
            SQLStatement.setString(1, user.getUsername());
            SQLStatement.setString(2, user.getEmail());
            SQLStatement.setString(3, user.getPasswordHash());
            SQLStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Integer id, Map<String, Object> updates) throws IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("IllegalArgument: Invalid user ID.");
        }
        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("IllegalArgument: No fields to update");
        }

        StringBuilder sql = new StringBuilder("UPDATE user SET ");
        for (String field : updates.keySet()) {
            sql.append(field).append(" = ?, ");
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE user_id = ?");

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql.toString())) {
            int index = 1;

            for (Object value : updates.values()) {
                SQLStatement.setObject(index++, value);
            }
            SQLStatement.setInt(index, id);
            SQLStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Integer id) throws IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("IllegalArgument: Invalid user ID.");
        }

        String sql = "DELETE FROM user WHERE user_id = ?;";

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql)) {
            SQLStatement.setInt(1, id);
            SQLStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) throws SQLException, IllegalArgumentException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("IllegalArgument: Invalid username");
        }

        User user = null;
        String sql = "SELECT * FROM user WHERE username = ?;";

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql)) {
            SQLStatement.setObject(1, username);

            try (ResultSet resultSet = SQLStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                        resultSet.getInt("user_id"), 
                        resultSet.getString("username"), 
                        resultSet.getString("password_hash"), 
                        resultSet.getString("email"),
                        resultSet.getBoolean("isVerified"),
                        resultSet.getBoolean("isLocked")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error: Executing query for database access", e);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException, IllegalArgumentException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("IllegalArgument: Invalid email");
        }

        User user = null;
        String sql = "SELECT * FROM user WHERE email = ?;";

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql)) {
            SQLStatement.setObject(1, email);

            try (ResultSet resultSet = SQLStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                        resultSet.getInt("user_id"), 
                        resultSet.getString("username"), 
                        resultSet.getString("password_hash"), 
                        resultSet.getString("email"),
                        resultSet.getBoolean("isVerified"),
                        resultSet.getBoolean("isLocked")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error: Executing query for database access", e);
        }

        return user;
    }

    @Override
    public User getUserById(Integer id) throws SQLException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("IllegalArgument: Invalid user ID.");
        }

        User user = null;
        String sql = "SELECT * FROM user WHERE user_id = ?;";

        try (Connection c = DatabaseConnection.getConnection(this.db_name);
            PreparedStatement SQLStatement = c.prepareStatement(sql)) {
            SQLStatement.setInt(1, id);

            try (ResultSet resultSet = SQLStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                        resultSet.getInt("user_id"), 
                        resultSet.getString("username"), 
                        resultSet.getString("password_hash"), 
                        resultSet.getString("email"),
                        resultSet.getBoolean("isVerified"),
                        resultSet.getBoolean("isLocked")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error: Executing query for database access", e);
        }

        return user;
    }
}
