package com.aula.appbimo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.models.Curso;
import com.aula.appbimo.models.Produto;
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
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogo, parent, false);
        return new AdapterCurso.ViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterCurso.ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(listaCurso.get(position).getcimgfirebase())
                .into(holder.imgItem);
        holder.tituloItem.setText(listaCurso.get(position).getcNome());
        holder.precoItem.setText("R$" + listaCurso.get(position).getfValor());

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
        ImageView imgItem, iconCategoria;
        TextView tituloItem, precoItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            tituloItem = itemView.findViewById(R.id.tituloItem);
            precoItem = itemView.findViewById(R.id.precoItem);
            iconCategoria = itemView.findViewById(R.id.iconCategoria);
        }
    }
}
