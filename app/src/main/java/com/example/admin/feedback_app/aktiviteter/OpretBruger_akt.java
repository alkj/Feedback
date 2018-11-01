package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.feedback_app.R;

public class OpretBruger_akt extends AppCompatActivity implements View.OnClickListener {

    Button tilbage_btn, opret_btn;
    EditText fornavn_editTxt, efternavn_editTxt, email_editTxt,
            tlfnr_editTxt, password_editTxt, password2_editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        //Knapper
        tilbage_btn = findViewById(R.id.opretbruger_tilbage_btn);
        opret_btn = findViewById(R.id.opretbruger_opret_btn);

        tilbage_btn.setOnClickListener(this);
        opret_btn.setOnClickListener(this);

        //Input felter
        fornavn_editTxt = findViewById(R.id.opretbruger_fornavn_editTxt);
        efternavn_editTxt = findViewById(R.id.opretbruger_efternavn_editTxt);
        email_editTxt = findViewById(R.id.opretbruger_mail_editTxt);
        tlfnr_editTxt = findViewById(R.id.oprebruger_tlf_editTxt);
        password_editTxt = findViewById(R.id.opretbruger_password_editTxt);
        password2_editTxt = findViewById(R.id.opretbruger_password2_editTxt);

    }

    @Override
    public void onClick(View view) {
        if (view == tilbage_btn){
            //Luk og gå tilbage til login aktiviteten
            finish();
        }
        else if (view == opret_btn){
            //TODO: håndtere opret bruger
        }
    }
}
