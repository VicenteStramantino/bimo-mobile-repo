package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoInterface {

    @GET("selecionarTodos")
    Call<List<Produto>> listarProdutos();

    @POST("inserir")
    Call<String> inserirProduto(@Body Produto produto);

    @PUT("atualizar/{id}")
    Call<String> atualizarProduto(@Path("id") int id, @Body Produto produto);

    @DELETE("deletar/{ids}")
    Call<String> deletarProduto(@Path("ids") int ids);
}
