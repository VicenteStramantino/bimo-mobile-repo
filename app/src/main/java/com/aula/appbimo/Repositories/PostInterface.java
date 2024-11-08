package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostInterface {
    @GET("todas")
    Call<List<Posts>> listarPosts();

    @POST("inserir")
    Call<String> InserirPostagem(@Body Posts posts);

    @PUT("curtir/{id}")
    Call<Posts> curtirPostagem(@Path("id") String id);

    @PUT("descurtir/{id}")
    Call<Posts> descurtirPostagem(@Path("id") String id);
}
