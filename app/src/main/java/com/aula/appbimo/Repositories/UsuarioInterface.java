package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioInterface{
    @POST("inserir")
    Call<String> inserirUsuario(@Body Usuario usuario);
    @GET("selecionarPorHash/{hash}")
    Call<Usuario> buscarUsuarioPorHash(@Path("hash") String hash);
    @GET("selecionarPorID/{id}")
    Call<Usuario> buscarUsuarioPorID(@Path("id") int id);

    @PUT("atualizar/{id}")
    Call<String> atualizarUsuario(@Path("id") int id, @Body Usuario usuario);
}
