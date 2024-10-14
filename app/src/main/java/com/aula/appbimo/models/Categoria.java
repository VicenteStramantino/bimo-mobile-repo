package com.aula.appbimo.models;

public class Categoria {
    private int sid;
    private String cNome;
    private String cTipo;
    private boolean bIsInactive;

    public Categoria(int sid, String cNome, String cTipo, boolean bIsInactive) {
        this.sid = sid;
        this.cNome = cNome;
        this.cTipo = cTipo;
        this.bIsInactive = bIsInactive;
    }

    public Categoria() {
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

    public String getcTipo() {
        return cTipo;
    }

    public void setcTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public boolean isbIsInactive() {
        return bIsInactive;
    }

    public void setbIsInactive(boolean bIsInactive) {
        this.bIsInactive = bIsInactive;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "  cNome='" + cNome + '\'' +
                ", cTipo='" + cTipo + '\'' +
                ", bIsInactive=" + bIsInactive +
                '}';
    }
}
