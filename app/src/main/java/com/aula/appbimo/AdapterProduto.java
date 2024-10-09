package com.aula.appbimo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import com.aula.appbimo.models.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ViewHolder>{
    List<Produto> listaProduto;

    public AdapterProduto(List<Produto> arg){
        this.listaProduto = arg;
    }

    @NonNull
    @Override
    public AdapterProduto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogo, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduto.ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(listaProduto.get(position).getCimgfirebase())
                .into(holder.imgProduto);
        holder.tituloProduto.setText(listaProduto.get(position).getcNome());
        holder.precoProduto.setText(String.valueOf(listaProduto.get(position).getFvalor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "O elemento " + holder.getAdapterPosition() + " foi clicado!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listaProduto.remove(holder.getAdapterPosition());

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Toast.makeText(v.getContext(), "Texto removido", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduto;
        TextView tituloProduto, precoProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduto = itemView.findViewById(R.id.imgProduto);
            tituloProduto = itemView.findViewById(R.id.tituloProduto);
            precoProduto = itemView.findViewById(R.id.precoProduto);
        }
    }
}
