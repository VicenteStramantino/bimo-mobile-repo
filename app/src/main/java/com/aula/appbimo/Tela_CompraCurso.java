package com.aula.appbimo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class Tela_CompraCurso extends Activity {
    private TextView txtdescricao;
    private TextView txtpreco;
    private ImageView imgproduto;
    private MainActivity mainActivity =  new MainActivity();
    private TextView txtnome;
    private ImageView voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra_curso);

        txtdescricao = findViewById(R.id.descricaoProduto);
        txtpreco = findViewById(R.id.tituloProduto);
        imgproduto = findViewById(R.id.imgCurso);
        txtnome = findViewById(R.id.precoProduto);
        voltar = findViewById(R.id.voltar);
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
