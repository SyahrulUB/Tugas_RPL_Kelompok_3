package com.project;

import java.time.LocalDateTime;

public class Comment {
    private String commentID;
    private String text;
    private LocalDateTime timestamp;

    public Comment(String commentID, String text) {
        this.commentID = commentID;
        this.text = text;
        this.timestamp = LocalDateTime.now();
    }

    public String getCommentID() { return commentID; }
    public void setCommentID(String commentID) { this.commentID = commentID; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID='" + commentID + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
