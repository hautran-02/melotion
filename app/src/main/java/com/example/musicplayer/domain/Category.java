package com.example.musicplayer.domain;

public class Category {
    private long id;
    private String name;
    private String image;
    private String description;

    public Category(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
