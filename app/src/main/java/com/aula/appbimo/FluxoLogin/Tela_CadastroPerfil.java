package com.aula.appbimo.FluxoLogin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aula.appbimo.R;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_CadastroPerfil extends AppCompatActivity {

    private TextInputEditText txtTelefone;
    ShapeableImageView imgUsuario;
    ImageButton bt_galeria, bt_camera;
    Button btn_voltar, btn_entrar;
    Uri uri;
    private boolean isUpdating = false;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_perfil);

        txtTelefone = findViewById(R.id.InputTelefone);
        bt_galeria = findViewById(R.id.bt_galeria);
        bt_camera = findViewById(R.id.bt_camera);
        btn_voltar = findViewById(R.id.btn_voltar);
        btn_entrar = findViewById(R.id.btn_entrar);
        imgUsuario = findViewById(R.id.foto_usuario);



        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                salvarUsuario(bundle);
                Intent i = new Intent(Tela_CadastroPerfil.this, Tela_Login.class);
                startActivity(i);
            }
        });

        //formatar telefone
        txtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nada aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    return;
                }

                isUpdating = true;

                // Remove caracteres não numéricos
                String str = s.toString().replaceAll("[^\\d]", "");

                // Adiciona parênteses após o DDD
                if (str.length() > 2) {
                    str = "(" + str.substring(0, 2) + ") " + str.substring(2);
                }

                // Adiciona hífen após o quinto dígito, mas verifica o tamanho primeiro
                if (str.length() > 10) {
                    str = str.substring(0, 10) + "-" + str.substring(10);
                }

                // Limita o tamanho do telefone a 14 caracteres
                if (str.length() > 15) {
                    str = str.substring(0, 15);
                }

                // Atualiza o campo de texto com o telefone formatado
                s.replace(0, s.length(), str);
                txtTelefone.setSelection(str.length());

                isUpdating = false;
            }
        });

        // abrir galeria
        bt_galeria.setOnClickListener(v2 -> {
            // acessar a galeria
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });

        // abrir camera
        bt_camera.setOnClickListener(v3 -> {
            // abrir camera
            Intent intent = new Intent(Tela_CadastroPerfil.this, FotoActivity.class);
            resultLauncherCamera.launch(intent);
        });


    }
    private ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    // imagem selecionada
                    uri = imageUri;
                    // Exibe a imagem selecionada
                    Glide.with(this).load(imageUri)
                            .centerCrop()
                            .into(imgUsuario);
                } else {
                    // imagem padrão
                    uri = Uri.parse("https://i.pinimg.com/736x/e8/a1/52/e8a15286aec46a1ac01c9c4091c3d793.jpg");
                    Glide.with(this).load("https://i.pinimg.com/736x/e8/a1/52/e8a15286aec46a1ac01c9c4091c3d793.jpg")
                            .centerCrop()
                            .into(imgUsuario);
                }
            }
    );

    private ActivityResultLauncher<Intent> resultLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    // imagem selecionada
                    uri = imageUri;
                    // Exibe a imagem selecionada
                    Glide.with(this).load(imageUri)
                            .centerCrop()
                            .into(imgUsuario);
                } else {
                    // imagem padrão
                    uri = Uri.parse("https://i.pinimg.com/736x/e8/a1/52/e8a15286aec46a1ac01c9c4091c3d793.jpg");
                    Glide.with(this).load("https://i.pinimg.com/736x/e8/a1/52/e8a15286aec46a1ac01c9c4091c3d793.jpg")
                            .centerCrop()
                            .into(imgUsuario);
                }
            }
    );


    private void salvarUsuario(Bundle bundle) {
        String cpf = bundle.getString("CPF");
        String dtNascimento = bundle.getString("DtNascimento");
        String email = bundle.getString("Email");
        String senha = bundle.getString("Senha");
        //salvar no firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser userLogin = firebaseAuth.getCurrentUser();
                    adicionarUsuarioBanco(userLogin, bundle);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        // Caso o erro seja de e-mail já existente
                        Toast.makeText(Tela_CadastroPerfil.this, "E-mail já está em uso!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Outros tipos de erro
                        Toast.makeText(Tela_CadastroPerfil.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    public void adicionarUsuarioBanco(FirebaseUser user, Bundle bundle){
        //Pegando bundle da intent
        String cpf = bundle.getString("CPF");
        String dtNascimento = bundle.getString("DtNascimento");
        String email = bundle.getString("Email");
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";
        //Configurar Acesso API
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Criar a chamada
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        Usuario usuario = new Usuario(
                cpf,
                email,
                dtNascimento,
                user.getUid().toString(),
                1
        );
        Call<String> call = usuarioInterface.inserirUsuario(usuario);
    }
}

