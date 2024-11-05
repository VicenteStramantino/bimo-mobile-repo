package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aula.appbimo.FluxoLogin.Tela_Cadastro;
import com.aula.appbimo.FluxoLogin.Tela_Login;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_CadastrarNovaSenha extends AppCompatActivity {

    TextInputEditText InputConfirmarSenha, InputSenha;
    AppCompatButton bnt_enviarAlterarSenha, btn_voltarAlterarSenha;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_nova_senha);
    }


}