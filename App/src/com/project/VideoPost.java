package com.project;

/**
 * Concrete VideoPost class
 */
public class VideoPost extends Post {
    private String videoURL;
    private int duration; // seconds

    public VideoPost(String postID, String caption, String videoURL, int duration) {
        super(postID, caption);
        this.videoURL = videoURL;
        this.duration = duration;
    }

    public String getVideoURL() { return videoURL; }
    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "VideoPost{" +
                "postID='" + postID + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", duration=" + duration +
                '}';
    }
}
