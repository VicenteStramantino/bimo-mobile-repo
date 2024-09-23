package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.aula.appbimo.R;
import com.aula.appbimo.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_Cadastro extends AppCompatActivity {

    private Retrofit retrofit;

    AppCompatButton btn_voltar, btn_cadastrar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); FirebaseUser userLogin = firebaseAuth.getCurrentUser();

    TextInputEditText txtNome, txtEmail, txtSenha, txtCpf, txtDataNasc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);

        btn_voltar = findViewById(R.id.btn_voltar);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tela_Cadastro.this, Tela_LoginCadastro.class);
                startActivity(intent);
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNome = findViewById(R.id.InputNomeDeUsuario);
                txtEmail = findViewById(R.id.InputEmail);
                txtSenha = findViewById(R.id.InputSenha);
                txtCpf = findViewById(R.id.InputCpf);
                txtDataNasc = findViewById(R.id.InputDataDeNascimento);
                if (txtNome.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty() || txtSenha.getText().toString().isEmpty() || txtCpf.getText().toString().isEmpty() || txtDataNasc.getText().toString().isEmpty()) {
                    Toast.makeText(Tela_Cadastro.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (dataValidator(txtDataNasc.getText().toString())) {
                        if (CPFValidator(txtCpf.getText().toString())) {
                            salvarUsuario();
                        } else {
                            Toast.makeText(Tela_Cadastro.this, "Insira um CPF valido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Tela_Cadastro.this, "Insira um formato valido de data.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    private void salvarUsuario() {
        //salvar no firebase
        firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Atualizar o profile
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(txtNome.getText().toString())
                            .setPhotoUri(Uri.parse("https://www.cnnbrasil.com.br/wp-content/uploads/sites/12/2022/05/03-e1653929996954.jpg?w=1024")).build();
                    userLogin.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                adicionarUsuarioBanco();
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Tela_Cadastro.this, "Deu RED " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Email", task.getException().getMessage());
                }
            }
        });
    }


    public static boolean dataValidator(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean CPFValidator(String cpf) {
        // Remove qualquer caractere que não seja número
        cpf = cpf.replaceAll("[^\\d]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula o primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            firstDigit = (firstDigit >= 10) ? 0 : firstDigit;

            // Calcula o segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            secondDigit = (secondDigit >= 10) ? 0 : secondDigit;

            // Verifica se os dígitos calculados são iguais aos dígitos verificadores do CPF
            return firstDigit == Character.getNumericValue(cpf.charAt(9)) &&
                    secondDigit == Character.getNumericValue(cpf.charAt(10));

        } catch (NumberFormatException e) {
            // Se o CPF tiver algum caractere inválido, retorna falso
            return false;
        }
    }

    public void adicionarUsuarioBanco(){
        String API = "https://bimo-web-repo.onrender.com//apibimo/usuarios/";
        //Configurar Acesso API

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Criar a chamada
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        Usuario usuario = new Usuario(1, txtNome.getText().toString(), txtCpf.getText().toString(), null,txtCpf.getText().toString(), txtEmail.getText().toString(), null, null , txtDataNasc.getText().toString(), null, userLogin.getUid().toString(), null, false);
        Call<String> call = usuarioInterface.inserirUsuario(usuario);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Tela_Cadastro.this, "Inserido com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(Tela_Cadastro.this, "Erro ao inserir " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        }
    }