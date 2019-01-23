package com.example.admin.feedback_app;

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

            if (!sArray[0].contains(NULL))
                svar.setTekst(sArray[0].replace(TEKST, ""));
            svar.setSmiley(Integer.parseInt(sArray[1].replace(SMILEY, "")));
        }

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
        return "{"+TEKST+tekst+KOMMA+smiley+"}";
    }
}
