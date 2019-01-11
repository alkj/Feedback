package com.example.admin.feedback_app;

import java.util.ArrayList;
import java.util.List;

public class PersonData {
    private static final PersonData ourInstance = new PersonData();

    public static PersonData getInstance() {
        return ourInstance;
    }

    Mødeholder mødeholder;
    ArrayList<Møde> møderne;

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

        for (Møde møde : møderne)
            if (!møde.isAfholdt())
                strings.add(møde.getNavn());

        return strings.toArray(new String[0]);
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

        for (Møde møde : møderne)
            if (!møde.isAfholdt())
                møder.add(møde);

        return møder;
    }

    public List<Møde> getAfholdteMøder() {
        List<Møde> møder = new ArrayList<>();

        for (Møde møde : møderne)
            if (møde.isAfholdt())
                møder.add(møde);

        return møder;
    }

    public void ryd(){
        mødeholder = null;
        møderne = null;
    }
}
