package com.aula.appbimo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Tela_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dashboard);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiMGQ4Zjg5OTgtNmM0Yi00MGE2LWI5YzUtZWFiMWViMWY5YTYyIiwidCI6ImIxNDhmMTRjLTIzOTctNDAyYy1hYjZhLTFiNDcxMTE3N2FjMCJ9");

        findViewById(R.id.btn_dash1).setOnClickListener(v -> {
            webView.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiMGQ4Zjg5OTgtNmM0Yi00MGE2LWI5YzUtZWFiMWViMWY5YTYyIiwidCI6ImIxNDhmMTRjLTIzOTctNDAyYy1hYjZhLTFiNDcxMTE3N2FjMCJ9");
        });

        findViewById(R.id.btn_dash2).setOnClickListener(v -> {
            webView.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiNzJjZGI5YTgtMjIzYi00NDRmLWJjZWUtNzE5ZmVhZmY2OWM4IiwidCI6ImIxNDhmMTRjLTIzOTctNDAyYy1hYjZhLTFiNDcxMTE3N2FjMCJ9");
        });

        findViewById(R.id.fecharTela).setOnClickListener(v -> finish());
    }
}