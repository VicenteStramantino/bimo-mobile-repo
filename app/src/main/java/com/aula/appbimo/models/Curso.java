package com.aula.appbimo.models;

public class Curso {
    private int sid;
    private double fValor;
    private String cDescricao;
    private String cCertificacao;
    private int iCategoria;
    private int iNumeroInscricao;
    private String cNome;
    private String cDuracao;
    private String cimgfirebase;
    private boolean transaction_made;

    public Curso(int sid, double fValor, String cDescricao, String cCertificacao, int iCategoria, int iNumeroInscricao, String cNome, String cDuracao, String cimgfirebase) {
        this.sid = sid;
        this.fValor = fValor;
        this.cDescricao = cDescricao;
        this.cCertificacao = cCertificacao;
        this.iCategoria = iCategoria;
        this.iNumeroInscricao = iNumeroInscricao;
        this.cNome = cNome;
        this.cDuracao = cDuracao;
        this.cimgfirebase = cimgfirebase;
    }

    public Curso() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public double getfValor() {
        return fValor;
    }

    public void setfValor(double fValor) {
        this.fValor = fValor;
    }

    public String getcDescricao() {
        return cDescricao;
    }

    public void setcDescricao(String cDescricao) {
        this.cDescricao = cDescricao;
    }

    public String getcCertificacao() {
        return cCertificacao;
    }

    public void setcCertificacao(String cCertificacao) {
        this.cCertificacao = cCertificacao;
    }

    public int getiCategoria() {
        return iCategoria;
    }

    public void setiCategoria(int iCategoria) {
        this.iCategoria = iCategoria;
    }

    public int getiNumeroInscricao() {
        return iNumeroInscricao;
    }

    public void setiNumeroInscricao(int iNumeroInscricao) {
        this.iNumeroInscricao = iNumeroInscricao;
    }

    public String getcNome() {
        return cNome;
    }

    public void setcNome(String cNome) {
        this.cNome = cNome;
    }

    public String getcDuracao() {
        return cDuracao;
    }

    public void setcDuracao(String cDuracao) {
        this.cDuracao = cDuracao;
    }

    public boolean isTransaction_made() {
        return transaction_made;
    }

    public void setTransaction_made(boolean transaction_made) {
        this.transaction_made = transaction_made;
    }

    public String getcimgfirebase() {
        return cimgfirebase;
    }

    public void setcimgfirebase(String cimgfirebase) {
        this.cimgfirebase = cimgfirebase;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "sid=" + sid +
                ", fValor=" + fValor +
                ", cDescricao='" + cDescricao + '\'' +
                ", cCertificacao='" + cCertificacao + '\'' +
                ", iCategoria=" + iCategoria +
                ", iNumeroInscricao=" + iNumeroInscricao +
                ", cNome='" + cNome + '\'' +
                ", cDuracao='" + cDuracao + '\'' +
                ", cimgfirebase='" + cimgfirebase + '\'' +
                '}';
    }
}