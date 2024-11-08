package com.aula.appbimo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Produto;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_AdicionarProduto extends AppCompatActivity {
    private ImageView btimg;
    private EditText edt_nome, edt_descricao;
    private TextInputEditText edt_valor;
    private boolean inserirImagem = false;
    private Button btn_publicar;
    private int idUsuario = 0;
    private String estadoProduto = "null";
    private Retrofit retrofit;
    private RadioGroup radioGroupEstado, radioGroup2;
    private RadioButton btNovo, btUsado, eletronicos, roupas, moveis;
    private Map<String, String> docData = new HashMap<>();
    private DatabaseFotoGeral databaseFotoGeral = new DatabaseFotoGeral();
    private Intent intent = new Intent(Tela_AdicionarProduto.this, Tela_ErroInterno.class);
    private MainActivity  mainActivity =  new MainActivity();
    private String categoria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_produto);

        btimg = findViewById(R.id.imgColocarFoto);
        btn_publicar = findViewById(R.id.btn_publicarPost);
        radioGroupEstado = findViewById(R.id.radioGroup);
        btNovo = findViewById(R.id.bt_novoAlterar);
        btUsado = findViewById(R.id.bt_usadoAlterar);
        radioGroup2 = findViewById(R.id.radioGroup2);
        eletronicos = findViewById(R.id.eletronicos);
        roupas = findViewById(R.id.roupas);
        moveis = findViewById(R.id.moveis);

        btimg.setOnClickListener(v2 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });


        btn_publicar.setOnClickListener(v -> {
            mainActivity.pegarUsuario(new UsuarioCallback() {
                @Override
                public void onUsuarioEncontrado(Usuario usuario) {
                    if (usuario.getId() != 0) {
                        if(inserirImagem == false) {
                            Toast.makeText(Tela_AdicionarProduto.this, "Escolha uma imagem para que o produto possa ser publicado.", Toast.LENGTH_SHORT).show();
                            Log.e("dbhkujldgyudawvyuhk", "ldhiuugildqwutgiladwyiu");
                        }
                        else{
                            Log.e("Tela_AdicionarProdutdasdaosad", "ldhiuugildqwutgiladwyiu");
                            databaseFotoGeral.uploadFoto(Tela_AdicionarProduto.this, btimg, docData, uriLink -> {
                                adicionarProdutoNoBanco(usuario.getId(), uriLink);
                            });
                            startActivity(new Intent(Tela_AdicionarProduto.this, Tela_Inicial.class));
                            finish();
                        }
                    } else {
                        Log.e("Erro", "ID do usuário não encontrado.");
                    }
                }

                @Override
                public void onErro(String mensagemErro) {
                    // Lida com o erro
                    Log.e("Erro", mensagemErro);
                }
            });
        });


        radioGroupEstado.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == btNovo.getId()) {
                estadoProduto = "Novo";
            } else if (checkedId == btUsado.getId()) {
                estadoProduto = "Usado";
            }
        });

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == eletronicos.getId()) {
                categoria = "PROD_1";
            } else if (checkedId == roupas.getId()) {
                 categoria = "PROD_2";
            } else if (checkedId == moveis.getId()) {
                categoria = "PROD_3";
            }
        });

        findViewById(R.id.fecharTela).setOnClickListener(v -> finish());
    }


    private void adicionarProdutoNoBanco(int idUsuario, String uriLink) {
        edt_valor = findViewById(R.id.InputPrecoAlterar);
        edt_nome = findViewById(R.id.editTextNomeAlterar);
        edt_descricao = findViewById(R.id.edt_descricaoPostAlterar);

        String valorComMoeda = edt_valor.getText().toString();
        String valorSomenteNumero = valorComMoeda.replaceAll("[^\\d.,]", "");
        double valor = 0;
        try {
            valor = Double.parseDouble(valorSomenteNumero.replace(",", "."));
        } catch (NumberFormatException e) {
            startActivity(intent);
        }

        String nome = edt_nome.getText().toString();
        String descricao = edt_descricao.getText().toString();

        // Verifica se algum dos campos está nulo, se o valor não contém nenhum número ou se estadoProduto é nulo
        if (nome.isEmpty() || descricao.isEmpty() || valorSomenteNumero.isEmpty() || estadoProduto == null || categoria.equals("")) {
            Toast.makeText(this, "Todos os campos são obrigatórios e o valor deve ser informado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o nome ou a descrição tem menos de 3 caracteres
        if (nome.length() < 3 || descricao.length() < 3) {
            Toast.makeText(this, "O nome e a descrição devem ter no mínimo 3 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        String API = "https://bimo-web-repo.onrender.com/apibimo/produtos/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Produto produto = new Produto(nome, categoria, descricao, valor, idUsuario, estadoProduto, uriLink);
        ProdutoInterface produtoInterface = retrofit.create(ProdutoInterface.class);
        Call<String> call = produtoInterface.inserirProduto(produto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Tela_AdicionarProduto.this, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API_ERRO", "Erro ao adicionar produto: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("API_ERRO", throwable.getMessage());
            }
        });
    }



    private ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result != null && result.getData() != null && result.getData().getData() != null) {
                    Uri imageUri = result.getData().getData();
                    Glide.with(this).load(imageUri).centerCrop().into(btimg);
                    inserirImagem = true;
                } else {
                    btimg.setImageResource(R.drawable.adicionarfotogrupo);
                    Toast.makeText(this, "Nenhuma imagem foi selecionada. Usando imagem padrão.", Toast.LENGTH_SHORT).show();
                    inserirImagem = false;
                }
            }
    );
}
