package com.example.admin.feedback_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_akt extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn, nyBruger_btn, tilbage_btn;
    private EditText brugernavn_editTxt, password_editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Knapper
        login_btn = (Button)findViewById(R.id.login_login_btn);
        nyBruger_btn = (Button)findViewById(R.id.login_nyBruger_btn);
        tilbage_btn = (Button)findViewById(R.id.login_tilbage_btn);

        login_btn.setOnClickListener(this);
        nyBruger_btn.setOnClickListener(this);
        tilbage_btn.setOnClickListener(this);

        //Input felter
        brugernavn_editTxt = (EditText)findViewById(R.id.login_brugernavn_editTxt);
        password_editTxt = (EditText)findViewById(R.id.login_password_editTxt);

    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            //TODO: håndtere login
        }
        else if (view == nyBruger_btn){
            //Åbner opret bruger aktiviteten
            Intent intent = new Intent(this, OpretBruger_akt.class);
            startActivity(intent);
        }
        else if (view == tilbage_btn){
            //Lukker aktiviteten og går derfor tilbage til den forrige
            finish();
        }
    }
}
