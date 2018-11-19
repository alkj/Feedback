package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OpretBruger_akt extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "opretBruger";

    private Button tilbage_btn, opret_btn;
    private EditText fornavn_editTxt, efternavn_editTxt, email_editTxt,
            tlfnr_editTxt, password_editTxt, password2_editTxt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

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

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validering()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(OpretBruger_akt.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    public boolean validering(){

        Log.d(TAG, "valideringEmail: startes");
        boolean valid = true;

        String fornavn = this.fornavn_editTxt.getText().toString();
        String efterNavn = this.efternavn_editTxt.getText().toString();
        String tlf = this.tlfnr_editTxt.getText().toString();
        String password = this.password_editTxt.getText().toString();
        String password2 = this.password2_editTxt.getText().toString();

        //TODO evt lav check om emailen er valid og er af formen xxx@xxx.com

        if(TextUtils.isEmpty(fornavn)){
            this.fornavn_editTxt.setError("Indtast fornavn");
            valid = false;
        }
        if (TextUtils.isEmpty(efterNavn)) {
            this.efternavn_editTxt.setError("Indtast efternavn");
            valid = false;
        }
        if (TextUtils.isEmpty(tlf)) {
            this.tlfnr_editTxt.setError("Indtast tlf nummer");
            valid = false;
        }
        if (TextUtils.isEmpty(tlf)) {
            this.password_editTxt.setError("Indtast password");
            valid = false;
        }
        if (TextUtils.isEmpty(tlf)) {
            this.password2_editTxt.setError("Indtast password");
            valid = false;
        }
        if (!password.equals(password2)) {
            Toast.makeText(OpretBruger_akt.this, "Dine passwords er ikke ens", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        Log.d(TAG, "valideringEmail: validering returneres");
        return valid;
    }

    public void updateUI(FirebaseUser user){
        if(user!=null){
            Intent myIntent = new Intent(OpretBruger_akt.this,Navigation_akt.class);
            OpretBruger_akt.this.startActivity(myIntent);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == tilbage_btn){
            //Luk og gå tilbage til login aktiviteten
            finish();
        }
        else if (view == opret_btn){
            createAccount(email_editTxt.getText().toString(),password_editTxt.getText().toString());
            //TODO: håndtere opret bruger
        }
    }
}
