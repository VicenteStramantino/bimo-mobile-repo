package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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

    TextInputEditText txtEmail, txtSenha, txtCpf, txtDataNasc;
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);

        btn_voltar = findViewById(R.id.btn_voltar);
        btn_cadastrar = findViewById(R.id.btn_Continuar);
        txtEmail = findViewById(R.id.InputEmail);
        txtSenha = findViewById(R.id.InputSenha);
        txtCpf = findViewById(R.id.InputCpf);
        txtDataNasc = findViewById(R.id.InputDataDeNascimento);

        //formatar data
        txtDataNasc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nada aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    return;
                }

                isUpdating = true;

                String str = s.toString().replaceAll("[^\\d]", ""); // Remove caracteres não numéricos

                if (str.length() > 2) {
                    str = str.substring(0, 2) + "/" + str.substring(2);
                }
                if (str.length() > 5) {
                    str = str.substring(0, 5) + "/" + str.substring(5);
                }
                if (str.length() > 10) {
                    str = str.substring(0, 10);
                }

                s.replace(0, s.length(), str);  // Atualiza o campo de texto com a data formatada
                txtDataNasc.setText(str);
                txtDataNasc.setSelection(txtDataNasc.getText().length());
                isUpdating = false;
            }
        });

        //formatar CPF
        txtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nada aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    return;
                }

                isUpdating = true;

                String str = s.toString().replaceAll("[^\\d]", ""); // Remove caracteres não numéricos

                if (str.length() > 3) {
                    str = str.substring(0, 3) + "." + str.substring(3);
                }
                if (str.length() > 7) {
                    str = str.substring(0, 7) + "." + str.substring(7);
                }
                if (str.length() > 11) {
                    str = str.substring(0, 11) + "-" + str.substring(11);
                }
                if (str.length() > 14) {
                    str = str.substring(0, 14); // Limita o tamanho do CPF
                }

                s.replace(0, s.length(), str); // Atualiza o campo de texto com o CPF formatado
                txtCpf.setText(str);
                txtCpf.setSelection(txtCpf.getText().length());
                isUpdating = false;
            }
        });

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
                if (txtEmail.getText().toString().isEmpty() || txtSenha.getText().toString().isEmpty() || txtCpf.getText().toString().isEmpty() || txtDataNasc.getText().toString().isEmpty()) {
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
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser userLogin = firebaseAuth.getCurrentUser();
                    Toast.makeText(Tela_Cadastro.this, userLogin.getUid().toString(), Toast.LENGTH_SHORT).show();
                    adicionarUsuarioBanco(userLogin);
                    Intent intent = new Intent(Tela_Cadastro.this, Tela_CadastroPerfil.class);
                    startActivity(intent);
                }
                else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        // Caso o erro seja de e-mail já existente
                        Toast.makeText(Tela_Cadastro.this, "E-mail já está em uso!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Outros tipos de erro
                        Toast.makeText(Tela_Cadastro.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

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

    public void adicionarUsuarioBanco(FirebaseUser user){
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
        //Configurar Acesso API

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Criar a chamada
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        Usuario usuario = new Usuario(
                txtCpf.getText().toString(),
                txtEmail.getText().toString(),
                txtDataNasc.getText().toString(),
                user.getUid().toString(),
                1
        );
        Call<String> call = usuarioInterface.inserirUsuario(usuario);
        }
    }