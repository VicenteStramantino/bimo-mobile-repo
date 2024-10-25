package com.aula.appbimo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.models.Curso;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterCurso extends RecyclerView.Adapter<AdapterCurso.ViewHolder>{
    List<Curso> listaCurso;
    Context context;

    public AdapterCurso(List<Curso> arg, Context context){
        this.listaCurso = arg;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCurso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        return new AdapterCurso.ViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterCurso.ViewHolder holder, int position) {
        Curso curso = listaCurso.get(position);
        Glide.with(holder.itemView)
                .load(curso.getCurlfoto())
                .into(holder.imgCurso);
        holder.tituloCurso.setText(curso.getcNome());
        holder.qntAulas.setText(curso.getcDuracao() + " horas");

        String categoria = curso.getiCategoria();

        if (categoria.equalsIgnoreCase("curs_1")) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_computer_24);
        } else if (categoria.equalsIgnoreCase("curs_2")) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_wallet_24);
        } else if (categoria.equalsIgnoreCase("curs_3")) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_monitor_heart_24);
        }

        // Configurar o clique do item para abrir a Tela_CompraCurso
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), Tela_CompraCurso.class);
            Log.e("Curso: ", curso.toString());
            Bundle bundle = new Bundle();
            bundle.putString("nome", curso.getcNome());
            bundle.putString("preco", String.valueOf(curso.getfValor()));
            bundle.putString("img", curso.getCurlfoto());
            bundle.putString("id", String.valueOf(curso.getSid()));
            bundle.putString("descricao", curso.getcDescricao());
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    public int getItemCount() {
        return listaCurso.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCurso, iconCategoria;
        TextView tituloCurso, qntAulas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCurso = itemView.findViewById(R.id.imgCurso);
            tituloCurso = itemView.findViewById(R.id.tituloCurso);
            qntAulas = itemView.findViewById(R.id.qntAulas);
            iconCategoria = itemView.findViewById(R.id.iconCategoria);
        }
    }
}
