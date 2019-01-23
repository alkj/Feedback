package com.example.admin.feedback_app;

import java.util.Arrays;

public class FeedbackManager {

    private static final FeedbackManager INSTANCE = new FeedbackManager();
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
        feedback = null;
    }
}
