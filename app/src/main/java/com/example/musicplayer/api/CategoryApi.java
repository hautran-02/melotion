package com.example.musicplayer.api;


import com.example.musicplayer.domain.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CategoryApi {
    @POST("category/all")
    Call<List<Category>> getAllCategory();
}
