package main.java.com.spotify.app.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private Integer id;
    private String passwordHash;
    private String email;
    private boolean isVerified;
    private boolean isLocked;
    private int numFailedAttempts;

    // constructor for creating a user
    public User(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isVerified = false; // default value in db
        this.isLocked = false; // default value in db
        this.numFailedAttempts = 0; // default value in db
    }

    // constructor for loading full user details from the database  
    public User(Integer id, String username, String passwordHash, String email, boolean isVerified, boolean isLocked, int numFailedAttempts) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isVerified = isVerified;
        this.isLocked = isLocked;
        this.numFailedAttempts = numFailedAttempts;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("['user_id': " + this.id + ", ");
        s.append("'username': " + this.username + ", ");
        s.append("'password_hash': " + this.passwordHash + ", ");
        s.append("'is_verified': " + this.isVerified + ", ");
        s.append("'is_locked': " + this.isLocked + "]");

        return s.toString();
    }

    // getters
    public Integer getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getPasswordHash() { return this.passwordHash; }
    public String getEmail() { return this.email; }
    public boolean isVerified() { return this.isVerified; }
    public boolean isLocked() { return this.isLocked; }
    public int getNumFailedAttempts() { return this.numFailedAttempts; }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
