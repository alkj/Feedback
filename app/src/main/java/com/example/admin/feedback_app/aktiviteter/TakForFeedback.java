package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.feedback_app.FeedbackManager;
import com.example.admin.feedback_app.NetworkManager;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;

import java.util.Timer;
import java.util.TimerTask;

public class TakForFeedback extends BaseActivity {

    Timer timer;
    private TextView tekst_besked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tak_for_feedback);
        tekst_besked = findViewById(R.id.tak_besked_txtView);

        if (NetworkManager.harInternet(this)){
            tekst_besked.setVisibility(View.INVISIBLE);
            showProgressDialog();
            updateProgressDialog("Fake loader");
        }
        else {
            tekst_besked.setText("No internet :O");
            //TODO: hvis ingen internet
        }



        timer = new Timer();
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
        }, 3500);

    }

    private void indsendFeedback(){
        FeedbackManager fm = FeedbackManager.getInstance();
        Svar[] feedback = fm.hentAltFeedback();

        //TODO send feedback
    }

}
