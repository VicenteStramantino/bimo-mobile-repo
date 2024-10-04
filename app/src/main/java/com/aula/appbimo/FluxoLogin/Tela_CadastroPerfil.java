package com.aula.appbimo.FluxoLogin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_CadastroPerfil extends AppCompatActivity {

    private TextInputEditText txtTelefone, txtNome, txtNomeCompleto;
    ShapeableImageView imgUsuario;

    DatabaseFoto databaseFoto = new DatabaseFoto();
    ImageButton bt_galeria, bt_camera;
    Button btn_voltar, btn_entrar;
    private static String[] REQUIRED_PERMISSIONS;
    Uri uri;
    private boolean isUpdating = false;
    private Retrofit retrofit;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_perfil);

        bt_galeria = findViewById(R.id.bt_galeria);
        bt_camera = findViewById(R.id.bt_camera);
        btn_voltar = findViewById(R.id.btn_voltar);
        btn_entrar = findViewById(R.id.btn_entrar);
        imgUsuario = findViewById(R.id.foto_usuario);
        txtTelefone = findViewById(R.id.InputTelefone);
        txtNome = findViewById(R.id.InputNomeDeUsuario);
        txtNomeCompleto = findViewById(R.id.InputNomeCompleto);

        btn_voltar.setOnClickListener(v -> finish());

        btn_entrar.setOnClickListener(v -> {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            salvarUsuario(bundle);
        });

        txtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;

                isUpdating = true;
                String str = s.toString().replaceAll("[^\\d]", "");

                if (str.length() > 2) str = "(" + str.substring(0, 2) + ") " + str.substring(2);
                if (str.length() > 10) str = str.substring(0, 10) + "-" + str.substring(10);
                if (str.length() > 15) str = str.substring(0, 15);

                s.replace(0, s.length(), str);
                txtTelefone.setSelection(str.length());

                isUpdating = false;
            }
        });

        bt_galeria.setOnClickListener(v2 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });

        bt_camera.setOnClickListener(v -> {
            List<String> requiredPermissions = new ArrayList<>();
            requiredPermissions.add(android.Manifest.permission.CAMERA);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                requiredPermissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            REQUIRED_PERMISSIONS = requiredPermissions.toArray(new String[0]);
            if (allPermissionsGranted()) {
                startCamera();

            } else {
                requestPermissions();
            }
        });
    }

    private ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result != null && result.getData() != null && result.getData().getData() != null) {
                    Uri imageUri = result.getData().getData();
                    uri = imageUri;
                    Glide.with(this).load(imageUri).centerCrop().into(imgUsuario);
                } else {
                    uri = Uri.parse("https://i.pinimg.com/736x/e8/a1/52/e8a15286aec46a1ac01c9c4091c3d793.jpg");
                    Glide.with(this).load(uri).centerCrop().into(imgUsuario);
                    Toast.makeText(this, "Nenhuma imagem foi selecionada. Usando imagem padrão.", Toast.LENGTH_SHORT).show();
                }
            }
    );

    public void startCamera() {
        File imageFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_picture.jpg");
        photoUri = FileProvider.getUriForFile(
                this, getApplicationContext().getPackageName() + ".fileprovider", imageFile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            resultLauncherCamera.launch(cameraIntent);
        }
    }

    private ActivityResultLauncher<Intent> resultLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Glide.with(this).load(photoUri).centerCrop().into(imgUsuario);
                }
            }
    );

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS);
    }

    private ActivityResultLauncher<String[]> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                boolean permissionGranted = true;
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    if (Arrays.asList(REQUIRED_PERMISSIONS).contains(entry.getKey()) && !entry.getValue()) {
                        permissionGranted = false;
                        break;
                    }
                }
                if (!permissionGranted) {
                    Toast.makeText(getApplicationContext(), "Permissão NEGADA. Tente novamente.", Toast.LENGTH_SHORT).show();
                } else {
                    startCamera();
                }
            }
    );

    private void salvarUsuario(Bundle bundle) {
        String cpf = bundle.getString("CPF");
        String dtNascimento = bundle.getString("DtNascimento");
        String email = bundle.getString("Email");
        String senha = bundle.getString("Senha");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser userLogin = firebaseAuth.getCurrentUser();
                    adicionarUsuarioBanco(userLogin, bundle);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(Tela_CadastroPerfil.this, "E-mail já está em uso!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tela_CadastroPerfil.this, Tela_Cadastro.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Tela_CadastroPerfil.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void adicionarUsuarioBanco(FirebaseUser user, Bundle bundle) {
        String cpf = bundle.getString("CPF").replaceAll("[^\\d]", "");
        String dtNascimento = bundle.getString("DtNascimento");
        String email = bundle.getString("Email");
        String API = "https://bimo-web-repo.onrender.com/apibimo/usuarios/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);
        String nomeCompleto = txtNomeCompleto.getText().toString().trim();
        String[] partesNome = nomeCompleto.split("\\s+");

        String primeiroNome = partesNome[0];
        String ultimoSobrenome = partesNome.length > 1 ? partesNome[partesNome.length - 1] : "";
        Usuario usuario = new Usuario(
                primeiroNome, ultimoSobrenome, txtTelefone.getText().toString(),
                cpf, email, dtNascimento, user.getUid(), 1, databaseFoto.getUriLink(), txtNome.getText().toString());

        Call<String> call = usuarioInterface.inserirUsuario(usuario);

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Intent i = new Intent(Tela_CadastroPerfil.this, Tela_Inicial.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ErroNoBanco: ", t.getMessage());
            }
        });
    }
}
