package com.example.musicplayer.domain;

import com.google.gson.annotations.SerializedName;

public class UserMessage {
    @SerializedName("message")
    private String message;

    @SerializedName("userDTO")
    private User user ;

    public UserMessage(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
