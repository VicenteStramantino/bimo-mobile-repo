package com.aula.appbimo.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Produto {

    private int sid;

    private String cNome;

    private String idCategoria;

    private String cDescricao;

    private String dDataCriacao;

    private double fvalor;

    private int idUsuario;

    private String cEstado;

    private String cimgfirebase;

    public Produto(int sid, String cNome, String idCategoria, String cdescricao, double fvalor, int idUsuario, String cEstado, String cimgfirebase) {
        this.sid = sid;
        this.cNome = cNome;
        this.idCategoria = idCategoria;
        this.cDescricao = cdescricao;
        this.fvalor = fvalor;
        this.idUsuario = idUsuario;
        this.cEstado = cEstado;
        this.cimgfirebase = cimgfirebase;
    }

    public Produto(String cNome, String idCategoria, String cdescricao, double fvalor, int idUsuario, String cEstado, String cimgfirebase) {
        this.cNome = cNome;
        this.idCategoria = idCategoria;
        this.cDescricao = cdescricao;
        this.fvalor = fvalor;
        this.idUsuario = idUsuario;
        this.cEstado = cEstado;
        this.cimgfirebase = cimgfirebase;
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.dDataCriacao = formato.format(dataAtual);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "  cNome='" + cNome + '\'' +
                ", idCategoria='" + idCategoria + '\'' +
                ", cDescricao='" + cDescricao + '\'' +
                ", fvalor=" + fvalor +
                ", idUsuario=" + idUsuario +
                ", cEstado='" + cEstado + '\'' +
                ", cimgfirebase='" + cimgfirebase + '\'' +
                ", dDataCriacao='" + dDataCriacao + '\'' +
                '}';
    }
}
