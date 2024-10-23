package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Endereco;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EnderecoInterface {

    @POST("inserir")
    Call<String> InserirPostagem(@Body Endereco posts);

    @PUT("atualizar/{id}")
    Call<String> AlterarEndereco( @Path("id") int id, @Body Endereco endereco);
}
