package com.example.admin.feedback_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedbackManager {

    private static final FeedbackManager INSTANCE = new FeedbackManager();
    public static final String
            DEF_CLOSE = "\\}",
            DEF_SMILEY = "smiley=",
            DEF_TEKST = "tekst=";

    private Svar[] feedback;
    private String mødeId;
    private int count;

    private FeedbackManager(){}

    public static FeedbackManager getInstance() {
        return INSTANCE;
    }

    public void startFeedback(int antalSpørgsmål, String mødeId){
        feedback = new Svar[antalSpørgsmål];

        for (int i = 0; i < antalSpørgsmål; i++) {
            feedback[i] = new Svar();
        }

        count = antalSpørgsmål;

        this.mødeId = mødeId;
    }

    public void indsætFeedback(int i, Svar svar){
        if (i < count){
            feedback[i] = svar;
        }
    }

    public Svar hentFeedback(int i){
        if (i < count){
            return feedback[i];
        }

        return null;
    }

    public Svar[] hentAltFeedback(){
        return feedback;
    }

    public FeedbackTilFirebase hentFeedbackTilFire() {
        FeedbackTilFirebase feedbackTilFirebase = new FeedbackTilFirebase(mødeId);
        feedbackTilFirebase.setSvar(Arrays.asList(feedback));
        return feedbackTilFirebase;
    }

    public boolean erFeedbackUdfyldt(int i){
        return i < count && feedback[i].getSmiley() > 0;
    }

    public void clear(){
        feedback = null;
        mødeId = null;
    }

    public static List<Svar> stringTilFeedback(String s){
            String[] strings = s.split(DEF_SMILEY);

            List<Svar> feedback = new ArrayList<>();

            for (int i = 1;i<strings.length;i++){
                Svar svar = new Svar();

                if (strings[i].contains(DEF_TEKST)){
                    svar.setSmiley(Integer.parseInt(
                            Character.toString(
                                    strings[i].charAt(0)
                            )
                    ));

                    String t = strings[i].substring(
                            strings[i].indexOf(DEF_TEKST) + DEF_TEKST.length()
                    ).split(DEF_CLOSE)[0];

                    if (t.equals("null")){
                        t = new String();
                    }

                    svar.setTekst(t);

                    feedback.add(svar);
                }
            }

            return feedback;
    }


}
