package com.aula.appbimo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.aula.appbimo.FluxoLogin.Tela_ListaCursos;
import com.aula.appbimo.FluxoLogin.Tela_ListaProdutos;


public class Tela_Perfil extends AppCompatActivity {

    private ImageView imgUsuario;
    private TextView txtnome_usuario;
    private TextView emailUsuario;
    private View underline_Produtos;
    private View underline_Cursos;
    private Tela_ListaProdutos tela_ListaProdutos = new Tela_ListaProdutos();
    private Tela_ListaCursos tela_ListaCursos = new Tela_ListaCursos();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);

        underline_Produtos = findViewById(R.id.underline_Produtos);
        underline_Cursos = findViewById(R.id.underline_Cursos);

        ((Button) findViewById(R.id.btn_produtos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underline_Produtos.setVisibility(View.VISIBLE);
                underline_Cursos.setVisibility(View.INVISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameConteudo, tela_ListaProdutos);
                transaction.commit();
            }
        });

        ((Button) findViewById(R.id.btn_cursos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underline_Produtos.setVisibility(View.INVISIBLE);
                underline_Cursos.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameConteudo, tela_ListaCursos);
                transaction.commit();
            }
        });
    }
}
