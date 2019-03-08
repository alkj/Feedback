package com.spinoff.apps.feedback_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonData {
    private static final PersonData ourInstance = new PersonData();

    public static PersonData getInstance() {
        return ourInstance;
    }

    Mødeholder mødeholder;
    ArrayList<Møde> møderne;
    Map<String, List<List<Svar>>> feedback;

    private PersonData() {
    }

    public void setMødeholder(String fornavn, String efternavn, String email, String password, String virk_id, String tlf) {
        mødeholder = new Mødeholder(fornavn, efternavn, email, password, virk_id, tlf);
    }

    public Mødeholder getMødeholder() {
        return mødeholder;
    }

    public void tilføjMøde(Møde møde) {
        if(møderne == null)
            møderne = new ArrayList<>();

        møderne.add(møde);
    }

    public void tilføjFeedback(String mødeid, List<List<Svar>> list){
        if (feedback == null)
            feedback = new HashMap<>();
        feedback.put(mødeid, list);
    }

    public List<List<Svar>> getFeedbackTilMøde(String mødeid){
        if (feedback == null){
            return null;
        }
        return feedback.get(mødeid);
    }

    public ArrayList<Møde> getMøderne() {
        return møderne;
    }

    public void ClearMøder() {
        møderne.clear();
    }

    //Skal nok ikke bruges længere
    @Deprecated
    public String[] getIkkeAfholdteMøderAsStringArray() {
        ArrayList<String> strings = new ArrayList<>();
        if (møderne.isEmpty()){

            return strings.toArray(new String[0]);

        }else {
            for (Møde møde : møderne)
                if (!møde.isAfholdt())
                    strings.add(møde.getNavn());

            return strings.toArray(new String[0]);
        }
    }

    //Skal nok ikke bruges længere
    @Deprecated
    public String[] getAfholdteMøderAsStringArray() {
        ArrayList<String> strings = new ArrayList<>();

        for (Møde møde : møderne)
            if (møde.isAfholdt())
                strings.add(møde.getNavn());

        return strings.toArray(new String[0]);
    }

    public List<Møde> getIkkeAfholdteMøder() {
        List<Møde> møder = new ArrayList<>();

        if (møderne == null){
            return møder;
        }
        else {

            for (Møde møde : møderne)
                if (!møde.isAfholdt())
                    møder.add(møde);

            return møder;
        }
    }

    public List<Møde> getAfholdteMøder() {
        List<Møde> møder = new ArrayList<>();

        if (møderne==null){
            return møder;
        }
        else {

            for (Møde møde : møderne)
                if (møde.isAfholdt())
                    møder.add(møde);

            return møder;
        }
    }



    public void sorterMøderne(){
        if (møderne != null ){
            ArrayList<Møde> nyeListe = new ArrayList<>();

            for (int i = 0; i < møderne.size(); i++) {
                Møde lavesteMøde = null;

                for (Møde møde : møderne) {
                    if (lavesteMøde == null) {
                        lavesteMøde = møde;
                    } else {
                        if (møde.getDatoDag() < lavesteMøde.getDatoDag()
                                && møde.getDatoMåned() <= lavesteMøde.getDatoMåned()
                                && møde.getDatoÅr() <= lavesteMøde.getDatoÅr()) {
                            lavesteMøde = møde;
                            break;
                        }
                    }
                }

                møderne.remove(lavesteMøde);
                nyeListe.add(lavesteMøde);
            }

            møderne = nyeListe;
        }
    }


    public void ryd(){
        mødeholder = null;
        møderne = null;
    }
    public void rydmøder(){
        møderne = null;
    }
}

