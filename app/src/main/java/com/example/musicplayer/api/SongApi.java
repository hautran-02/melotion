package com.example.musicplayer.api;

import com.example.musicplayer.domain.SongMessage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SongApi {
    @FormUrlEncoded
    @POST("song/SongCategory")
    Call<SongMessage> SongCategory(@Field("category_id") Long category_id);

    @FormUrlEncoded
    @POST("song/GetId")
    Call<SongMessage> GetById(@Field("id") Long id);
}
