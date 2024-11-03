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

        // Recebe os dados do bundle
        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String imagem = bundle.getString("img");
        String preco = bundle.getString("preco");
        String descricao = bundle.getString("descricao");
        int idusuario = bundle.getInt("idUsuario");
        String id = bundle.getString("id");

        // Atualiza a UI com as informações recebidas
        txtdescricao.setText(descricao);
        txtpreco.setText("R$ " + preco);
        Glide.with(this).load(imagem).into(imgproduto);
        txtnome.setText(nome);

        Bundle bundle2 = new Bundle();

        // Chama o método pegarUsuarioPorID e atualiza a UI com as informações do usuário
        mainActivity.pegarUsuarioPorID(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtnmusuario.setText(usuario.getCnome());
                Glide.with(Tela_CompraProduto.this)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgusuario);

                bundle2.putString("nome", usuario.getCnome());
                bundle2.putString("img", usuario.getCimgfirebase());
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro ao buscar o usuário
                Log.e("Erro", mensagemErro);
            }
        }, idusuario);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_CompraProduto.this, Tela_Chat.class);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });

        voltar.setOnClickListener(v -> finish());
    }
}
