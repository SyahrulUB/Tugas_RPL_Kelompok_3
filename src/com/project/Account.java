package com.project;

/**
 * Account class
 */
public class Account {
    private String username;
    private String email;
    private String password;

    private User owner;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean login(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void logout() {
        // placeholder for logout logic
    }

    public void resetPassword() {
        // placeholder for reset logic
    }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
