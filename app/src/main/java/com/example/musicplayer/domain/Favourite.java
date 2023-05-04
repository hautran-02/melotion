package com.example.musicplayer.domain;

import com.google.gson.annotations.SerializedName;

public class Favourite {

    @SerializedName("id")
    private long id;

    @SerializedName("song")
    private Song song;

    @SerializedName("user")
    private User user;
}
