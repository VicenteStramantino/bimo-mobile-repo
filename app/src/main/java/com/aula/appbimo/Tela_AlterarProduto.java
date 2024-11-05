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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.models.Produto;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_AlterarProduto extends AppCompatActivity {

    private ImageView btimg;
    private EditText edt_nome, edt_descricao;
    private TextInputEditText edt_valor;

    private boolean inserirImagem = true;
    private Button btn_publicar;
    private int idUsuario = 108;
    private String estadoProduto = "null";
    private Retrofit retrofit;
    private RadioGroup radioGroupEstado, radioGroup2;
    private RadioButton btNovo, btUsado, eletronicos, roupas, moveis;

    private int id = 0;
    private ImageButton excluir;
    private Map<String, String> docData = new HashMap<>();
    private DatabaseFotoGeral databaseFotoGeral = new DatabaseFotoGeral();

    private String categoria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_produto);

        btimg = findViewById(R.id.imgColocarFoto);
        btn_publicar = findViewById(R.id.btn_atualizar_produto);
        radioGroupEstado = findViewById(R.id.radioGroupAtualizarProduto);
        btNovo = findViewById(R.id.bt_novoAlterar);
        btUsado = findViewById(R.id.bt_usadoAlterar);
        radioGroup2 = findViewById(R.id.radioGroup2);
        eletronicos = findViewById(R.id.eletronicos);
        roupas = findViewById(R.id.roupas);
        moveis = findViewById(R.id.moveis);
        edt_valor = findViewById(R.id.InputPrecoAlterar);
        edt_nome = findViewById(R.id.editTextNomeAlterar);
        edt_descricao = findViewById(R.id.edt_descricaoPostAlterar);
        excluir = findViewById(R.id.excluir);


        //carregar valores atuais
        Bundle bundle = getIntent().getExtras();
        Glide.with(this).load(bundle.getString("img")).into(btimg);
        String strid = bundle.getString("id");
        id = Integer.parseInt(strid);
        edt_nome.setText(bundle.getString("nome"));
        edt_valor.setText(bundle.getString("preco"));
        edt_descricao.setText(bundle.getString("descricao"));
        if (bundle.getString("estado").equalsIgnoreCase("Novo")) {
            btNovo.setChecked(true);
            estadoProduto = "Novo";
        } else if (bundle.getString("estado").equalsIgnoreCase("Usado")) {
            estadoProduto = "Usado";
            btUsado.setChecked(true);
        }
        if (bundle.getString("categoria").equalsIgnoreCase("prod_1")) {
            eletronicos.setChecked(true);
            categoria = "prod_1";
        } else if (bundle.getString("categoria").equalsIgnoreCase("prod_2")) {
            roupas.setChecked(true);
            categoria = "prod_2";
        } else if (bundle.getString("categoria").equalsIgnoreCase("prod_3")) {
            moveis.setChecked(true);
            categoria = "prod_3";
        }

        btimg.setOnClickListener(v2 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });

        excluir.setOnClickListener(v -> {
            deletarProduto(id);
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
        btn_publicar.setOnClickListener(v -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (idUsuario != 0) {
                        if(inserirImagem == false) {
                            Toast.makeText(Tela_AlterarProduto.this, "Escolha uma imagem para que o produto possa ser publicado.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseFotoGeral.uploadFoto(Tela_AlterarProduto.this, btimg, docData, uriLink -> {
                                alterarProduto(idUsuario, uriLink);
                            });
                        }
                    } else {
                        handler.postDelayed(this, 500);
                    }
                }
            }, 500);
        });



        findViewById(R.id.fecharTela).setOnClickListener(v -> finish());
    }

    public void deletarProduto(int id) {
        String API = "https://bimo-web-repo.onrender.com/apibimo/produtos/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProdutoInterface produtoInterface = retrofit.create(ProdutoInterface.class);
        Call<String> call = produtoInterface.deletarProduto(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("Sucesso: " + response.body());
                } else {
                    System.out.println("Erro ao deletar produto: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void alterarProduto(int idUsuario, String uriLink) {
        String valorComMoeda = edt_valor.getText().toString();
        String valorSomenteNumero = valorComMoeda.replaceAll("[^\\d.,]", "");
        double valor = 0;
        try {
            valor = Double.parseDouble(valorSomenteNumero.replace(",", "."));
        } catch (NumberFormatException e) {
        }

        String nome = edt_nome.getText().toString();
        String descricao = edt_descricao.getText().toString();

        if (nome.isEmpty() || descricao.isEmpty() || valorSomenteNumero.isEmpty() || estadoProduto == null || categoria.equals("")) {
            Toast.makeText(this, "Todos os campos são obrigatórios e o valor deve ser informado.", Toast.LENGTH_SHORT).show();
            return;
        }

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
        Call<String> call = produtoInterface.atualizarProduto(id, produto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });
        Intent intent = new Intent(Tela_AlterarProduto.this, Tela_Perfil.class);
        startActivity(intent);
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
