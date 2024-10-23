package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aula.appbimo.Repositories.EnderecoInterface;
import com.aula.appbimo.models.Endereco;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_OperacoesEndereco extends AppCompatActivity {


    int idUsuario = 1;

    private TextInputEditText inputRua, inputBairro, inputCidade, inputEstado, inputNumero;

    AppCompatButton btnVoltar, btnConfirmar;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_endereco);

//        Bundle bundle = getIntent().getExtras();
//        idUsuario = bundle.getInt("id");
        btnVoltar = findViewById(R.id.btn_voltar);
        btnConfirmar = findViewById(R.id.btn_confirmar);

        inputRua = findViewById(R.id.InputRua);
        inputNumero = findViewById(R.id.InputNumero);
        inputBairro = findViewById(R.id.InputBairro);
        inputEstado = findViewById(R.id.InputEstado);
        inputCidade = findViewById(R.id.InputCidade);





        btnVoltar.setOnClickListener(view -> {
            finish();
        });

        btnConfirmar.setOnClickListener(view -> {
            adicionarEndereco();
        });
    }


    private void adicionarEndereco() {
        String rua = inputRua.getText().toString().trim();
        String numero = inputNumero.getText().toString().trim();
        String bairro = inputBairro.getText().toString().trim();
        String estado = inputEstado.getText().toString().trim();
        String cidade = inputCidade.getText().toString().trim();

        if (rua.isEmpty() || numero.isEmpty() || bairro.isEmpty() || estado.isEmpty() || cidade.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String API = "https://bimo-web-repo.onrender.com/apibimo/endereco/";

            retrofit = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Endereco endereco = new Endereco("01001-0000", bairro, Integer.parseInt(numero), rua, idUsuario, estado);
            EnderecoInterface enderecoInterface = retrofit.create(EnderecoInterface.class);
            Call<String> call = enderecoInterface.AlterarEndereco(idUsuario, endereco);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                }
            });
            Intent intent = new Intent(Tela_OperacoesEndereco.this, Tela_AlterarInfoPerfil.class);
            startActivity(intent);
        }
    }
}