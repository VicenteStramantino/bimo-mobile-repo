package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Categoria;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriaInterface {
    @GET("selecionarPorID/{id}")
    Call<Categoria> getCategoryById(@Path("id") String Id);
}
