package com.aula.appbimo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Tela_CompraProduto extends Activity {

    private TextView txtdescricao;
    private TextView txtpreco;
    private ImageView imgproduto;

    private MainActivity mainActivity =  new MainActivity();

    private TextView txtnome;

    private TextView txtnmusuario;

    private ImageView imgusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra_produto);
        txtdescricao = findViewById(R.id.descricaoProduto);
        txtpreco = findViewById(R.id.tituloProduto);
        imgproduto = findViewById(R.id.imgProd);
        txtnome = findViewById(R.id.precoProduto);
        imgusuario = findViewById(R.id.imgfotousuarioprod);
        txtnmusuario = findViewById(R.id.txtnmusuarioprod);
        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String preco = bundle.getString("preco");
        String imagem = bundle.getString("imagem");
        String descricao = bundle.getString("descricao");
        int idusuario = bundle.getInt("idUsuario");
        String id = bundle.getString("id");

        txtdescricao.setText(descricao);
        txtpreco.setText(preco);
        Glide.with(this).load(imagem).into(imgproduto);
        txtnome.setText(nome);
        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtnmusuario.setText(usuario.getCnome());
                Glide.with(Tela_CompraProduto.this)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform()) // Aplica o efeito de círculo
                        .into(imgusuario);
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro
                Log.e("Erro", mensagemErro);
            }
        });

    }
}
