package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.feedback_app.FeedbackManager;
import com.example.admin.feedback_app.FeedbackTilFirebase;
import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.NetworkManager;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class TakForFeedback extends BaseActivity {

    Timer timer;
    private TextView tekst_besked;
    private final String TAG = "TakForFeedback";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tak_for_feedback);
        tekst_besked = findViewById(R.id.tak_besked_txtView);

        if (NetworkManager.harInternet(this)){
            tekst_besked.setVisibility(View.INVISIBLE);
            showProgressDialog();
            indsendFeedback();
        }
        else {
            tekst_besked.setText("No internet :O");
            //TODO: hvis ingen internet
        }

        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hideProgressDialog();
                tekst_besked.post(new Runnable() {
                    @Override
                    public void run() {
                        tekst_besked.setVisibility(View.VISIBLE);
                    }
                });
            }
        },2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(TakForFeedback.this,StartSkaerm_akt.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 3500);*/

    }

    private void indsendFeedback(){
        updateProgressDialog("Indsender feedback");
        FeedbackManager fm = FeedbackManager.getInstance();
        FeedbackTilFirebase feedback = fm.hentFeedbackTilFire();
        fm.clear();

        FirebaseFirestore.getInstance()
                .collection("Feedback")
                .add(feedback)
                .addOnCompleteListener(new OprettetListener());
    }

    private void feedbackLykkedes(){
        Log.d(TAG, "Feedback er uploaded");
        tekst_besked.post(new Runnable() {
            @Override
            public void run() {
                tekst_besked.setVisibility(View.VISIBLE);
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(TakForFeedback.this,StartSkaerm_akt.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 2000);
    }

    class OprettetListener implements OnCompleteListener<DocumentReference> {

        @Override
        public void onComplete(@NonNull Task<DocumentReference> task) {
            hideProgressDialog();
            if(task.isSuccessful()){
                feedbackLykkedes();
            }
            else {
                Log.w(TAG, "Feedback blev ikke oprettet!", task.getException());
            }
        }
    }

}
