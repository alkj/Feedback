package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import dk.spinoff.apps.feedback_app.Mødeholder;
import dk.spinoff.apps.feedback_app.R;


public class OpretBruger_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */
    private static final String TAG = "opretBruger";

    private Button opret_btn;
    private EditText fornavn_editTxt, efternavn_editTxt, email_editTxt,
            tlfnr_editTxt, password_editTxt, password2_editTxt, virk_id_editTxt;

    private Mødeholder mødeholder;
    private FirebaseAuth firebaseAuth;
    private String detHentedeVirkID;
    private ImageView password1, password2;
    private Boolean passwordStatus = true, getPasswordStatus2 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Knapper
        opret_btn = findViewById(R.id.opretbruger_opret_btn);
        opret_btn.setOnClickListener(this);

        //Input felter
        fornavn_editTxt = findViewById(R.id.dato);
        efternavn_editTxt = findViewById(R.id.tidStart);
        tlfnr_editTxt = findViewById(R.id.tidSlut);
        virk_id_editTxt = findViewById(R.id.virk_id_editTxt);
        email_editTxt = findViewById(R.id.opretbruger_mail_editTxt);
        password_editTxt = findViewById(R.id.opretbruger_password_editTxt);
        password2_editTxt = findViewById(R.id.opretbruger_password2_editTxt);

        //ImageView
        password1 = findViewById(R.id.imViewPassword1Synlig);
        password2 = findViewById(R.id.imViewPassword2Synlig);
        password1.setOnClickListener(this);
        password2.setOnClickListener(this);


    }

    private void skabBrugerkonto(final String email, final String password) {
        Log.d(TAG, "skabBrugerkonto:" + email);
        if (!validering()) {
            hideProgressDialog();
            return;
        }

        hentVirksomhedsID();


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
        if (view == opret_btn) {
            //vis brugeren at der arbejdes
            showProgressDialog();

            skabBrugerkonto(email_editTxt.getText().toString(), password_editTxt.getText().toString());

            //TODO lav toast der giver besked hvis emailen allerede er oprettet
        }
        else if (view == password1) {
            if (passwordStatus) {
                password_editTxt.setTransformationMethod(null);
                passwordStatus = false;
                password1.setImageResource(R.drawable.ic_visibility_on_green_24dp);
                password_editTxt.setSelection(password_editTxt.length());
            }
            else {
                password_editTxt.setTransformationMethod(new PasswordTransformationMethod());
                passwordStatus = true;
                password1.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
                password_editTxt.setSelection(password_editTxt.length());
            }
        }
        else if (view == password2) {
            if (passwordStatus) {
                password2_editTxt.setTransformationMethod(null);
                passwordStatus = false;
                password2.setImageResource(R.drawable.ic_visibility_on_green_24dp);
                password2_editTxt.setSelection(password2_editTxt.length());
            }
            else {
                password2_editTxt.setTransformationMethod(new PasswordTransformationMethod());
                passwordStatus = true;
                password2.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
                password2_editTxt.setSelection(password2_editTxt.length());
            }
        }

    }


    private boolean tjekVirksomhedsID() {
        if (virk_id_editTxt.getText().toString().equals(detHentedeVirkID)) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Sørger for at hente virksomhedsID ned (hvis det findes)
     */
    private void hentVirksomhedsID() {
        updateProgressDialog("tjekker virksomheds-ID");

        FirebaseFirestore.getInstance().collection("Virksomheder")
                .whereEqualTo("VirksomhedsID", virk_id_editTxt.getText().toString())
                .get()
                .addOnCompleteListener(new hentVirkIDListener());
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
                                            "Verificering email sendt til: " + firebaseAuth.getCurrentUser().getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(context,
                                            "Verificering email fejlede",
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

    class hentVirkIDListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    detHentedeVirkID = document.get("VirksomhedsID").toString();
                }

                //Her tjekkes om ID'et er korrekt og brugeren kan så oprettes
                if(tjekVirksomhedsID()) {

                    mødeholder = new Mødeholder(
                            fornavn_editTxt.getText().toString(),
                            efternavn_editTxt.getText().toString(),
                            email_editTxt.getText().toString(),
                            password_editTxt.getText().toString(),
                            virk_id_editTxt.getText().toString(),
                            tlfnr_editTxt.getText().toString());

                    updateProgressDialog("Opretter bruger");

                    firebaseAuth.createUserWithEmailAndPassword(mødeholder.getEmail(), mødeholder.getPassword())
                            .addOnCompleteListener(new UserCreatedListener());
                }
                else {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Ugyldigt virksomhedsID", Toast.LENGTH_SHORT).show();

                }
            } else {
                Log.d("debug, det er lrt", "FEJL");


            }

        }
    }
}