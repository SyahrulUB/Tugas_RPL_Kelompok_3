package com.project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for posts
 */
public abstract class Post {
    protected String postID;
    protected String caption;
    protected LocalDateTime timestamp;

    protected List<Comment> comments = new ArrayList<>();
    protected List<User> likes = new ArrayList<>();

    public Post(String postID, String caption) {
        this.postID = postID;
        this.caption = caption;
        this.timestamp = LocalDateTime.now();
    }

    public String getPostID() { return postID; }
    public void setPostID(String postID) { this.postID = postID; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void addComment(Comment comment) {
        if (comment != null) comments.add(comment);
    }

    public void addLike(User user) {
        if (user != null && !likes.contains(user)) likes.add(user);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public List<Comment> getComments() { return new ArrayList<>(comments); }
    public List<User> getLikes() { return new ArrayList<>(likes); }

    @Override
    public String toString() {
        return "Post{" +
                "postID='" + postID + '\'' +
                ", caption='" + caption + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
