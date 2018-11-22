package com.example.admin.feedback_app.aktiviteter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragment_feedback_smiley;
import com.example.admin.feedback_app.uddyb_feedback;

public class GivFeedback_akt extends AppCompatActivity implements fragment_feedback_smiley.OnButtonClickedFeedbackSmiley, uddyb_feedback.uddyb_feed {

    private Fragment smiley;
    private Fragment uddyb;
    int humoer;
    int nummer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giv_feedback);

        smiley = new fragment_feedback_smiley();
        uddyb = new uddyb_feedback();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentGivFeedback, smiley).commit();

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
    public int feedbackSendt(int i) {
        if(i>=4){
            finish();
        }
        this.nummer = ++i;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentGivFeedback, smiley).commit();
        return nummer;
    }

}