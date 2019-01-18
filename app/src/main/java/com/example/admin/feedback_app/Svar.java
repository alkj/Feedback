package com.example.admin.feedback_app;

public class Svar {
    private int smiley;
    private String tekst;

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
        return FeedbackManager.DEF_SVAR + FeedbackManager.DEF_OPEN + FeedbackManager.DEF_SMILEY + smiley + FeedbackManager.DEF_NEXT + FeedbackManager.DEF_TEKST + tekst + FeedbackManager.DEF_CLOSE;
    }
}
