package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.aula.appbimo.Repositories.PostInterface;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.Repositories.UsuarioInterface;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Produto;
import com.aula.appbimo.models.Usuario;

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapterPosts);

        buscarUsuarioPorId(108);
    }

    private void buscarPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimomongoapi.onrender.com/bimomongoapi/postagens/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostInterface postInterface = retrofit.create(PostInterface.class);

        Call<List<Posts>> call = postInterface.listarPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaPosts.clear();
                    listaPosts.addAll(response.body());
                    adapterPosts.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
            }
        });
    }

    private void buscarUsuarioPorId(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimo-web-repo.onrender.com/apibimo/usuarios/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);

        Call<Usuario> call = usuarioInterface.buscarUsuarioPorID(id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Toast.makeText(Tela_Feed.this, "sucesso", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Tela_Feed.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("foto perfil", response.body().getCimgfirebase());
                    bundle.putString("nome", response.body().getCnome());

                    Toast.makeText(Tela_Feed.this, "bundle feito", Toast.LENGTH_SHORT).show();
                    AdapterPosts adapterPosts = new AdapterPosts(listaPosts, Tela_Feed.this, bundle);
                    recyclerView.setAdapter(adapterPosts);

                    buscarPosts();
                }
                Toast.makeText(Tela_Feed.this, "valor nulo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Tela_Feed.this, "erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}