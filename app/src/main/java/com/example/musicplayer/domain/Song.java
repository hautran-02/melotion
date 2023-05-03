package com.example.musicplayer.domain;

public class Song {
    private long id;
    private String name;
    private String artist;
    private int image;

    public Song(String name, String singer, int image) {
        this.name = name;
        this.artist = singer;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String singer) {
        this.artist = singer;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
