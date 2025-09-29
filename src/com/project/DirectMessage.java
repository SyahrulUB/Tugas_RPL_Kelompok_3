package com.project;

import java.time.LocalDateTime;

public class DirectMessage {
    private String messageID;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead = false;

    private User from;
    private User to;

    public DirectMessage(String messageID, String content, User from, User to) {
        this.messageID = messageID;
        this.content = content;
        this.from = from;
        this.to = to;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public boolean isRead() { return isRead; }
    public void markAsRead() { isRead = true; }

    public void sendMessage() {
        // placeholder to simulate sending a message
        System.out.println("Sending DM from " + (from != null ? from.getUserID() : "unknown") + " to " + (to != null ? to.getUserID() : "unknown") + ": " + content);
    }

    public void deleteMessage() {
        // placeholder for deletion
        System.out.println("Deleting message " + messageID);
    }
}
