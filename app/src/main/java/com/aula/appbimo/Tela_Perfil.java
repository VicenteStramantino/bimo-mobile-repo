package com.aula.appbimo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Tela_Perfil extends AppCompatActivity {

    private ImageView imgUsuario;

    private TextView txtnome_usuario;

    private TextView emailUsuario;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);
    }
}
