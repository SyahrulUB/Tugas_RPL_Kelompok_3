package com.project;

/**
 * Concrete PhotoPost class
 */
public class PhotoPost extends Post {
    private String imageURL;
    private String filter;

    public PhotoPost(String postID, String caption, String imageURL, String filter) {
        super(postID, caption);
        this.imageURL = imageURL;
        this.filter = filter;
    }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getFilter() { return filter; }
    public void setFilter(String filter) { this.filter = filter; }

    @Override
    public String toString() {
        return "PhotoPost{" +
                "postID='" + postID + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", filter='" + filter + '\'' +
                '}';
    }
}
