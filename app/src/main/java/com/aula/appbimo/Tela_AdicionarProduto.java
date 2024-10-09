package com.aula.appbimo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.models.Produto;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private RadioGroup radioGroupEstado;
    private RadioButton btNovo, btUsado;
    private Map<String, String> docData = new HashMap<>();
    private DatabaseFotoGeral databaseFotoGeral = new DatabaseFotoGeral();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_produto);
        Bundle bundle = getIntent().getExtras();

        idUsuario = bundle.getInt("id");

        btimg = findViewById(R.id.imgColocarFoto);
        btn_publicar = findViewById(R.id.btn_publicar);
        radioGroupEstado = findViewById(R.id.radioGroup);
        btNovo = findViewById(R.id.bt_novo);
        btUsado = findViewById(R.id.bt_usado);

        btimg.setOnClickListener(v2 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });

        btn_publicar.setOnClickListener(v -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (idUsuario != 0) {
                        if(inserirImagem == false) {
                            Toast.makeText(Tela_AdicionarProduto.this, "Escolha uma imagem para que o produto possa ser publicado.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseFotoGeral.uploadFoto(Tela_AdicionarProduto.this, btimg, docData, uriLink -> {
                                adicionarProdutoNoBanco(idUsuario, uriLink);
                            });
                        }
                    } else {
                        handler.postDelayed(this, 500);
                    }
                }
            }, 500);
        });


        radioGroupEstado.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == btNovo.getId()) {
                estadoProduto = "Novo";
            } else if (checkedId == btUsado.getId()) {
                estadoProduto = "Usado";
            }
        });
    }


    private void adicionarProdutoNoBanco(int idUsuario, String uriLink) {
        edt_valor = findViewById(R.id.InputPreco);
        edt_nome = findViewById(R.id.editTextNome);
        edt_descricao = findViewById(R.id.editTextDescricao);

        String valorComMoeda = edt_valor.getText().toString();
        String valorSomenteNumero = valorComMoeda.replaceAll("[^\\d.,]", "");
        double valor = 0;
        try {
            valor = Double.parseDouble(valorSomenteNumero.replace(",", "."));
        } catch (NumberFormatException e) {
            Log.e("Erro na transformação do numero", "Valor inválido: " + valorSomenteNumero);
        }

        String nome = edt_nome.getText().toString();
        String descricao = edt_descricao.getText().toString();

        // Verifica se algum dos campos está nulo, se o valor não contém nenhum número ou se estadoProduto é nulo
        if (nome.isEmpty() || descricao.isEmpty() || valorSomenteNumero.isEmpty() || estadoProduto == null) {
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

        Produto produto = new Produto(nome, "prod_1", descricao, valor, idUsuario, estadoProduto, uriLink);
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
