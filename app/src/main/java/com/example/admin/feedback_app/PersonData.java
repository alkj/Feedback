package com.example.admin.feedback_app;

import java.util.ArrayList;

public class PersonData {
    private static final PersonData ourInstance = new PersonData();

    public static PersonData getInstance() {
        return ourInstance;
    }

    Mødeholder mødeholder;
    ArrayList<Møde> møderne = new ArrayList<Møde>();
    Møde feedbackTilDetteMøde;

    private PersonData() {
    }

    public void setMødeholder(String fornavn, String efternavn, String email, String password, String virk_id, String tlf) {
        mødeholder = new Mødeholder(fornavn, efternavn, email, password, virk_id, tlf);
    }

    public Mødeholder getMødeholder() {
        return mødeholder;
    }

    public void tilføjMøde(Møde møde) {
        møderne.add(møde);
    }

    public ArrayList<Møde> getMøderne() {
        return møderne;
    }

    public void ClearMøder() {
        møderne.clear();
    }

    public String[] getIkkeAfholdteMøderAsStringArray() {
        ArrayList<String> strings = new ArrayList<>();

        for (Møde møde : møderne)
            if (!møde.getAfholdt())
                strings.add(møde.getNavn());

        return strings.toArray(new String[0]);
    }

    public String[] getAfholdteMøderAsStringArray() {
        ArrayList<String> strings = new ArrayList<>();

        for (Møde møde : møderne)
            if (møde.getAfholdt())
                strings.add(møde.getNavn());

        return strings.toArray(new String[0]);
    }

    public Møde getFeedbackTilDetteMøde() {
        return feedbackTilDetteMøde;
    }

    public void setFeedbackTilDetteMøde(Møde feedbackTilDetteMøde) {
        this.feedbackTilDetteMøde = feedbackTilDetteMøde;
    }

    public void afslutFeedback(){
        feedbackTilDetteMøde = null;
    }


}
