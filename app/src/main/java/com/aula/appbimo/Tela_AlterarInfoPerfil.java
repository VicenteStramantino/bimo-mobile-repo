package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_AlterarInfoPerfil extends AppCompatActivity {

    private TextInputEditText InputNomeDeUsuario, InputEmail;

    private AppCompatButton btn_cancelar, btn_salvar, btn_alterar_senha;

    private MainActivity mainActivity =  new MainActivity();

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_info_perfil);
            InputNomeDeUsuario = findViewById(R.id.InputNomeDeUsuario);
            InputEmail = findViewById(R.id.InputEmail);
            btn_salvar = findViewById(R.id.btn_salvar);
            btn_cancelar = findViewById(R.id.btn_cancelar);
            btn_alterar_senha = findViewById(R.id.alterar_senha);
            btn_cancelar.setOnClickListener(view -> {
                finish();
            });

            btn_salvar.setOnClickListener(view -> {
                alterarInfo();
            });

            btn_alterar_senha.setOnClickListener(view -> {
                Intent intent = new Intent(Tela_AlterarInfoPerfil.this, Tela_EsqueciMinhaSenha.class);
                startActivity(intent);
            });
    }


    private void alterarInfo() {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        String nomeusuario = InputNomeDeUsuario.getText().toString();
        String email = InputEmail.getText().toString();
        if(nomeusuario.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if (!email.matches(EMAIL_REGEX)) {
                Toast.makeText(mainActivity, "Insira um email valido", Toast.LENGTH_SHORT).show();
                return;
            }
            String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
            mainActivity.pegarUsuario(new UsuarioCallback() {
                @Override
                public void onUsuarioEncontrado(Usuario usuario) {
                    usuario.setCusername(nomeusuario);
                    usuario.setCemail(email);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
                    Log.e("Usuarionjkdbjdbjd", usuario.toString());
                    Call<String> call = usuarioInterface.atualizarUsuario(usuario.getId(), usuario);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {
                        }
                    });
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("Firebase", "Email atualizado com sucesso.");
                                    } else {
                                        Log.e("Firebase", "Erro ao atualizar o email.", task.getException());
                                    }
                                });
                }

                @Override
                public void onErro(String mensagemErro) {
                    // Lida com o erro
                    Log.e("Erro", mensagemErro);
                }
            });
        }
    }
}