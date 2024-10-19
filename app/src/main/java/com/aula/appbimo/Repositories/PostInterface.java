package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("todas")
    Call<List<Posts>> listarPosts();
}
