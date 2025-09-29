package com.project;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User model
 */
public class User {
    private String userID;
    private String name;
    private URL profilePicture;

    private Account account;

    private List<User> following = new ArrayList<>();
    private List<User> followers = new ArrayList<>();

    public User(String userID, String name, URL profilePicture) {
        this.userID = userID;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public URL getProfilePicture() { return profilePicture; }
    public void setProfilePicture(URL profilePicture) { this.profilePicture = profilePicture; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public void followUser(User userToFollow) {
        if (userToFollow != null && !following.contains(userToFollow)) {
            following.add(userToFollow);
            userToFollow.followers.add(this);
        }
    }

    public void sendMessage(User recipient, DirectMessage dm) {
        if (recipient != null && dm != null) {
            // messaging is simplistic here; in real app you'd route via service
            // We'll just mark recipient as having received the message by printing
            System.out.println("Send message from " + this.userID + " to " + recipient.userID + ": " + dm.getContent());
        }
    }

    // ... additional convenience methods could be added

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
