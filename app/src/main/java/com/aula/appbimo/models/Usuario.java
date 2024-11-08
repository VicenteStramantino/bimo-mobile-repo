package com.aula.appbimo.models;

import com.google.gson.Gson;

public class Usuario {
    public Integer sid;
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
    private String imgFirebase;
    private String cusername;

    public Usuario(String cnome, String csobrenome, String ccpf, String cemail, String ccnpj, String ctelefone, String ddatanascimento, String clinklinkedin, String cidhash, String cespecialidadeprofissional, int plano, String imgfirebase, String cusername) {
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
        this.cusername = cusername;
        this.imgFirebase = imgfirebase;
    }

    public Usuario(){

    }

    public Usuario(String cnome, String csobrenome, String ccpf, String cemail, String ctelefone, String ddatanascimento,  String cidhash, int plano, String imgfirebase, String cusername) {
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.idplano = plano;
        this.cusername = cusername;
        this.imgFirebase = imgfirebase;
    }
    public Usuario(int id, String cnome, String csobrenome, String ccpf, String cemail, String ctelefone, String ddatanascimento,  String cidhash, int plano, String imgfirebase, String cusername) {
        this.sid = id;
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.idplano = plano;
        this.cusername = cusername;
        this.imgFirebase = imgfirebase;
    }


    public Integer getId() {
        return sid;
    }

    public void setId(int id) {
        this.sid = id;
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

    public String getCimgfirebase() {
        return imgFirebase;
    }

    public void setCimgfirebase(String cimgfirebase) {
        this.imgFirebase = cimgfirebase;
    }

    public String getcusername() {
        return cusername;
    }

    public void setcusername(String ccusername) {
        this.cusername = ccusername;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "\"sid\":" + sid + ",\n" +
                "\"cnome\":'" + cnome + "',\n" +
                "\"csobrenome\":'" + csobrenome + "',\n" +
                "\"ccpf\":'" + ccpf + "',\n" +
                "\"cemail\":'" + cemail + "',\n" +
                "\"ctelefone\":'" + ctelefone + "',\n" +
                "\"ddatanascimento\":'" + ddatanascimento + "',\n" +
                "\"cidhash\":'" + cidhash + "',\n" +
                "\"idplano\":" + idplano + ",\n" +
                "\"imgFirebase\":'" + imgFirebase + "',\n" +
                "\"cusername\":'" + cusername + "'\n" +
                '}';
    }

}
//"ccpf":"50248624814","cemail":"tavosouza888@gmail.com","cidhash":"QPcCSByhfmc2NRYQHEYJDFToWiM2","cnome":"Vicente","csobrenome":"Stramantino","ctelefone":"(11) 98824-8313","ddatanascimento":"16/09/2007","idplano":1,"imgfirebase":"https://firebasestorage.googleapis.com/v0/b/bimo-repo.appspot.com/o/galeria%2Fgaleria_1728349880544.jpg?alt\u003dmedia\u0026token\u003d1f9dd5cc-6ea8-4fe9-8a14-22c6ca19ff42","cusername":"VicenteStramantino."}

