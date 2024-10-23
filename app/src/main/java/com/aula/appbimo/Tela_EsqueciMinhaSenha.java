package com.aula.appbimo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class Tela_EsqueciMinhaSenha extends AppCompatActivity {

    private TextInputEditText InputTelefone;

    private AppCompatButton btn_cancelar, btn_trocar;

    private MainActivity mainActivity =  new MainActivity();

    private boolean isUpdating = false;

    String string = "";

    String telefoneNaoFormatado = "";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_esqueci_minha_senha);
        InputTelefone = findViewById(R.id.InputTelefone);
        btn_cancelar = findViewById(R.id.btn_voltarAlterarSenha);
        btn_trocar = findViewById(R.id.btn_enviarAlterarSenha);
        btn_cancelar.setOnClickListener(view -> {
            finish();
        });
        btn_trocar.setOnClickListener(view -> {
            trocarSenha();
        });

        InputTelefone.addTextChangedListener(new TextWatcher() {
            private String telefoneNaoFormatado = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                isUpdating = true;

                // Armazena a versão não formatada (apenas números)
                telefoneNaoFormatado = s.toString().replaceAll("[^\\d]", "");

                String str = telefoneNaoFormatado;
                if (str.length() > 2) str = "(" + str.substring(0, 2) + ") " + str.substring(2);
                if (str.length() > 10) str = str.substring(0, 10) + "-" + str.substring(10);
                if (str.length() > 15) str = str.substring(0, 15);

                s.replace(0, s.length(), str);
                InputTelefone.setSelection(str.length());
                string = str;
                isUpdating = false;
            }

            public String getTelefoneNaoFormatado() {
                return telefoneNaoFormatado;
            }
        });

    }

    private void trocarSenha() {
        if (string.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("Telefoneoadjsda2", string.trim());
        mainActivity.pegarUsuarioPorTelefone(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = usuario.getCemail();
                Log.e("Email", emailAddress);

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Toast.makeText(Tela_EsqueciMinhaSenha.this, "Senha alterada", Toast.LENGTH_SHORT).show();
                            } else {
                                // Algo deu errado
                                Toast.makeText(Tela_EsqueciMinhaSenha.this, "Senha não alterada", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onErro(String mensagemErro) {
                Toast.makeText(mainActivity, "Usuario não encontrado no banco.", Toast.LENGTH_SHORT).show();
            }
        }, string);
    }




}
