package com.aula.appbimo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.R;
import com.aula.appbimo.models.CardPlano;

import java.util.List;

public class AdapterPlano extends RecyclerView.Adapter<AdapterPlano.CardViewHolder> {

    private List<CardPlano> cardItems;

    public AdapterPlano(List<CardPlano> cardItems) {
        this.cardItems = cardItems;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_plano, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        int realPosition = position % cardItems.size();
        CardPlano item = cardItems.get(realPosition);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.precoTextView.setText("R$" + item.getPreco());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView precoTextView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cardTitle);
            descriptionTextView = itemView.findViewById(R.id.cardDescription);
            precoTextView = itemView.findViewById(R.id.precoPlano);
        }
    }
}
