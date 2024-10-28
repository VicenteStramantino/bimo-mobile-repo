package com.aula.appbimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private ImageView voltar;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra_produto);

        // Inicializa os componentes da UI
        txtdescricao = findViewById(R.id.descricaoProduto);
        txtpreco = findViewById(R.id.tituloProduto);
        imgproduto = findViewById(R.id.imgProd);
        txtnome = findViewById(R.id.precoProduto);
        imgusuario = findViewById(R.id.imgfotousuarioprod);
        txtnmusuario = findViewById(R.id.txtnmusuarioprod);
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

        // Recebe os dados do bundle
        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String preco = bundle.getString("preco");
        String imagem = bundle.getString("img");
        String descricao = bundle.getString("descricao");
        int idusuario = bundle.getInt("idUsuario");
        String id = bundle.getString("id");

        //Manda um bundle para a tela de chat
        Bundle bundle2 = new Bundle();
        bundle2.putString("nome", nome);
        bundle2.putString("img", imagem);
        Intent intent = new Intent(Tela_CompraProduto.this, Tela_Chat.class);
        intent.putExtras(bundle2);

        // Atualiza a UI com as informações recebidas
        txtdescricao.setText(descricao);
        txtpreco.setText("R$ " + preco);
        Glide.with(this).load(imagem).into(imgproduto);
        txtnome.setText(nome);

        // Chama o método pegarUsuarioPorID e atualiza a UI com as informações do usuário
        mainActivity.pegarUsuarioPorID(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtnmusuario.setText(usuario.getCnome());
                Glide.with(Tela_CompraProduto.this)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgusuario);
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro ao buscar o usuário
                Log.e("Erro", mensagemErro);
            }
        }, idusuario);

        voltar.setOnClickListener(v -> finish());
    }
}
