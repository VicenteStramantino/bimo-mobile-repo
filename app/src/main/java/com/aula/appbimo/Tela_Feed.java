package com.aula.appbimo;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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