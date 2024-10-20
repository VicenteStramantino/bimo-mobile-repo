package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.viewpager2.widget.ViewPager2;

import com.aula.appbimo.models.CardPlano;

import java.util.ArrayList;
import java.util.List;

public class Tela_Planos extends AppCompatActivity {
    private ViewPager2 viewPager;
    private AdapterPlano adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_planos);

        viewPager = findViewById(R.id.viewPager);

        List<CardPlano> cardItems = new ArrayList<>();
        cardItems.add(new CardPlano("Plano Bronze", "Gratuito e ideal para quem quer começar a experimentar. Com o Plano BRONZE, você tem 3 dias para divulgar 1 produto por vez, com visibilidade limitada. Esse plano é perfeito para quem deseja dar o primeiro passo sem custos.", "R$ 0,00"));
        cardItems.add(new CardPlano("Plano Prata", "Por apenas R$15,00, o Plano PRATA oferece 1 semana de divulgação com a capacidade de listar até 3 produtos por vez. Com uma visibilidade intermediária, esse plano é ideal para quem busca maior alcance a um preço acessível.", "R$ 15,00"));
        cardItems.add(new CardPlano("Plano Ouro", "O Plano OURO é a melhor opção para quem deseja maximizar a visibilidade e o tempo de exposição. Por R$30,00 pode divulgar até 5 produtos durante 1 mês inteiro, com maior destaque e visibilidade garantida. Ideal para acelerar suas vendas.", "R$ 30,00"));

        adapter = new AdapterPlano(cardItems);
        viewPager.setAdapter(adapter);

        ImageView arrowHint = findViewById(R.id.arrowHint);
        AlphaAnimation fadeInOut = new AlphaAnimation(0.0f, 1.0f);
        fadeInOut.setDuration(1000);
        fadeInOut.setRepeatMode(Animation.REVERSE);
        fadeInOut.setRepeatCount(Animation.INFINITE);
        arrowHint.startAnimation(fadeInOut);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        AdapterPlano adapter = new AdapterPlano(cardItems);
        viewPager.setAdapter(adapter);

        int initialPosition = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(initialPosition, false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

    }
}