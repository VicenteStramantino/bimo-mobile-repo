package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.aula.appbimo.R;
import com.aula.appbimo.Tela_EsqueciMinhaSenha;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Tela_Login extends AppCompatActivity {

    AppCompatButton btn_voltar, btn_entrar;

    TextInputEditText InputEmail, InputSenha;
    TextView esqueciMinhaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);
        FirebaseAuth autenticar = FirebaseAuth.getInstance();

        btn_voltar = findViewById(R.id.btn_voltar);
        btn_entrar = findViewById(R.id.btn_entrar);

        esqueciMinhaSenha = findViewById(R.id.EsqueciMinhaSenha);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tela_Login.this, Tela_LoginCadastro.class);
                startActivity(intent);
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputEmail = findViewById(R.id.InputEmail);
                InputSenha = findViewById(R.id.InputSenha);
                String txtEmail = InputEmail.getText().toString();
                String txtSenha = InputSenha.getText().toString();

                if (txtEmail.isEmpty() || txtSenha.isEmpty()) {
                    Toast.makeText(Tela_Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Autentica usuario
                    autenticar.signInWithEmailAndPassword(txtEmail, txtSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //Abrir a tela principal
                                Intent intent = new Intent(Tela_Login.this, Tela_Inicial.class);
                                startActivity(intent);
                            }
                            else {
                                //Exibir mensagem de erro
                                String msg = "Deu RED";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    msg = "Email inexistente";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    msg = "Senha invalida";
                                } catch (Exception e) {
                                    msg = e.getMessage();
                                }

                                Toast.makeText(Tela_Login.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        esqueciMinhaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tela_Login.this, Tela_EsqueciMinhaSenha.class);
                startActivity(intent);
            }
        });
    }
}