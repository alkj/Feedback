package com.example.admin.feedback_app;

import android.util.Log;

public class Svar {
    private int smiley;
    private String tekst = "";
    private static String SMILEY = "smiley=", TEKST = "tekst=", KOMMA = ", ", NULL = "null";

    public static Svar getSvar(String s){
        Svar svar = null;
        if (s.contains(SMILEY) && s.contains(TEKST)){
            svar = new Svar();
            s = s.substring(1, s.length()-1);

            String[] sArray = s.split(KOMMA);

            for(String s1 : sArray){
                if (s1.contains(TEKST)) {
                    if (!s1.contains(NULL)){
                        //Log.d("TAG", "Var tekst: " + s1.replace(TEKST, ""));
                        svar.setTekst(s1.replace(TEKST, ""));
                    }
                }
                else {
                    //Log.d("TAG", "Var smiley: " + Integer.parseInt(s1.replace(SMILEY, "")));
                    svar.setSmiley(Integer.parseInt(s1.replace(SMILEY, "")));
                }
            }
        }

        //Log.d("TAG", svar.toString());
        return svar;
    }

    public int getSmiley() {
        return smiley;
    }

    public String getTekst() {
        return tekst;
    }

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    @Override
    public String toString() {
        return "{"+TEKST+tekst+KOMMA+SMILEY+smiley+"}";
    }
}
