package com.example.admin.feedback_app;

public class Møde {

    private String navn;
    private String formål;
    private String dato;
    private String tid;
    private String sted;
    private boolean afholdt, igang;
    private String mødeholderID;
    private String mødeID;
    private String mødeIDtildeltager;



    public Møde(){

    }
    public Møde(String navn, String formål, String dato, String tid, String sted, String mødeholderID, boolean afholdt, boolean igang, String mødeID, String mødeIDtildeltager) {
        this.navn = navn;
        this.formål = formål;
        this.dato = dato;
        this.tid = tid;
        this.sted = sted;
        this.mødeholderID = mødeholderID;
        this.afholdt = afholdt;
        this.igang = igang;
        this.mødeID = mødeID;
        this.mødeIDtildeltager = mødeIDtildeltager;
    }

    public String getNavn() {
        return navn;
    }

    public String getFormål() {
        return formål;
    }

    public String getDato() {
        return dato;
    }

    public String getTid() {
        return tid;
    }

    public String getSted() {
        return sted;
    }

    public boolean getAfholdt() {
        return afholdt;
    }

    public boolean getIgang() {
        return igang;
    }

    public String getMødeholderID() {
        return mødeholderID;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setFormål(String formål) {
        this.formål = formål;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public void setAfholdt(boolean afholdt) {
        this.afholdt = afholdt;
    }
    public void setIgang(boolean igang) {
        this.igang= igang;
    }

    public void setMødeholderID(String mødeholderID) {
        this.mødeholderID = mødeholderID;
    }

    public String getMødeIDtildeltager() {
        return mødeIDtildeltager;
    }

    public void setMødeIDtildeltager(String mødeIDtildeltager) {
        this.mødeIDtildeltager = mødeIDtildeltager;
    }

    public String getMødeID() {
        return mødeID;
    }

    public void setMødeID(String mødeID) {
        this.mødeID = mødeID;
    }
}
