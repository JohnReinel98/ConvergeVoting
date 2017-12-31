package com.dev.jrc.convergevoting.Models;

/**
 * Created by JohnReinel on 12/31/2017.
 */

public class ListTopics {
    private int imageId;
    private String title;
    private String description;

    public ListTopics(int imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
