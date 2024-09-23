package com.aula.appbimo.models;

public class Usuario {

    private int sid;

    private String cnome;

    private String csobrenome;

    private String csenha;

    private String ccpf;


    private String cemail;


    private String ccnpj;


    private String ctelefone;


    private String ddatanascimento;


    private String clinklinkedin;



    private String cidhash;


    private String cespecialidadeprofissional;


    private boolean transaction_made;


    public Usuario(int sid, String cnome, String csobrenome, String csenha, String ccpf, String cemail, String ccnpj, String ctelefone, String ddatanascimento, String clinklinkedin, String cidhash, String cespecialidadeprofissional, boolean transaction_made) {
        this.sid = sid;
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.csenha = csenha;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ccnpj = ccnpj;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.cespecialidadeprofissional = cespecialidadeprofissional;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getCnome() {
        return cnome;
    }

    public void setCnome(String cnome) {
        this.cnome = cnome;
    }

    public String getCsobrenome() {
        return csobrenome;
    }

    public void setCsobrenome(String csobrenome) {
        this.csobrenome = csobrenome;
    }

    public String getCsenha() {
        return csenha;
    }

    public void setCsenha(String csenha) {
        this.csenha = csenha;
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

    public boolean isTransaction_made() {
        return transaction_made;
    }

    public void setTransaction_made(boolean transaction_made) {
        this.transaction_made = transaction_made;
    }

    @Override
    public String toString() {
        return "{" +
                "sid=" + sid +
                ", cnome='" + cnome + '\'' +
                ", csobrenome='" + csobrenome + '\'' +
                ", csenha='" + csenha + '\'' +
                ", ccpf='" + ccpf + '\'' +
                ", cemail='" + cemail + '\'' +
                ", ccnpj='" + ccnpj + '\'' +
                ", ctelefone='" + ctelefone + '\'' +
                ", ddatanascimento=" + ddatanascimento +
                ", clinklinkedin='" + clinklinkedin + '\'' +
                ", cidhash='" + cidhash + '\'' +
                ", cespecialidadeprofissional='" + cespecialidadeprofissional + '\'' +
                ", transaction_made=" + transaction_made +
                '}';
    }
}

