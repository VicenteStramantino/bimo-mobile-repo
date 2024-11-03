package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.checkerframework.checker.i18nformatter.qual.I18nMakeFormat;

public class Tela_ResumoPedido extends AppCompatActivity {
    private TextView tituloPlano;
    private TextView precoPlano;
    private TextView descricaoPlano;
    private ImageView fecharTela;
    private Button btn_pagar;

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
                startActivity(new Intent(Tela_ResumoPedido.this, Tela_CadastroCartao.class));
            }
        });
    }
}