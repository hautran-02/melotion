package com.example.musicplayer.api;

import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.SongUpdate;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SongApi {
    @FormUrlEncoded
    @POST("song/SongCategory")
    Call<SongMessage> SongCategory(@Field("category_id") Long category_id);


    @POST("song/all")
    Call<SongMessage> getAllSong();

    @FormUrlEncoded
    @POST("song/GetId")
    Call<SongMessage> GetById(@Field("id") Long id);


    @FormUrlEncoded
    @POST("song/delete")
    Call<String> deleteSong(@Field("id") Long id);

    @PUT("song/update/{id}")
    Call<SongMessage> update(@Path("id") long id, @Body SongUpdate song);

    @Multipart
    @POST("song/create")
    Call<String> createSong(@Part MultipartBody.Part file, @Part MultipartBody.Part image, @Part("name") RequestBody name, @Part("author") RequestBody author, @Part("singer") RequestBody singer, @Part("category_id") RequestBody category_id);
}
