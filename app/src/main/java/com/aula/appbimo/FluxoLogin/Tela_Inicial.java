package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aula.appbimo.R;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.Tela_AdicionarProduto;
import com.aula.appbimo.Tela_Perfil;
import com.aula.appbimo.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_Inicial extends AppCompatActivity {

    private Usuario usuario;

    private int idUsuario = 0;
    private Retrofit retrofit;

    private TextView txtBoasVindas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_inicial);
        pegarUsuario();
        txtBoasVindas = findViewById(R.id.BoasVindas);
        Bundle bundle = new Bundle();
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (idUsuario != 0) {
                    txtBoasVindas.setText(txtBoasVindas.getText().toString() + usuario.getCusername());


//                    bundle.putInt("id", usuario.getId());
//                    bundle.putString("nome", usuario.getCnome());
//                    bundle.putString("sobrenome", usuario.getCsobrenome());
//                    bundle.putString("cpf", usuario.getCcpf());
//                    bundle.putString("telefone", usuario.getCtelefone());
//                    bundle.putString("email", usuario.getCemail());
//                    bundle.putString("hash", usuario.getCidhash());
//                    bundle.putString("dataNasc", usuario.getDdatanascimento());
//                    bundle.putInt("plano", usuario.getIdplano());
//                    bundle.putString("imgFirebase", usuario.getCimgfirebase());
//                    bundle.putString("username", usuario.getCusername());
//                    Intent tela_adicionar_produto = new Intent(Tela_Inicial.this, Tela_AdicionarProduto.class);
//                    tela_adicionar_produto.putExtras(bundle);
//                    startActivity(tela_adicionar_produto);

                    Intent tela_perfil = new Intent(Tela_Inicial.this, Tela_Perfil.class);
                    tela_perfil.putExtras(bundle);
                    startActivity(tela_perfil);
                }
                else {
                    handler.postDelayed(this, 500);
                }
            }
        }, 500);
    }


    private void pegarUsuario() {
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
                        usuario = response.body();
                        idUsuario = usuario.getId();
                    } else {
                        Log.e("Erro API", "Resposta sem sucesso ou usuário nulo.");
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable throwable) {
                    Log.e("API_ERRO", throwable.getMessage());
                }
            });
        } else {
            Log.e("Erro", "Usuário não autenticado.");
        }
    }

    public Usuario usuarioContectado() {
        return this.usuario;
    }
}