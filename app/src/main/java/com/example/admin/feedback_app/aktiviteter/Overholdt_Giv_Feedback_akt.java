package com.example.admin.feedback_app.aktiviteter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Feedback_frg;

public class Overholdt_Giv_Feedback_akt extends AppCompatActivity implements Feedback_frg.OnFragmentInteractionListener {

    private Fragment fragmentFeedback;

    private String TAG = "feedbackfragmentet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overholdt__giv__feedback);

        String datoString = getIntent().getStringExtra("MØDEID");
        String stedString = getIntent().getStringExtra("MØDEIDdel");

        fragmentFeedback = Feedback_frg.newInstance("spørgsmål 1 hvor gammel er du?");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_feedback_container, fragmentFeedback).addToBackStack(null).commit();

    }


    @Override
    public void onFragmentInteraction(int smiley, String tekstUddybning) {

        Log.d(TAG, "onFragmentInteraction: " + "modtaget i aktivitet " + smiley + " og tekst " + tekstUddybning);

        fragmentFeedback = Feedback_frg.newInstance("nyt spgrsmål");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_feedback_container, fragmentFeedback).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }




}
