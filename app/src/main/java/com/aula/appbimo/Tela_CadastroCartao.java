package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class Tela_CadastroCartao extends AppCompatActivity {

    private EditText campoNumeroCartao;
    private EditText campoNomeTitular;
    private EditText campoValidade;
    private EditText campoCVV;
    private boolean isUpdating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_cartao);

        campoNumeroCartao = findViewById(R.id.InputNumeroCartao);
        campoNomeTitular = findViewById(R.id.InputNomeCartao);
        campoValidade = findViewById(R.id.InputVencimento);
        campoCVV = findViewById(R.id.InputCvv);

        findViewById(R.id.btn_voltar).setOnClickListener(v -> finish());

        campoNomeTitular.addTextChangedListener(new TextWatcher() {
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
                String text = s.toString();
                text = text.replaceAll("[^a-zA-Z ]", "");
                if (!text.equals(s.toString())) {
                    s.replace(0, s.length(), text);
                }
            }
        });

        campoValidade.addTextChangedListener(new TextWatcher() {
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
                if (isUpdating) return;
                isUpdating = true;

                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 2) str = str.substring(0, 2) + "/" + str.substring(2);
                if (str.length() > 5) str = str.substring(0, 5);

                s.replace(0, s.length(), str);
                campoValidade.setSelection(campoValidade.getText().length());
                isUpdating = false;
            }
        });

        campoNumeroCartao.addTextChangedListener(new TextWatcher() {
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
                if (isUpdating) return;
                isUpdating = true;

                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 4) str = str.substring(0, 4) + " " + str.substring(4);
                if (str.length() > 9) str = str.substring(0, 9) + " " + str.substring(9);
                if (str.length() > 14) str = str.substring(0, 14) + " " + str.substring(14);
                if (str.length() > 19) str = str.substring(0, 19);

                s.replace(0, s.length(), str);
                campoNumeroCartao.setSelection(campoNumeroCartao.getText().length());
                isUpdating = false;
            }
        });

        campoCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        campoCVV.addTextChangedListener(new TextWatcher() {
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
                String str = s.toString().replaceAll("[^\\d]", ""); // Remove caracteres não numéricos
                if (!str.equals(s.toString())) {
                    s.replace(0, s.length(), str); // Atualiza o texto apenas com números
                }
            }
        });

        findViewById(R.id.prosseguir).setOnClickListener(v -> {
            if (validarCampos()) {
                startActivity(new Intent(Tela_CadastroCartao.this, Tela_ConfirmacaoPagamento.class));
                finish();
            }
        });
    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(campoNumeroCartao.getText())) {
            campoNumeroCartao.setError("O número do cartão é obrigatório.");
            campoNumeroCartao.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(campoNomeTitular.getText())) {
            campoNomeTitular.setError("O nome do titular é obrigatório.");
            campoNomeTitular.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(campoValidade.getText())) {
            campoValidade.setError("A validade do cartão é obrigatória.");
            campoValidade.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(campoCVV.getText())) {
            campoCVV.setError("O código de segurança (CVV) é obrigatório.");
            campoCVV.requestFocus();
            return false;
        }

        return true;
    }
}
