package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.feedback_app.R;

public class StartSkaerm_akt extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn, feedback_btn;
    private EditText mødeId_editTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        //Knapper
        login_btn = (Button)findViewById(R.id.startskaerm_login_btn);
        feedback_btn = (Button)findViewById(R.id.startskaerm_feedback_btn);

        login_btn.setOnClickListener(this);
        feedback_btn.setOnClickListener(this);

        //Input felt
        mødeId_editTxt = (EditText)findViewById(R.id.startskærm_editTxt);
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            //Starter login aktiviteteten
            Intent intent = new Intent(this, Login_akt.class);
            startActivity(intent);
        }
        else if ( view == feedback_btn){
            //TODO: indlæs inputet fra editText'en og finde det tilhørende møde

            //Starter feedback aktiviteten
            Intent intent = new Intent(this, GivFeedback_akt.class);
            //intent.putExtra("MØDEID",mødeId_editTxt.getText());
            startActivity(intent);
        }
    }
}
