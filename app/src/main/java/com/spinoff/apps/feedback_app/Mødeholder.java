package com.spinoff.apps.feedback_app;

public class Mødeholder {

    private String fornavn, efternavn, email, password, virk_id, tlf;

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVirk_id(String virk_id) {
        this.virk_id = virk_id;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getFornavn() {

        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getVirk_id() {
        return virk_id;
    }

    public String getTlf() {
        return tlf;
    }

    public Mødeholder(String fornavn, String efternavn, String email, String password, String virk_id, String tlf) {

        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.password = password;
        this.virk_id = virk_id;
        this.tlf = tlf;
    }
}
