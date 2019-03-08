package dk.spinoff.apps.feedback_app;

import java.util.ArrayList;
import java.util.List;

public class FeedbackTilFirebase {

    private String mødeId;
    private List<Svar> svar;

    public FeedbackTilFirebase(String mødeid) {
        this.mødeId = mødeid;
        svar = new ArrayList<>();
    }

    public List<Svar> getSvar() {
        return svar;
    }

    public void setSvar(List<Svar> svar) {
        this.svar = svar;
    }

    public String getMødeId() {
        return mødeId;
    }

    public void setMødeId(String mødeId) {
        this.mødeId = mødeId;
    }

}
