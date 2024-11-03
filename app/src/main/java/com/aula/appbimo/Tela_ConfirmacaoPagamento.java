package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;

public class Tela_ConfirmacaoPagamento extends AppCompatActivity {
    private Button btn_telaInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_pagamento);

        btn_telaInicial = findViewById(R.id.btn_telaInicial);

        btn_telaInicial.setOnClickListener(v -> {
            startActivity(new Intent(Tela_ConfirmacaoPagamento.this, Tela_Inicial.class));
            finish();
        });
    }
}