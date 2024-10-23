package com.aula.appbimo.models;

public class Endereco {

    private int sid;


    private String cCep;


    private String cBairro;


    private int iNumero;


    private String cRua;


    private int idUsuario;

    private String cEstado;

    public Endereco(String cCep, String cBairro, int iNumero, String cRua, int idUsuario, String cEstado) {
        this.cCep = cCep;
        this.cBairro = cBairro;
        this.iNumero = iNumero;
        this.cRua = cRua;
        this.idUsuario = idUsuario;
        this.cEstado = cEstado;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "sid=" + sid +
                ", cCep='" + cCep + '\'' +
                ", cBairro='" + cBairro + '\'' +
                ", iNumero=" + iNumero +
                ", cRua='" + cRua + '\'' +
                ", idUsuario=" + idUsuario +
                ", cEstado='" + cEstado + '\'' +
                '}';
    }
}
