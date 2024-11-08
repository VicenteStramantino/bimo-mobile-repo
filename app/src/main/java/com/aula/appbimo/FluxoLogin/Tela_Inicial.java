package com.aula.appbimo.FluxoLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aula.appbimo.MainActivity;
import com.aula.appbimo.R;
import com.aula.appbimo.Tela_AdicionarProduto;
import com.aula.appbimo.Tela_Feed;
import com.aula.appbimo.Tela_Perfil;
import com.aula.appbimo.Tela_Planos;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tela_Inicial extends AppCompatActivity {
    private Tela_ListaProdutos tela_ListaProdutos = new Tela_ListaProdutos();
    private Tela_ListaCursos tela_ListaCursos = new Tela_ListaCursos();
    private TextView txtBoasVindas;
    private View underline_Produtos;
    MainActivity mainActivity = new MainActivity();
    private View underline_Cursos;
    private Button btn_cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_inicial);
        txtBoasVindas = findViewById(R.id.BoasVindas);
        underline_Produtos = findViewById(R.id.underline_Produtos);
        underline_Cursos = findViewById(R.id.underline_Cursos);
        btn_cursos = findViewById(R.id.btn_cursos);
        btn_cursos.setAlpha(0.5f);
        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtBoasVindas.setText("Boas-vindas, " + usuario.getCnome());

                tela_ListaProdutos.setUserId(0);

                if (usuario.getIdplano() != 1) {
                    btn_cursos.setAlpha(1.0f);
                }
                btn_cursos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(usuario.getIdplano() == 1) {
                            Toast.makeText(Tela_Inicial.this, "Você precisa ter o plano prata ou ouro!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            underline_Produtos.setVisibility(View.INVISIBLE);
                            underline_Cursos.setVisibility(View.VISIBLE);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.FrameConteudo, tela_ListaCursos);
                            transaction.commit();
                        }
                    }
                });
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