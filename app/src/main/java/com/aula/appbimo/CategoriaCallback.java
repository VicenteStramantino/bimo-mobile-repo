package com.aula.appbimo;

public interface CategoriaCallback {
    void onCategoriaRecebida(String nomeCategoria);
    void onErro(String mensagemErro);
}