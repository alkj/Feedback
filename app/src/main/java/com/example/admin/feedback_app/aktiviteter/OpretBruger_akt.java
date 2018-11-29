package com.example.admin.feedback_app.aktiviteter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class OpretBruger_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "opretBruger";

    private Button tilbage_btn, opret_btn;
    private EditText fornavn_editTxt, efternavn_editTxt, email_editTxt,
            tlfnr_editTxt, password_editTxt, password2_editTxt, virk_id_editTxt;

    private Mødeholder mødeholder;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Knapper
        tilbage_btn = findViewById(R.id.opretbruger_tilbage_btn);
        opret_btn = findViewById(R.id.opretbruger_opret_btn);

        tilbage_btn.setOnClickListener(this);
        opret_btn.setOnClickListener(this);

        //Input felter
        fornavn_editTxt = findViewById(R.id.opretbruger_fornavn_editTxt);
        efternavn_editTxt = findViewById(R.id.opretbruger_efternavn_editTxt);
        tlfnr_editTxt = findViewById(R.id.oprebrhgfguger_tlf_editpikTxt);
        virk_id_editTxt = findViewById(R.id.virk_id_editTxt);
        email_editTxt = findViewById(R.id.opretbruger_mail_editTxt);
        password_editTxt = findViewById(R.id.opretbruger_password_editTxt);
        password2_editTxt = findViewById(R.id.opretbruger_password2_editTxt);


    }

    private void skabBrugerkonto(final String email, final String password) {
        Log.d(TAG, "skabBrugerkonto:" + email);
        if (!validering()) {
            return;
        }

        mødeholder = new Mødeholder(
                fornavn_editTxt.getText().toString(),
                efternavn_editTxt.getText().toString(),
                email_editTxt.getText().toString(),
                password_editTxt.getText().toString(),
                virk_id_editTxt.getText().toString(),
                tlfnr_editTxt.getText().toString());

        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(mødeholder.getEmail(), mødeholder.getPassword())
                .addOnCompleteListener(new UserCreatedListener());
    }


    public boolean validering() {

        Log.d(TAG, "valideringEmail: startes");
        boolean valid = true;

        String fornavn = this.fornavn_editTxt.getText().toString();
        String efterNavn = this.efternavn_editTxt.getText().toString();
        String tlf = this.tlfnr_editTxt.getText().toString();
        String email = this.email_editTxt.getText().toString();
        String password = this.password_editTxt.getText().toString();
        String password2 = this.password2_editTxt.getText().toString();
        String virkId = this.virk_id_editTxt.getText().toString();

        if (TextUtils.isEmpty(fornavn)) {
            this.fornavn_editTxt.setError("Indtast fornavn");
            valid = false;
        }
        if (TextUtils.isEmpty(efterNavn)) {
            this.efternavn_editTxt.setError("Indtast efternavn");
            valid = false;
        }
        if (!isValidTlf(tlf) || TextUtils.isEmpty(tlf)) {
            this.tlfnr_editTxt.setError("Indtast et 8 cifret tlf. nummer");
            valid = false;
        }
        if (TextUtils.isEmpty(virkId)) {
            this.virk_id_editTxt.setError("Indtast virksomheds ID");
            valid = false;
        }
        if (!isValidEmailAddress(email) || TextUtils.isEmpty(email)) {
            this.email_editTxt.setError("Indtast en valid email");
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            this.password_editTxt.setError("Indtast password");
            valid = false;
        }
        if (TextUtils.isEmpty(password2)) {
            this.password2_editTxt.setError("Indtast password");
            valid = false;
        }
        if (!(password_editTxt.getText().toString().equals(password2_editTxt.getText().toString()))) {
            Toast.makeText(OpretBruger_akt.this, "Dine passwords er ikke ens", Toast.LENGTH_SHORT).show();
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

    public boolean isValidTlf(String tlf) {
        boolean valid = false;
        int længde = tlf.length();
        if (længde == 8) {
            valid = true;
        }
        return valid;
    }

    private void sendEmailVeri() {

    }

    public void brugerOprettet() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == tilbage_btn) {
            //Luk og gå tilbage til login aktiviteten
            finish();
        } else if (view == opret_btn) {
            skabBrugerkonto(email_editTxt.getText().toString(), password_editTxt.getText().toString());

            //TODO lav toast der giver besked hvis emailen allerede er oprettet
        }
    }


    class UserCreatedListener implements OnCompleteListener<AuthResult> {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            final Context context = getApplicationContext();

            if (task.isSuccessful()) {

                //sætter data over på firebase
                FirebaseFirestore.getInstance()
                        .collection("Mødeholder")
                        .document(firebaseAuth.getUid()).set(mødeholder);

                //sender verificering mail
                firebaseAuth.getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(context,
                                            "Verification email sent to " + firebaseAuth.getCurrentUser().getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(context,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                //Brugeren blev oprettet
                brugerOprettet();

            } else
                Toast.makeText(context, "Fejl", Toast.LENGTH_SHORT).show();

            hideProgressDialog();

        }
    }
}