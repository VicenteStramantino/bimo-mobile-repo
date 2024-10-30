package com.aula.appbimo.Repositories;

import com.aula.appbimo.models.Curso;
import com.aula.appbimo.models.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CursoInterface {
    @GET("selecionarTodos")
    Call<List<Curso>> listarCursos();
}
