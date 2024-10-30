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

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getcNome() {
        return cNome;
    }

    public void setcNome(String cNome) {
        this.cNome = cNome;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getcDescricao() {
        return cDescricao;
    }

    public void setcDescricao(String cDescricao) {
        this.cDescricao = cDescricao;
    }

    public String getdDataCriacao() {
        return dDataCriacao;
    }

    public void setdDataCriacao(String dDataCriacao) {
        this.dDataCriacao = dDataCriacao;
    }

    public double getFvalor() {
        return fvalor;
    }

    public void setFvalor(double fvalor) {
        this.fvalor = fvalor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getcEstado() {
        return cEstado;
    }

    public void setcEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getCimgfirebase() {
        return cimgfirebase;
    }

    public void setCimgfirebase(String cimgfirebase) {
        this.cimgfirebase = cimgfirebase;
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
