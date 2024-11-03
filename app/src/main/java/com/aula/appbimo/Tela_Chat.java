package com.aula.appbimo;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.models.Mensagem;
import com.bumptech.glide.Glide;

import java.util.List;

public class Tela_Chat extends AppCompatActivity {
    private RecyclerView messageList;
    private EditText messageInput;
    private ImageButton sendButton;
    private List<Mensagem> messages;
    private ImageView profileImage;
    private TextView contactName;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_chat);

        messageList = findViewById(R.id.messageList);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        profileImage = findViewById(R.id.profileImage);
        contactName = findViewById(R.id.contactName);
        backButton = findViewById(R.id.backButton);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String imagem = bundle.getString("img");

        contactName.setText(nome);
        Glide.with(this).load(imagem).into(profileImage);

        backButton.setOnClickListener(v -> finish());
    }
}