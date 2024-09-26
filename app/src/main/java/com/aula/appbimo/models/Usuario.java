package com.aula.appbimo.models;

public class Usuario {


    private String cnome;

    private String csobrenome;

    private String ccpf;


    private String cemail;


    private String ccnpj;


    private String ctelefone;


    private String ddatanascimento;


    private String clinklinkedin;



    private String cidhash;


    private String cespecialidadeprofissional;



    private int idplano;


    public Usuario(String cnome, String csobrenome, String ccpf, String cemail, String ccnpj, String ctelefone, String ddatanascimento, String clinklinkedin, String cidhash, String cespecialidadeprofissional, int plano) {
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ccnpj = ccnpj;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.clinklinkedin = clinklinkedin;
        this.cidhash = cidhash;
        this.cespecialidadeprofissional = cespecialidadeprofissional;
        this.idplano = plano;
    }

    public Usuario(String ccpf, String cemail, String ddatanascimento, String cidhash, int idplano) {
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.idplano = idplano;
    }

    public String getCnome() {
        return cnome;
    }

    public void setCnome(String cnome) {
        this.cnome = cnome;
    }

    public int getIdplano() {
        return idplano;
    }

    public void setIdplano(int idplano) {
        this.idplano = idplano;
    }

    public String getCsobrenome() {
        return csobrenome;
    }

    public void setCsobrenome(String csobrenome) {
        this.csobrenome = csobrenome;
    }


    public String getCcpf() {
        return ccpf;
    }

    public void setCcpf(String ccpf) {
        this.ccpf = ccpf;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCcnpj() {
        return ccnpj;
    }

    public void setCcnpj(String ccnpj) {
        this.ccnpj = ccnpj;
    }

    public String getCtelefone() {
        return ctelefone;
    }

    public void setCtelefone(String ctelefone) {
        this.ctelefone = ctelefone;
    }

    public String getDdatanascimento() {
        return String.valueOf(this.ddatanascimento);
    }

    public void setDdatanascimento(String ddatanascimento) {
        this.ddatanascimento = ddatanascimento;
    }

    public String getClinklinkedin() {
        return clinklinkedin;
    }

    public void setClinklinkedin(String clinklinkedin) {
        this.clinklinkedin = clinklinkedin;
    }

    public String getCidhash() {
        return cidhash;
    }

    public void setCidhash(String cidhash) {
        this.cidhash = cidhash;
    }

    public String getCespecialidadeprofissional() {
        return cespecialidadeprofissional;
    }

    public void setCespecialidadeprofissional(String cespecialidadeprofissional) {
        this.cespecialidadeprofissional = cespecialidadeprofissional;
    }


    @Override
    public String toString() {
        return "{" +
                "cnome='" + cnome + '\'' +
                ", csobrenome='" + csobrenome + '\'' +
                ", ccpf='" + ccpf + '\'' +
                ", cemail='" + cemail + '\'' +
                ", ccnpj='" + ccnpj + '\'' +
                ", ctelefone='" + ctelefone + '\'' +
                ", ddatanascimento=" + ddatanascimento +
                ", clinklinkedin='" + clinklinkedin + '\'' +
                ", cidhash='" + cidhash + '\'' +
                ", cespecialidadeprofissional='" + cespecialidadeprofissional + '\'' +
                ", idplano=" + idplano +
                '}';
    }
}

