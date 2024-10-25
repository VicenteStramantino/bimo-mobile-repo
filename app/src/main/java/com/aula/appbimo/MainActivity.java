package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Usuario usuario = new Usuario();

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pegarUsuario(final UsuarioCallback callback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userLogin = firebaseAuth.getCurrentUser();

        if (userLogin != null) {
            String hash = userLogin.getUid();
            String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
            Call<Usuario> call = usuarioInterface.buscarUsuarioPorHash(hash);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Usuario usuario = response.body();
                        int idusuario = usuario.getId();
                        // Chama o callback com o usuário encontrado
                        callback.onUsuarioEncontrado(usuario);
                    } else {
                        Log.e("IDUSUARIO", "Resposta sem sucesso ou usuário nulo.");
                        callback.onErro("Resposta sem sucesso ou usuário nulo.");
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable throwable) {
                    Log.e("API_ERRO", throwable.getMessage());
                    callback.onErro(throwable.getMessage());
                }
            });
        } else {
            Log.e("ERRO", "Usuário não autenticado.");
            callback.onErro("Usuário não autenticado.");
        }
    }


    public void pegarUsuarioPorID(final UsuarioCallback callback, int id) {
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.buscarUsuarioPorID(id);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    int idusuario = usuario.getId();
                    // Chama o callback com o usuário encontrado
                    callback.onUsuarioEncontrado(usuario);
                } else {
                    Log.e("IDUSUARIO", "Resposta sem sucesso ou usuário nulo.");
                    callback.onErro("Resposta sem sucesso ou usuário nulo.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.e("API_ERRO", throwable.getMessage());
                callback.onErro(throwable.getMessage());
            }
        });
    }


    public void pegarUsuarioPorTelefone(final UsuarioCallback callback, String telefone) {
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        Call<Usuario> call = usuarioInterface.buscarUsuarioPorTelefone(telefone);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    callback.onUsuarioEncontrado(usuario);
                } else {
                    Log.e("IDUSUARIO", "Resposta sem sucesso ou usuário nulo.");
                    callback.onErro("Resposta sem sucesso ou usuário nulo.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.e("API_ERRO", throwable.getMessage());
                callback.onErro(throwable.getMessage());
            }
        });
    }
}