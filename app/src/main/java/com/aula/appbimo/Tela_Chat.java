package com.aula.appbimo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.models.Mensagem;

import java.util.List;

public class Tela_Chat extends AppCompatActivity {
    private RecyclerView messageList;
    private EditText messageInput;
    private ImageButton sendButton;
    private AdapterMensagem adapter;
    private List<Mensagem> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_chat);

        messageList = findViewById(R.id.messageList);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
    }
}