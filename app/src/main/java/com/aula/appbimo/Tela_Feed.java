package com.aula.appbimo;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;
import com.aula.appbimo.Repositories.PostInterface;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Produto;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tela_Feed extends AppCompatActivity {
    List<Posts> listaPosts = new ArrayList<>();
    AdapterPosts adapterPosts;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_feed);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPosts);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.feed);
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
                return true;
            } else if (id == R.id.chat) {
                startActivity(new Intent(getApplicationContext(), Tela_Conversas.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Tela_Perfil.class));
                overridePendingTransition(0, 0);
                return true;
            } else {
                return false;
            }
        });

        findViewById(R.id.btn_AddPublicacao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tela_AdicionarPublicacao.class));
                finish();
            }
        });

        buscarPosts();
    }

    private void buscarPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimomongoapi.onrender.com/bimomongoapi/postagens/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostInterface postInterface = retrofit.create(PostInterface.class);

        Call<List<Posts>> call = postInterface.listarPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Tela_Feed", response.body().toString());
                    listaPosts.clear();
                    listaPosts.addAll(response.body());

                    AdapterPosts adapterPosts = new AdapterPosts(listaPosts, Tela_Feed.this);
                    recyclerView.setAdapter(adapterPosts);

                    adapterPosts.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
            }
        });
    }
}