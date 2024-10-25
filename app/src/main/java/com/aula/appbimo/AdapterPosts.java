package com.aula.appbimo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.Repositories.PostInterface;
import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Curso;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder>{
    List<Posts> listaPosts;
    Context context;
    MainActivity mainActivity = new MainActivity();
    private Retrofit retrofit;

    public AdapterPosts(List<Posts> arg, Context context) {
        this.listaPosts = arg;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new AdapterPosts.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPosts.ViewHolder holder, int position) {
        mainActivity.pegarUsuarioPorID(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                holder.nome_user.setText(usuario.getCnome());
                Log.e("Foto de perfil:", usuario.getCimgfirebase());
                Glide.with(context)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform()) // Aplica o efeito de círculo na imagem do usuário
                        .into(holder.perfil_user);
            }
            @Override
            public void onErro(String mensagemErro) {
                Log.e("Erro", mensagemErro);
            }
        }, listaPosts.get(position).getiIdUsuario());

        Glide.with(holder.itemView)
                .load(listaPosts.get(position).getcImgFirebase())
                .into(holder.foto);
        holder.descricao.setText(listaPosts.get(position).getcTexto());
        holder.qnt_curtidas.setText(String.valueOf(listaPosts.get(position).getcCurtidas()));


        boolean isLiked = listaPosts.get(position).isLiked();
        updateLikeButton(holder, isLiked);


        holder.curtidas.setOnClickListener(view -> {
            boolean currentlyLiked = listaPosts.get(position).isLiked();
            boolean newLikedState = !currentlyLiked;
            listaPosts.get(position).setLiked(newLikedState);
            updateLikeButton(holder, newLikedState);

        });
    }


    private void updateLikeButton(AdapterPosts.ViewHolder holder, boolean isLiked) {
        if (isLiked) {
            holder.curtidas.setImageResource(R.drawable.coracao);
            holder.qnt_curtidas.setText(String.valueOf(Integer.parseInt(holder.qnt_curtidas.getText().toString()) + 1));
            curtirPostagem(listaPosts.get(holder.getAdapterPosition()).getsID());
        } else {
            holder.curtidas.setImageResource(R.drawable.baseline_favorite_border_24);
            if(Integer.parseInt(holder.qnt_curtidas.getText().toString()) > 0){
                holder.qnt_curtidas.setText(String.valueOf(Integer.parseInt(holder.qnt_curtidas.getText().toString()) - 1));
                descurtirPostagem(listaPosts.get(holder.getAdapterPosition()).getsID());
            }

        }

    }

    public int getItemCount() {
        return listaPosts.size();
    }

    public void curtirPostagem(String id) {
        String API = "https://bimomongoapi.onrender.com/bimomongoapi/postagens/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostInterface PostInterface = retrofit.create(PostInterface.class);
        Call<Posts> call = PostInterface.curtirPostagem(id);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.isSuccessful()) {
                    Posts postagemCurtida = response.body();
                    Log.d("Postagem", "Postagem curtida: " + postagemCurtida);
                } else {
                    Log.e("API_ERRO", "Erro ao curtir postagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.e("API_ERRO", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    public void descurtirPostagem(String id) {
        String API = "https://bimomongoapi.onrender.com/bimomongoapi/postagens/";

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostInterface PostInterface = retrofit.create(PostInterface.class);
        Call<Posts> call = PostInterface.descurtirPostagem(id);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.isSuccessful()) {
                    Posts postagemCurtida = response.body();
                    Log.d("Postagem", "Postagem curtida: " + postagemCurtida);
                } else {
                    Log.e("API_ERRO", "Erro ao curtir postagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.e("API_ERRO", "Falha na requisição: " + t.getMessage());
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView perfil_user, foto;
        TextView nome_user, descricao, qnt_curtidas;
        ImageButton curtidas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            perfil_user = itemView.findViewById(R.id.perfil_user);
            foto = itemView.findViewById(R.id.foto);
            nome_user = itemView.findViewById(R.id.nome_user);
            descricao = itemView.findViewById(R.id.descricao);
            qnt_curtidas = itemView.findViewById(R.id.qnt_curtidas);
            curtidas = itemView.findViewById(R.id.like);
        }
    }
}
