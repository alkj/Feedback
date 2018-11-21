package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OpretBruger_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "opretBruger";

    private Button tilbage_btn, opret_btn;
    private EditText fornavn_editTxt, efternavn_editTxt, email_editTxt,
            tlfnr_editTxt, password_editTxt, password2_editTxt, virk_id_editTxt;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private mødeholder mødeholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        //Knapper
        tilbage_btn = findViewById(R.id.opretbruger_tilbage_btn);
        opret_btn = findViewById(R.id.opretbruger_opret_btn);

        tilbage_btn.setOnClickListener(this);
        opret_btn.setOnClickListener(this);

        //Input felter
        fornavn_editTxt = findViewById(R.id.opretbruger_fornavn_editTxt);
        efternavn_editTxt = findViewById(R.id.opretbruger_efternavn_editTxt);
        tlfnr_editTxt = findViewById(R.id.oprebruger_tlf_editTxt);
        virk_id_editTxt = findViewById(R.id.virk_id_editTxt);
        email_editTxt = findViewById(R.id.opretbruger_mail_editTxt);
        password_editTxt = findViewById(R.id.opretbruger_password_editTxt);
        password2_editTxt = findViewById(R.id.opretbruger_password2_editTxt);


    }

    private void createAccount(final String email, final String password) {
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

                            mødeholder = new mødeholder(
                                    fornavn_editTxt.getText().toString(),
                                    efternavn_editTxt.getText().toString(),
                                    email,
                                    password,
                                    virk_id_editTxt.getText().toString(),
                                    tlfnr_editTxt.getText().toString()
                            );

                            mFirestore.collection("mødeholder").document(mAuth.getUid()).set(mødeholder);



                            //indsætter data i firestore
                            /**
                            try{
                                Map<String, Object> muser = new HashMap<>();
                                muser.put("id",mAuth.getCurrentUser().getUid());
                                muser.put("fornavn", fornavn_editTxt.getText().toString());
                                muser.put("efternavn", efternavn_editTxt.getText().toString());
                                muser.put("tlf",tlfnr_editTxt.getText().toString());
                                muser.put("email",email);
                                muser.put("password",password);

                                mFirestore.collection("mødeholder").add(muser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "onSuccess: id på firestore "+documentReference.getId());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFalure: feeeejl ");
                                    }
                                });

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                             */



                            sendEmailVerification();
                            updateUI(user);
                        } else {



                            //TODO lav check om emailen allerede er i firebase og giv korrekt fejlmeddl.



                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(OpretBruger_akt.this, "Fejl.",
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
        String email = this.email_editTxt.getText().toString();
        String password = this.password_editTxt.getText().toString();
        String password2 = this.password2_editTxt.getText().toString();
        String virkId = this.virk_id_editTxt.getText().toString();

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
        if (TextUtils.isEmpty(virkId)) {
            this.virk_id_editTxt.setError("Indtast virksomheds ID");
            valid = false;
        }
        if (!isValidEmailAddress(email)) {
            this.email_editTxt.setError("Indtast en valid email");
            valid = false;
        }
        if (TextUtils.isEmpty(email)) {
            this.email_editTxt.setError("Indtast Email");
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
        return valid;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void updateUI(FirebaseUser user){
        if(user!=null){
            Intent myIntent = new Intent(OpretBruger_akt.this,Navigation_akt.class);
            OpretBruger_akt.this.startActivity(myIntent);
        }

    }

    private void sendEmailVerification() {
        // Disable button

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]

                        if (task.isSuccessful()) {
                            Toast.makeText(OpretBruger_akt.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(OpretBruger_akt.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    @Override
    public void onClick(View view) {
        if (view == tilbage_btn){
            //Luk og gå tilbage til login aktiviteten
            finish();
        }
        else if (view == opret_btn){

            createAccount(email_editTxt.getText().toString(),password_editTxt.getText().toString());

            //TODO lav toast der giver besked hvis emailen allerede er oprettet
        }
    }


}
