package com.aula.appbimo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aula.appbimo.Repositories.PostInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_AdicionarPublicacao extends AppCompatActivity {

    public boolean inserirImagem = false;
    private Button btn_publicar;
    private ImageView btimg;
    private EditText edt_descricao;
    private Retrofit retrofit;
    private MainActivity mainActivity = new MainActivity();
    private Map<String, String> docData = new HashMap<>();
    private DatabaseFotoGeral databaseFotoGeral = new DatabaseFotoGeral();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_publicacao);

        btn_publicar = findViewById(R.id.btn_publicarPost);
        btimg = findViewById(R.id.imgColocarFotoPost);
        edt_descricao = findViewById(R.id.edt_descricaoPostAlterar);
        btimg.setOnClickListener(v2 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });
        findViewById(R.id.fecharTela).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Tela_Feed.class));
            finish();
        });
        mainActivity.pegarUsuario(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                btn_publicar.setOnClickListener(v -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (usuario.getId() != 0) {
                                if(inserirImagem == false) {
                                    Toast.makeText(Tela_AdicionarPublicacao.this, "Escolha uma imagem para que o produto possa ser publicado.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    databaseFotoGeral.uploadFoto(Tela_AdicionarPublicacao.this, btimg, docData, uriLink -> {
                                        adicionarPostagemNoBanco(usuario.getId(), uriLink);
                                    });
                                    Toast.makeText(Tela_AdicionarPublicacao.this, "Publicação adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                handler.postDelayed(this, 500);
                            }
                        }
                    }, 500);
                });

            }

            @Override
            public void onErro(String mensagemErro) {
                Log.e("Erro", mensagemErro);
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

    private void adicionarPostagemNoBanco(int idUsuario, String uriLink) {
        if (edt_descricao.getText().toString().length() < 3 || TextUtils.isEmpty(edt_descricao.getText().toString().trim())) {
            Toast.makeText(this, "O nome e a descrição devem ter no mínimo 3 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(inserirImagem == false) {
            Toast.makeText(this, "Escolha uma imagem para que o produto possa ser publicado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String API = "https://bimomongoapi.onrender.com/bimomongoapi/postagens/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String id = agora.format(formatter);


        Posts posts = new Posts(id, idUsuario, edt_descricao.getText().toString(), uriLink);
        PostInterface PostInterface = retrofit.create(PostInterface.class);
        Call<String> call = PostInterface.InserirPostagem(posts);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Tela_AdicionarPublicacao.this, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT).show();
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
}