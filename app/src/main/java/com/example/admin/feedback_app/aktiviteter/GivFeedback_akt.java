package com.example.admin.feedback_app.aktiviteter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.feedback_app.FeedbackTilFirebase;
import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragment_feedback_smiley;
import com.example.admin.feedback_app.uddyb_feedback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class GivFeedback_akt extends AppCompatActivity implements fragment_feedback_smiley.OnButtonClickedFeedbackSmiley, uddyb_feedback.uddyb_feed {

    private Fragment smiley;
    private Fragment uddyb;
    int humoer;
    int nummer;
    private FeedbackTilFirebase feedbackTilFirebase;
    int antalSpørgsmål;
    private FirebaseFirestore mFirestore;
    private PersonData personData;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giv_feedback);
        mFirestore = FirebaseFirestore.getInstance();
        personData = PersonData.getInstance();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        smiley = new fragment_feedback_smiley();

        nummer = 1;

        Bundle bundle = new Bundle();
        Log.d("debug feedback akt", "onCreateView: " + nummer);
        bundle.putInt("nummer", nummer);
        smiley.setArguments(bundle);

        uddyb = new uddyb_feedback();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentGivFeedback, smiley).commit();

        antalSpørgsmål = 4;

    }

    @Override
    public void submit(int i) {
        this.humoer = i;

        Bundle bundle = new Bundle();
        bundle.putInt("humoer", humoer);
        uddyb.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentGivFeedback, uddyb).commit();

    }


    @Override
    public int feedbackSendt(int i, String s) {
        bygFeedbackObjekt(i, s);

        if(nummer>=antalSpørgsmål){
            Log.d("debug feedback akt", "feedbackSendt: \n" + feedbackTilFirebase.toString());
            feedbackTilFirebase.setMødeId(personData.getFeedbackTilDetteMøde().getMødeID());
//            feedbackTilFirebase.sendFeedback("");
            mFirestore.collection("Feedback").document().set(feedbackTilFirebase);


            SharedPreferences.Editor editor = prefs.edit();
            Set<String> set = prefs.getStringSet("key", null);
            set.add(personData.getFeedbackTilDetteMøde().getMødeIDtildeltager());
            //Set<String> set = new HashSet<String>();
            //set.add(personData.getFeedbackTilDetteMøde().getMødeIDtildeltager());
            editor.putStringSet("key", set);

            editor.commit();
//Get the values
            Log.i("hej", "feedbackSendt: "+prefs.getStringSet("key", null));


            personData.afslutFeedback();
            finish();
        }

        Log.d("debug feedback akt", "feedbackSendt: " + i + s);



        nummer++;



        Bundle bundle = new Bundle();
        Log.d("debug feedback akt", "onCreateView: " + nummer);
        bundle.putInt("nummer", nummer);
        smiley.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentGivFeedback, smiley).commit();
        return nummer;
    }

    private void bygFeedbackObjekt(int i, String s){
        if(feedbackTilFirebase==null) {
            feedbackTilFirebase = new FeedbackTilFirebase("123");
        }

        switch (nummer) {
            case 1:
                feedbackTilFirebase.setIntSpørgsmål1(i);
                feedbackTilFirebase.setStringSpørgsmål1(s);
                break;
            case 2:
                feedbackTilFirebase.setIntSpørgsmål2(i);
                feedbackTilFirebase.setStringSpørgsmål2(s);
                break;
            case 3:
                feedbackTilFirebase.setIntSpørgsmål3(i);
                feedbackTilFirebase.setStringSpørgsmål3(s);
                break;
            case 4:
                feedbackTilFirebase.setIntSpørgsmål4(i);
                feedbackTilFirebase.setStringSpørgsmål4(s);
                break;
        }



    }

}