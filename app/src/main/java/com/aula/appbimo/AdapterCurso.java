package com.aula.appbimo;

import android.annotation.SuppressLint;
import android.content.Context;
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
        Glide.with(holder.itemView)
                .load(listaCurso.get(position).getcimgfirebase())
                .into(holder.imgCurso);
        holder.tituloCurso.setText(listaCurso.get(position).getcNome());
        holder.qntAulas.setText(listaCurso.get(position).getcDuracao() + " horas");

        int categoria = listaCurso.get(position).getiCategoria();

        if (categoria == 1) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_computer_24);
        } else if (categoria == 2) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_wallet_24);
        } else if (categoria == 3) {
            holder.iconCategoria.setImageResource(R.drawable.baseline_monitor_heart_24);
        }
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
