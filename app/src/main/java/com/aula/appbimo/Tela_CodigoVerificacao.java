package com.aula.appbimo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tela_CodigoVerificacao extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;

    TextView enviarCodigoNovamente;

    AppCompatButton btn_validar, btn_voltar4;

    int numeroAleatorio = 0;

    EditText editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5, editTextCode6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_codigo_verificacao);
        enviarCodigoNovamente = findViewById(R.id.EnviarNovamente);
        solicitarPermissaoSMS();
        editTextCode1 = findViewById(R.id.editTextCode1);
        editTextCode2 = findViewById(R.id.editTextCode2);
        editTextCode3 = findViewById(R.id.editTextCode3);
        editTextCode4 = findViewById(R.id.editTextCode4);
        editTextCode5 = findViewById(R.id.editTextCode5);
        editTextCode6 = findViewById(R.id.editTextCode6);
        btn_validar = findViewById(R.id.btn_validar);
        btn_voltar4 = findViewById(R.id.btn_voltar4);
        List<EditText> editTexts = Arrays.asList(editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5, editTextCode6);

        for (int i = 0; i < editTexts.size(); i++) {
            final int index = i;
            editTexts.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Verifica se o comprimento do texto é 1 e muda o foco para o próximo EditText
                    if (s.length() == 1 && index < editTexts.size() - 1) {
                        editTexts.get(index + 1).requestFocus();  // Move para o próximo EditText
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
         });
}
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String telefone = bundle.getString("telefone");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            } else {
                enviarSMS(telefone);
            }
        }
        enviarCodigoNovamente.setOnClickListener(view -> {
            if (bundle != null) {
                String telefone = bundle.getString("telefone");
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                } else {
                    enviarSMS(telefone);
                }
            }
        });

        btn_validar.setOnClickListener(view -> {
            String code = editTextCode1.getText().toString().trim()
                    + editTextCode2.getText().toString().trim()
                    + editTextCode3.getText().toString().trim()
                    + editTextCode4.getText().toString().trim()
                    + editTextCode5.getText().toString().trim()
                    + editTextCode6.getText().toString().trim();
            if(code.length() < 6){
                Toast.makeText(Tela_CodigoVerificacao.this, "Preencha os 6 numeros solicitados", Toast.LENGTH_SHORT).show();
            }
            if (numeroAleatorio == Integer.parseInt(code)) {
                String hash = bundle.getString("hash");
                Bundle bundle1 = new Bundle();
                bundle1.putString("hash", hash);
                Intent intent = new Intent(Tela_CodigoVerificacao.this, Tela_CadastrarNovaSenha.class);
                intent.putExtras(bundle1);
                startActivity(intent);

            }
            else{
                Toast.makeText(this, "Codigo invalido, tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarSMS(String telefone) {
        Random random = new Random();
        numeroAleatorio = 100000 + random.nextInt(900000);
        Intent intent = new Intent(getApplicationContext().toString());
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), RESULT_OK, intent, PendingIntent.FLAG_IMMUTABLE);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("" + telefone, null,
                "Código de verificação: " + numeroAleatorio,
                pi, null);

    }


    // Verifica e solicita permissões
    private void solicitarPermissaoSMS() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }
    }



}
