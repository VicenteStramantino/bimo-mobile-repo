package com.aula.appbimo.models;

public class CardPlano {
    private String title;
    private String description;
    private String preco;

    public CardPlano(String title, String description, String preco) {
        this.title = title;
        this.description = description;
        this.preco = preco;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPreco() { return preco; }
}
