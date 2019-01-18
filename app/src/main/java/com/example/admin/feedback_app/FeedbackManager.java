package com.example.admin.feedback_app;

import java.util.Arrays;

public class FeedbackManager {

    private static final FeedbackManager INSTANCE = new FeedbackManager();
    public static final String
            DEF_FEEDBACK = "Feedback: ",
            DEF_OPEN = "{ ",
            DEF_CLOSE = " }",
            DEF_NEXT = ", ",
            DEF_SMILEY = "smiley = ",
            DEF_TEKST = "tekst = ",
            DEF_SVAR = "Svar: ";

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

    /**
     * Statisk metode til at konvertere feedback'en til en streng
     * @param feedback Svar[] som indeholder de forskellige svar til spørgsmål
     * @return en String som representerer array'et ellers null hvis array'et er tomt
     */
    public static String feedbackTilString(Svar[] feedback){
        String s = null;

        if (feedback.length > 0){
            s = new String(DEF_FEEDBACK + DEF_OPEN);

            for (int i = 0; i < feedback.length; i++){
                if (i > 0)
                    s += DEF_NEXT;
                s += feedback[i].toString();
            }

            s += DEF_CLOSE;
        }

        return s;
    }

    /**
     * MEtode til at konvertere en streng til feedback
     * @param s String som representerer feedback
     * @return Svar[] som indenholder de forskellige svar på spørgsmål
     */
    public static Svar[] stringTilFeedback(String s){
        Svar[] feedback = null;

        if (s.length() > 0 && s.contains(DEF_FEEDBACK)){
            String[] strings = s
                    .replace(DEF_FEEDBACK, "")
                    .split(DEF_SVAR);

            feedback = new Svar[strings.length-1];

            for (int i = 0; i < feedback.length; i++){
                int j = i+1;
                Svar svar = new Svar();

                svar.setSmiley(
                        Integer.parseInt(
                                Character.toString(
                                        strings[j].charAt(
                                                strings[j].indexOf(DEF_SMILEY) + DEF_SMILEY.length()
                                        )
                                )
                        )
                );

                svar.setTekst(
                        strings[j].substring(
                                strings[j].indexOf(DEF_TEKST) + DEF_TEKST.length(),
                                strings[j].indexOf(DEF_CLOSE) - 1
                        )
                );

                feedback[i] = svar;
            }
        }

        return  feedback;
    }


}
