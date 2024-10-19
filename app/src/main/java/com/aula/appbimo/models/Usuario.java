package com.aula.appbimo.models;

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

    private String imgfirebase;

    private String username;

    public Usuario(String cnome, String csobrenome, String ccpf, String cemail, String ccnpj, String ctelefone, String ddatanascimento, String clinklinkedin, String cidhash, String cespecialidadeprofissional, int plano, String imgfirebase, String username) {
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
        this.username = username;
        this.imgfirebase = imgfirebase;
    }

    public Usuario(){

    }

    public Usuario(String cnome, String csobrenome, String ccpf, String cemail, String ctelefone, String ddatanascimento,  String cidhash, int plano, String imgfirebase, String username) {
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.idplano = plano;
        this.username = username;
        this.imgfirebase = imgfirebase;
    }
    public Usuario(int id, String cnome, String csobrenome, String ccpf, String cemail, String ctelefone, String ddatanascimento,  String cidhash, int plano, String imgfirebase, String username) {
        this.sid = id;
        this.cnome = cnome;
        this.csobrenome = csobrenome;
        this.ccpf = ccpf;
        this.cemail = cemail;
        this.ctelefone = ctelefone;
        this.ddatanascimento = ddatanascimento;
        this.cidhash = cidhash;
        this.idplano = plano;
        this.username = username;
        this.imgfirebase = imgfirebase;
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
        return imgfirebase;
    }

    public void setCimgfirebase(String cimgfirebase) {
        this.imgfirebase = cimgfirebase;
    }

    public String getCusername() {
        return username;
    }

    public void setCusername(String cusername) {
        this.username = cusername;
    }

    @Override
    public String toString() {
        return "{" +
                " sid='" + sid + '\'' +
                ",cnome='" + cnome + '\'' +
                ", csobrenome='" + csobrenome + '\'' +
                ", ccpf='" + ccpf + '\'' +
                ", cemail='" + cemail + '\'' +
                ", ctelefone='" + ctelefone + '\'' +
                ", ddatanascimento=" + ddatanascimento +
                ", cidhash='" + cidhash + '\'' +
                ", idplano=" + idplano +
                ", cimgfirebase=" + imgfirebase +
                ", cusername=" + username +
                '}';
    }
}
//"ccpf":"50248624814","cemail":"tavosouza888@gmail.com","cidhash":"QPcCSByhfmc2NRYQHEYJDFToWiM2","cnome":"Vicente","csobrenome":"Stramantino","ctelefone":"(11) 98824-8313","ddatanascimento":"16/09/2007","idplano":1,"imgfirebase":"https://firebasestorage.googleapis.com/v0/b/bimo-repo.appspot.com/o/galeria%2Fgaleria_1728349880544.jpg?alt\u003dmedia\u0026token\u003d1f9dd5cc-6ea8-4fe9-8a14-22c6ca19ff42","username":"VicenteStramantino."}

