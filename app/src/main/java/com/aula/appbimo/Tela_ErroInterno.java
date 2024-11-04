package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Tela_ErroInterno extends AppCompatActivity {

    Button btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_erro_interno);
        btn_voltar = findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(v -> finish());

    }
}