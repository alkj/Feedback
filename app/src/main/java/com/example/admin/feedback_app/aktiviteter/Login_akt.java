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

public class Login_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "login";

    private Button login_btn, nyBruger_btn, tilbage_btn;
    private EditText email_editTxt, password_editTxt;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Knapper
        login_btn = findViewById(R.id.login_login_btn);
        nyBruger_btn = findViewById(R.id.login_nyBruger_btn);
        tilbage_btn = findViewById(R.id.login_tilbage_btn);

        login_btn.setOnClickListener(this);
        nyBruger_btn.setOnClickListener(this);
        tilbage_btn.setOnClickListener(this);

        //Input felter
        email_editTxt = findViewById(R.id.login_brugernavn_editTxt);
        password_editTxt = findViewById(R.id.login_password_editTxt);

    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        //Hvis brugeren har tidligere været logget ind, går vi direkte videre.
        if (firebaseAuth.getCurrentUser() != null) næsteSide();
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            //Login
            login(email_editTxt.getText().toString(),password_editTxt.getText().toString());
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

    private void login(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validering(email, password)) {
            return;
        }

        //Vis loading hvis valideringen er okay, og forsøg at login
        showProgressDialog();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new LoginListener());
    }

    public boolean validering(String email, String password){
        //TODO: lav en valideringsklasse til email da det bruges flere steder

        Log.d(TAG, "valideringEmail: startes");
        boolean valid = true;

        //TODO evt lav check om emailen er valid og er af formen xxx@xxx.com

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            this.password_editTxt.setError("Indtast password");
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        }
        else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        }
        else if (TextUtils.isEmpty(password)&& !TextUtils.isEmpty(email)) {
            this.password_editTxt.setError("Indtast password");
            valid = false;
        }

        Log.d(TAG, "valideringEmail: validering returneres");
        return valid;
    }

    public void næsteSide(){
        //Giver lige en besked
        Toast.makeText(this,
                "Logget ind med: " + firebaseAuth.getCurrentUser().getEmail(),
                Toast.LENGTH_SHORT).show();

        //Skifter til næste aktivitet
        Intent myIntent = new Intent(Login_akt.this,Navigation_akt.class);
        Login_akt.this.startActivity(myIntent);
    }

    class LoginListener implements OnCompleteListener<AuthResult>{

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            //Stop loading efter firebase er færdig med at give svar
            hideProgressDialog();

            if (task.isSuccessful()){
                //Login
                Log.d(TAG, "signInWithEmail:success");
                næsteSide();
            }
            else {
                //Login fejlede!!
                Log.w(TAG, "signInWithEmail:failure", task.getException());

                //TODO: giv en bedre beskrivelse af årsagen til fejlen
                //TODO: custom Toast feks. rød til fejl
                Toast.makeText(getApplicationContext(),
                        "Login fejlede!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
