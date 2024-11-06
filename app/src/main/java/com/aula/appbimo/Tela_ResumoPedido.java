package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.checkerframework.checker.i18nformatter.qual.I18nMakeFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_ResumoPedido extends AppCompatActivity {
    private TextView tituloPlano;
    private TextView precoPlano;
    private TextView descricaoPlano;
    private ImageView fecharTela;
    private Button btn_pagar;

    private MainActivity mainActivity =  new MainActivity();

    private Retrofit retrofit;

    private int idPlano = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resumo_pedido);

        tituloPlano = findViewById(R.id.tituloPlano);
        precoPlano = findViewById(R.id.precoPlano);
        descricaoPlano = findViewById(R.id.descricaoPlano);
        fecharTela = findViewById(R.id.fecharTela);
        btn_pagar = findViewById(R.id.btn_pagar);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String preco = bundle.getString("preco");
        String descricao = bundle.getString("descricao");

        tituloPlano.setText(nome);
        precoPlano.setText(preco);
        descricaoPlano.setText(descricao);

        fecharTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tela_ResumoPedido.this, Tela_Planos.class));
                finish();
            }
        });

        btn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nome.equals("Plano Bronze")){
                   idPlano = 1;
                } else if (nome.equals("Plano Prata")) {
                    idPlano = 2;
                }
                else{
                    idPlano = 3;
                }

                alteraUsuarioBanco();
//                startActivity(new Intent(Tela_ResumoPedido.this, Tela_CadastroCartao.class));
            }
        });
    }

    private void alteraUsuarioBanco() {
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                usuario.setIdplano(idPlano);

                retrofit = new Retrofit.Builder()
                        .baseUrl(API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
                Log.e("Usuaruijdjsid", usuario.toString());
                Call<String> call = usuarioInterface.atualizarUsuario(usuario.getId(), usuario);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("Sucessoadas", response.body().toString());
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("Errosadsad", t.getMessage());
                    }
                });
                startActivity(new Intent(Tela_ResumoPedido.this, Tela_ConfirmacaoPagamento.class));
                finish();

            }
            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro
                Log.e("Errosdasda", mensagemErro);
            }
        });
    }


}