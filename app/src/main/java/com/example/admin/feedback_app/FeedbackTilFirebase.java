package com.example.admin.feedback_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.type.TimeOfDayOrBuilder;

public class FeedbackTilFirebase {

    private String mødeId;

    private int antalSpørgsmål = 4;

    private int intSpørgsmål1;
    private String stringSpørgsmål1;

    private int intSpørgsmål2;
    private String stringSpørgsmål2;

    private int intSpørgsmål3;
    private String stringSpørgsmål3;

    private int intSpørgsmål4;
    private String stringSpørgsmål4;




    public FeedbackTilFirebase(String mødeid) {
        this.mødeId = mødeid;
    }

    public void sendFeedback(FirebaseAuth firebaseAuth){
        //TODO
    }

    public String toString(){
        return "humør til spørgs 1 = " + intSpørgsmål1 + "\n" +
                "kommentar til spørgs 1 = " + stringSpørgsmål1 + "\n" +
                "humør til spørgs 2 = " + intSpørgsmål2 + "\n" +
                "kommentar til spørgs 2 = " + stringSpørgsmål2 + "\n" +
                "humør til spørgs 3 = " + intSpørgsmål3 + "\n" +
                "kommentar til spørgs 3 = " + stringSpørgsmål3 + "\n" +
                "humør til spørgs 4 = " + intSpørgsmål4 + "\n" +
                "kommentar til spørgs 4 = " + stringSpørgsmål4 + "\n";

    }

    public String getMødeId() {
        return mødeId;
    }

    public void setMødeId(String mødeId) {
        this.mødeId = mødeId;
    }

    public int getAntalSpørgsmål() {
        return antalSpørgsmål;
    }

    public void setAntalSpørgsmål(int antalSpørgsmål) {
        this.antalSpørgsmål = antalSpørgsmål;
    }

    public int getIntSpørgsmål1() {
        return intSpørgsmål1;
    }

    public void setIntSpørgsmål1(int intSpørgsmål1) {
        this.intSpørgsmål1 = intSpørgsmål1;
    }

    public String getStringSpørgsmål1() {
        return stringSpørgsmål1;
    }

    public void setStringSpørgsmål1(String stringSpørgsmål1) {
        this.stringSpørgsmål1 = stringSpørgsmål1;
    }

    public int getIntSpørgsmål2() {
        return intSpørgsmål2;
    }

    public void setIntSpørgsmål2(int intSpørgsmål2) {
        this.intSpørgsmål2 = intSpørgsmål2;
    }

    public String getStringSpørgsmål2() {
        return stringSpørgsmål2;
    }

    public void setStringSpørgsmål2(String stringSpørgsmål2) {
        this.stringSpørgsmål2 = stringSpørgsmål2;
    }

    public int getIntSpørgsmål3() {
        return intSpørgsmål3;
    }

    public void setIntSpørgsmål3(int intSpørgsmål3) {
        this.intSpørgsmål3 = intSpørgsmål3;
    }

    public String getStringSpørgsmål3() {
        return stringSpørgsmål3;
    }

    public void setStringSpørgsmål3(String stringSpørgsmål3) {
        this.stringSpørgsmål3 = stringSpørgsmål3;
    }

    public int getIntSpørgsmål4() {
        return intSpørgsmål4;
    }

    public void setIntSpørgsmål4(int intSpørgsmål4) {
        this.intSpørgsmål4 = intSpørgsmål4;
    }

    public String getStringSpørgsmål4() {
        return stringSpørgsmål4;
    }

    public void setStringSpørgsmål4(String stringSpørgsmål4) {
        this.stringSpørgsmål4 = stringSpørgsmål4;
    }
}
