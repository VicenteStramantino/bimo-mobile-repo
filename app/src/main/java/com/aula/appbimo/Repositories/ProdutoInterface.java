package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProdutoInterface {
    @POST("inserir")
    Call<String> inserirProduto(@Body Produto produto);
}
