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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.Repositories.CategoriaInterface;
import com.aula.appbimo.models.Categoria;
import com.bumptech.glide.Glide;

import com.aula.appbimo.models.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ViewHolder>{
    List<Produto> listaProduto;
    Context context;

    public AdapterProduto(List<Produto> arg, Context context){
        this.listaProduto = arg;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProduto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterProduto.ViewHolder holder, int position) {
        Produto produto = listaProduto.get(position);

        // Carregar imagem com Glide
        Glide.with(holder.itemView)
                .load(produto.getCimgfirebase())
                .into(holder.imgItem);

        // Configurar nome e preço do produto
        holder.tituloItem.setText(produto.getcNome());
        holder.precoItem.setText("R$" + produto.getFvalor());

        // Configurar o ícone da categoria
        String categoria = produto.getIdCategoria();
        if (categoria != null) {
            if (categoria.equalsIgnoreCase("prod_1")) {
                holder.iconCategoria.setImageResource(R.drawable.baseline_computer_24);
            } else if (categoria.equalsIgnoreCase("prod_2")) {
                holder.iconCategoria.setImageResource(R.drawable.noun_get_dressed_2844795);
            } else if (categoria.equalsIgnoreCase("prod_3")) {
                holder.iconCategoria.setImageResource(R.drawable.baseline_table_bar_24);
            }
        } else {
            Toast.makeText(context, "ID da categoria definido como nulo", Toast.LENGTH_SHORT).show();
            holder.iconCategoria.setImageResource(R.drawable.baseline_category_24);
        }

        // Configurar o clique do item para abrir a Tela_CompraProduto
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), Tela_CompraProduto.class);
            Log.e("Produto: ", produto.toString());
            Bundle bundle = new Bundle();
            bundle.putString("nome", produto.getcNome());
            bundle.putString("preco", String.valueOf(produto.getFvalor()));
            bundle.putString("img", produto.getCimgfirebase());
            bundle.putString("id", String.valueOf(produto.getSid()));
            bundle.putInt("idUsuario", produto.getIdUsuario());
            bundle.putString("descricao", produto.getcDescricao());
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
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
