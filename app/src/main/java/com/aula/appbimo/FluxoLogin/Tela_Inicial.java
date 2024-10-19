package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.AdapterProduto;
import com.aula.appbimo.MainActivity;
import com.aula.appbimo.R;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Produto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.Tela_AdicionarProduto;
import com.aula.appbimo.Tela_Perfil;
import com.aula.appbimo.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tela_Inicial extends AppCompatActivity {
    private Tela_ListaProdutos tela_ListaProdutos = new Tela_ListaProdutos();
    private Tela_ListaCursos tela_ListaCursos = new Tela_ListaCursos();
    private int idUsuario = 0;
    private TextView txtBoasVindas;
    private View underline_Produtos;

    MainActivity mainActivity = new MainActivity();
    private View underline_Cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_inicial);
        txtBoasVindas = findViewById(R.id.BoasVindas);
        underline_Produtos = findViewById(R.id.underline_Produtos);
        underline_Cursos = findViewById(R.id.underline_Cursos);
        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtBoasVindas.setText("Boas-vindas, " + usuario.getCnome());

            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro
                Log.e("Erro", mensagemErro);
            }
        });
        Log.e("nfkjsdijds", String.valueOf(idUsuario));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                // Ação para Home
                return true;
            } else if (id == R.id.shop) {
                // Ação para Loja
                return true;
            } else if (id == R.id.feed) {
                // Ação para Feed
                return true;
            } else if (id == R.id.chat) {
                // Ação para Chat
                return true;
            } else if (id == R.id.profile) {
                // Ação para Perfil
                return true;
            } else {
                return false;
            }
        });




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