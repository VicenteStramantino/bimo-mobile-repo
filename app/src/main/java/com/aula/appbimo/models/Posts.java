package com.aula.appbimo.models;

public class Posts {
    private String id;
    private int sID;
    private int iIdUsuario;
    private String cTexto;
    private String cImgFirebase;
    private int cCurtidas;

    public Posts(String id, int sID, int iIdUsuario, String cTexto, String cImgFirebase, int cCurtidas) {
        this.id = id;
        this.sID = sID;
        this.iIdUsuario = iIdUsuario;
        this.cTexto = cTexto;
        this.cImgFirebase = cImgFirebase;
        this.cCurtidas = cCurtidas;
    }

    public Posts(int iIdUsuario, String cTexto, String cImgFirebase, int cCurtidas) {
        this.iIdUsuario = iIdUsuario;
        this.cTexto = cTexto;
        this.cImgFirebase = cImgFirebase;
        this.cCurtidas = cCurtidas;
    }
    public Posts(int sID, int iIdUsuario, String cTexto, String cImgFirebase) {
        this.sID = sID;
        this.iIdUsuario = iIdUsuario;
        this.cTexto = cTexto;
        this.cImgFirebase = cImgFirebase;
    }

    public Posts() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public int getiIdUsuario() {
        return iIdUsuario;
    }

    public void setiIdUsuario(int iIdUsuario) {
        this.iIdUsuario = iIdUsuario;
    }

    public String getcTexto() {
        return cTexto;
    }

    public void setcTexto(String cTexto) {
        this.cTexto = cTexto;
    }

    public String getcImgFirebase() {
        return cImgFirebase;
    }

    public void setcImgFirebase(String cImgFirebase) {
        this.cImgFirebase = cImgFirebase;
    }

    public int getcCurtidas() {
        return cCurtidas;
    }

    public void setcCurtidas(int cCurtidas) {
        this.cCurtidas = cCurtidas;
    }

    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", sID=" + sID +
                ", iIdUsuario=" + iIdUsuario +
                ", cTexto='" + cTexto + '\'' +
                ", cImgFirebase='" + cImgFirebase + '\'' +
                ", cCurtidas=" + cCurtidas +
                '}';
    }
}
