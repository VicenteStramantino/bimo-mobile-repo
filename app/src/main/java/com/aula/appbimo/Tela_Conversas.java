package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aula.appbimo.models.Conversas;

import java.util.List;

public class Tela_Conversas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterConversas adapter;
    private List<Conversas> conversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conversas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}