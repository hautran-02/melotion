package com.example.musicplayer.api;

import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserMessage> login(@Field("phone") String phone, @Field("password") String password);


    @POST("user/register")
    Call<UserMessage> register(@Body User user);
}
