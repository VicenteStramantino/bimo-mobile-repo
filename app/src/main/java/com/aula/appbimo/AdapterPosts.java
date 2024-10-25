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
import com.aula.appbimo.models.Curso;
import com.aula.appbimo.models.Posts;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder>{
    List<Posts> listaPosts;
    Context context;
    MainActivity mainActivity = new MainActivity();

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
                // Lida com o erro ao buscar o usuário
                Log.e("Erro", mensagemErro);
            }
        }, listaPosts.get(position).getiIdUsuario());

        Glide.with(holder.itemView)
                .load(listaPosts.get(position).getcImgFirebase())
                .into(holder.foto);
        holder.descricao.setText(listaPosts.get(position).getcTexto());
        holder.qnt_curtidas.setText(String.valueOf(listaPosts.get(position).getcCurtidas()));
    }

    public int getItemCount() {
        return listaPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView perfil_user, foto;
        TextView nome_user, descricao, qnt_curtidas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            perfil_user = itemView.findViewById(R.id.perfil_user);
            foto = itemView.findViewById(R.id.foto);
            nome_user = itemView.findViewById(R.id.nome_user);
            descricao = itemView.findViewById(R.id.descricao);
            qnt_curtidas = itemView.findViewById(R.id.qnt_curtidas);

        }
    }
}
