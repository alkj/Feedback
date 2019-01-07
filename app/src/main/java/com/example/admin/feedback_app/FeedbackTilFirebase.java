package com.example.admin.feedback_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.type.TimeOfDayOrBuilder;

public class FeedbackTilFirebase {

    private String mødeid;

    private int antalspørgsmål = 4;

    private int intspørgs1;
    private String stringspørgs1;

    private int intspørgs2;
    private String stringspørgs2;

    private int intspørgs3;
    private String stringspørgs3;

    private int intspørgs4;
    private String stringspørgs4;




    public FeedbackTilFirebase(String mødeid) {
        this.mødeid = mødeid;
    }

    public void sendFeedback(FirebaseAuth firebaseAuth){

        //TODO

    }

    public String getMødeid() {
        return mødeid;
    }

    public void setMødeid(String mødeid) {
        this.mødeid = mødeid;
    }

    public int getAntalspørgsmål() {
        return antalspørgsmål;
    }

    public void setAntalspørgsmål(int antalspørgsmål) {
        this.antalspørgsmål = antalspørgsmål;
    }

    public int getIntspørgs1() {
        return intspørgs1;
    }

    public void setIntspørgs1(int intspørgs1) {
        this.intspørgs1 = intspørgs1;
    }

    public String getStringspørgs1() {
        return stringspørgs1;
    }

    public void setStringspørgs1(String stringspørgs1) {
        this.stringspørgs1 = stringspørgs1;
    }

    public int getIntspørgs2() {
        return intspørgs2;
    }

    public void setIntspørgs2(int intspørgs2) {
        this.intspørgs2 = intspørgs2;
    }

    public String getStringspørgs2() {
        return stringspørgs2;
    }

    public void setStringspørgs2(String stringspørgs2) {
        this.stringspørgs2 = stringspørgs2;
    }

    public int getIntspørgs3() {
        return intspørgs3;
    }

    public void setIntspørgs3(int intspørgs3) {
        this.intspørgs3 = intspørgs3;
    }

    public String getStringspørgs3() {
        return stringspørgs3;
    }

    public void setStringspørgs3(String stringspørgs3) {
        this.stringspørgs3 = stringspørgs3;
    }

    public int getIntspørgs4() {
        return intspørgs4;
    }

    public void setIntspørgs4(int intspørgs4) {
        this.intspørgs4 = intspørgs4;
    }

    public String getStringspørgs4() {
        return stringspørgs4;
    }

    public void setStringspørgs4(String stringspørgs4) {
        this.stringspørgs4 = stringspørgs4;
    }
}
