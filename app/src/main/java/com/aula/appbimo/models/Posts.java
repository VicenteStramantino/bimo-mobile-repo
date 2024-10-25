package com.aula.appbimo.models;

public class Posts {
    private String id;
    private String cID;
    private int iIdUsuario;
    private String cTexto;
    private String cImgFirebase;
    private int cCurtidas;

    private boolean liked;

    public Posts(String id, String cID, int iIdUsuario, String cTexto, String cImgFirebase, int cCurtidas) {
        this.id = id;
        this.cID = cID;
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
    public Posts(String cID, int iIdUsuario, String cTexto, String cImgFirebase) {
        this.cID = cID;
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

    public String getsID() {
        return cID;
    }

    public void setsID(String cID) {
        this.cID = cID;
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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", sID=" + cID +
                ", iIdUsuario=" + iIdUsuario +
                ", cTexto='" + cTexto + '\'' +
                ", cImgFirebase='" + cImgFirebase + '\'' +
                ", cCurtidas=" + cCurtidas +
                '}';
    }
}
