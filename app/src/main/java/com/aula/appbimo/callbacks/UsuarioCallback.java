package com.aula.appbimo.callbacks;

import com.aula.appbimo.models.Usuario;

public interface UsuarioCallback {
    void onUsuarioEncontrado(Usuario usuario);
    void onErro(String mensagemErro);
}
