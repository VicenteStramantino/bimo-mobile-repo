package com.aula.appbimo.FluxoLogin;

import com.aula.appbimo.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioInterface{
    @POST("inserir")
    Call<String> inserirUsuario(@Body Usuario usuario);
}
