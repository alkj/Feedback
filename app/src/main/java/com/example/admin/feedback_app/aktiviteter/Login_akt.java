package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.FireBase.FBOnLoginListener;
import com.example.admin.feedback_app.FireBase.FirebaseLogik;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_akt extends BaseActivity implements View.OnClickListener, FBOnLoginListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "login";

    private Button login_btn, nyBruger_btn, tilbage_btn;
    private EditText email_editTxt, password_editTxt;
    private FirebaseAuth mAuth;
    private FirebaseLogik firebase;


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
        email_editTxt = (EditText)findViewById(R.id.login_brugernavn_editTxt);
        password_editTxt = (EditText)findViewById(R.id.login_password_editTxt);

    }

    @Override
    public void onStart() {
        super.onStart();

        firebase = new FirebaseLogik();
        firebase.setOnLoginListener(this);
        firebase.login(this, null, null);
        /* Checker om user er logget ind (not null) og opdaterer gui derefter
        FirebaseUser currentUser = mAuth.getCurrentUser();
        guiLogind(currentUser);*/
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            signIn(email_editTxt.getText().toString(),password_editTxt.getText().toString());
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

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validering()) {
            return;
        }

        //Vis loading hvis valideringen er okay, og check herefter om email/password er korrekt
        showProgressDialog();

        firebase.login(this,email, password);

        //Stop loading efter firebase er færdig med at give svar
        hideProgressDialog();
    }

    public boolean validering(){

        Log.d(TAG, "valideringEmail: startes");
        boolean valid = true;

        String email = this.email_editTxt.getText().toString();
        String password = this.password_editTxt.getText().toString();

        //TODO evt lav check om emailen er valid og er af formen xxx@xxx.com

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            this.password_editTxt.setError("Indtast password");
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        }
        if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        }
        if (TextUtils.isEmpty(password)&& !TextUtils.isEmpty(email)) {
            this.password_editTxt.setError("Indtast password");
            valid = false;
        }

        Log.d(TAG, "valideringEmail: validering returneres");
        return valid;


    }

    @Override
    public void onLogin(FirebaseUser user) {
        if(user != null){
            Intent myIntent = new Intent(Login_akt.this,Navigation_akt.class);
            Login_akt.this.startActivity(myIntent);
        }
    }
}
