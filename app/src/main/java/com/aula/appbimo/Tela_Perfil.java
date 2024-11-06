package com.aula.appbimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aula.appbimo.FluxoLogin.Tela_Cadastro;
import com.aula.appbimo.FluxoLogin.Tela_Inicial;
import com.aula.appbimo.FluxoLogin.Tela_ListaCursos;
import com.aula.appbimo.FluxoLogin.Tela_ListaProdutos;
import com.aula.appbimo.FluxoLogin.Tela_Login;
import com.aula.appbimo.FluxoLogin.Tela_LoginCadastro;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class Tela_Perfil extends AppCompatActivity {

    private ImageView imgUsuario;
    private TextView txtnome_usuario;
    private TextView emailUsuario;
    private View underline_Produtos;
    private View underline_Cursos;
    private ImageView btn_sair;
    private ImageView editPerfil;
    private Button btn_sair2;
    private FloatingActionButton btn_dash;
    private ImageView pencilIcon;
    private int idUser;
    private Tela_ListaProdutos tela_ListaProdutos = new Tela_ListaProdutos();
    private Tela_ListaCursos tela_ListaCursos = new Tela_ListaCursos();
    private MainActivity mainActivity =  new MainActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);

        FirebaseAuth autenticar = FirebaseAuth.getInstance();

        underline_Produtos = findViewById(R.id.underline_Produtos);
        underline_Cursos = findViewById(R.id.underline_Cursos);
        imgUsuario = findViewById(R.id.fotoUsuario);
        txtnome_usuario = findViewById(R.id.nomeUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        btn_sair = findViewById(R.id.btn_sair);
        editPerfil = findViewById(R.id.editPerfil);
        pencilIcon = findViewById(R.id.pencilIcon);
        btn_dash = findViewById(R.id.btn_dash);


        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtnome_usuario.setText(usuario.getcusername());
                Glide.with(Tela_Perfil.this)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgUsuario);
                emailUsuario.setText(usuario.getCemail());

                idUser = usuario.getId();

                tela_ListaProdutos.setUserId(idUser);

                if(idUser == 115){
                    btn_dash.setVisibility(View.VISIBLE);
                }
                else{
                    btn_dash.setVisibility(View.INVISIBLE);
                }

                if (savedInstanceState == null) {
                    loadFragment();
                }
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro
                Log.e("Erro", mensagemErro);
            }
        });



        btn_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underline_Produtos.setVisibility(View.VISIBLE);
                underline_Cursos.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameConteudo, tela_ListaProdutos)
                        .commit();
            }
        });

        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                findViewById(R.id.btn_cursos).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(usuario.getIdplano() == 1) {
                            Toast.makeText(Tela_Perfil.this, "Você precisa ter o plano prata ou ouro!", Toast.LENGTH_SHORT).show();
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

        findViewById(R.id.btn_dash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tela_Dashboard.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), Tela_Inicial.class));
                overridePendingTransition(0, 0);
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
                return true;
            } else {
                return false;
            }
        });

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticar.signOut();
                Intent intent = new Intent(Tela_Perfil.this, Tela_LoginCadastro.class);
                startActivity(intent);
                finish();
            }
        });

        editPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Perfil.this, Tela_AlterarInfoPerfil.class);
                startActivity(intent);
                finish();
            }
        });

        pencilIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Perfil.this, Tela_AlterarInfoPerfil.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameConteudo, tela_ListaProdutos)
                .commit();
    }
}
