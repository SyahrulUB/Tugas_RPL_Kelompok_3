package com.project;

import java.time.LocalDateTime;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Story {
    private String storyID;
    private URL contentURL;
    private LocalDateTime timestamp;
    private int durationHours = 24;

    private List<User> viewers = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<User> sharings = new ArrayList<>();

    public Story(String storyID, URL contentURL) {
        this.storyID = storyID;
        this.contentURL = contentURL;
        this.timestamp = LocalDateTime.now();
    }

    public String getStoryID() { return storyID; }
    public void setStoryID(String storyID) { this.storyID = storyID; }

    public URL getContentURL() { return contentURL; }
    public void setContentURL(URL contentURL) { this.contentURL = contentURL; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }

    public void addView(User viewer) {
        if (viewer != null && !viewers.contains(viewer)) viewers.add(viewer);
    }

    public void addComment(Comment comment) {
        if (comment != null) comments.add(comment);
    }

    public void addSharing(User sharing) {
        if (sharing != null && !sharings.contains(sharing)) sharings.add(sharing);
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyID='" + storyID + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
