package com.aula.appbimo;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.FluxoLogin.Tela_Inicial;
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
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogo, parent, false);
        return new ViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterProduto.ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(listaProduto.get(position).getCimgfirebase())
                .into(holder.imgItem);
        holder.tituloItem.setText(listaProduto.get(position).getcNome());
        holder.precoItem.setText("R$" + listaProduto.get(position).getFvalor());

        String categoria = listaProduto.get(position).getIdCategoria();

        if (categoria != null) {
            if (categoria.equalsIgnoreCase("prod_1")){
                holder.iconCategoria.setImageResource(R.drawable.baseline_computer_24);
            } else if (categoria.equalsIgnoreCase("prod_2")) {
                holder.iconCategoria.setImageResource(R.drawable.noun_get_dressed_2844795);
            } else if (categoria.equalsIgnoreCase("prod_3")) {
                holder.iconCategoria.setImageResource(R.drawable.baseline_table_bar_24);
            }
        }
        else {
            Toast.makeText(context, "ID da categoria definido como nulo", Toast.LENGTH_SHORT).show();
            holder.iconCategoria.setImageResource(R.drawable.baseline_category_24);
        }
    }

    private void buscarCategoria(String categoriaID, CategoriaCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimo-web-repo.onrender.com/apibimo/categorias/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoriaInterface categoriaInterface = retrofit.create(CategoriaInterface.class);

        Call<Categoria> call = categoriaInterface.getCategoryById(categoriaID);
        Toast.makeText(context, call.toString(), Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                    callback.onCategoriaRecebida(response.body().getcNome());
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                callback.onErro(t.getMessage());
                Toast.makeText(context, "aaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            }
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
