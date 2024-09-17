package main.java.com.spotify.app.dao;

import main.java.com.spotify.app.model.User;

public interface UserDAO {
    void createUser(User user);
    void updateUser(User user);
    
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);

    void deleteUser(int id);
}
