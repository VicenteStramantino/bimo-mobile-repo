package com.aula.appbimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class Tela_CompraCurso extends Activity {
    private TextView txtdescricao;
    private TextView txtpreco;
    private ImageView imgproduto;
    private TextView txtnome;
    private ImageView voltar;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra_curso);

        txtdescricao = findViewById(R.id.descricaoProduto);
        txtpreco = findViewById(R.id.tituloProduto);
        imgproduto = findViewById(R.id.imgCurso);
        txtnome = findViewById(R.id.precoProduto);
        voltar = findViewById(R.id.voltar);
        btnComprar = findViewById(R.id.btn_comprar);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chama o método comprar e fecha a tela
                startActivity(new Intent(getApplicationContext(), Tela_Chat.class));
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String preco = bundle.getString("preco");
        String imagem = bundle.getString("img");
        String descricao = bundle.getString("descricao");
        String id = bundle.getString("id");

        txtdescricao.setText(descricao);
        txtpreco.setText("R$ " + preco);
        Glide.with(this).load(imagem).into(imgproduto);
        txtnome.setText(nome);

        voltar.setOnClickListener(v -> finish());
    }
}
