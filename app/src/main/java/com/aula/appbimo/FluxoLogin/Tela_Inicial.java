package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aula.appbimo.MainActivity;
import com.aula.appbimo.R;
import com.aula.appbimo.Tela_Conversas;
import com.aula.appbimo.Tela_Feed;
import com.aula.appbimo.Tela_Perfil;
import com.aula.appbimo.Tela_Planos;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

                tela_ListaProdutos.setUserId(0);
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro
                Log.e("Erro", mensagemErro);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                return true;
            } else if (id == R.id.shop) {
                startActivity(new Intent(getApplicationContext(), Tela_Planos.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.feed) {
                startActivity(new Intent(getApplicationContext(), Tela_Feed.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.chat) {
                startActivity(new Intent(getApplicationContext(), Tela_Conversas.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Tela_Perfil.class));
                overridePendingTransition(0, 0);
                return true;
            } else {
                return false;
            }
        });

        if (savedInstanceState == null) {
            loadFragment(new Tela_ListaProdutos());
        }

        findViewById(R.id.btn_produtos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underline_Produtos.setVisibility(View.VISIBLE);
                underline_Cursos.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameConteudo, tela_ListaProdutos)
                        .commit();
            }
        });

        findViewById(R.id.btn_cursos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underline_Produtos.setVisibility(View.INVISIBLE);
                underline_Cursos.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameConteudo, tela_ListaCursos);
                transaction.commit();
            }
        });

        findViewById(R.id.btn_AddProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tela_AdicionarProduto.class));
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FrameConteudo, fragment);

        fragmentTransaction.commit();
    }

}