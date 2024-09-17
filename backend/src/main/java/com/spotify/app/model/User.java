package main.java.com.spotify.app.model;

public class User {
    private final int id;
    private final String username;
    private String passwordHash;
    private String email;
    private boolean isVerified = false;
    private boolean isLocked = false;

    public User(int id, String username, String passwordHash, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public int getId() { return this.id; }
    public String getUsername() { return this.username; }

    public String getPasswordHash() { return this.passwordHash; }
    public String getEmail() { return this.email; }
    public boolean isVerified() { return this.isVerified; }
    public boolean isLocked() { return this.isLocked; }

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
