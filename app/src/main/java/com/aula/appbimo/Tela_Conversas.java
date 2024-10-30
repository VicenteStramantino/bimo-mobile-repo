package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;
import com.aula.appbimo.models.Conversas;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), Tela_Inicial.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.shop) {
                startActivity(new Intent(getApplicationContext(), Tela_Planos.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.feed) {
                startActivity(new Intent(getApplicationContext(), Tela_Feed.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.chat) {
                return true;
            } else if (id == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Tela_Perfil.class));
                overridePendingTransition(0, 0);
                return true;
            } else {
                return false;
            }
        });
    }
}