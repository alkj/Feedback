package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "login";

    private Button login_btn, nyBruger_btn, tilbage_btn;
    private EditText email_editTxt, password_editTxt;
    private FirebaseAuth firebaseAuth;
    private PersonData personData;


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

        personData = PersonData.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) hentBrugerFraFire();
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn) {
            //Login
            login(email_editTxt.getText().toString(), password_editTxt.getText().toString());
        } else if (view == nyBruger_btn) {
            //Åbner opret bruger aktiviteten
            Intent intent = new Intent(this, OpretBruger_akt.class);
            startActivity(intent);
        } else if (view == tilbage_btn) {
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

    public boolean validering(String email, String password) {
        //TODO: lav en valideringsklasse til email da det bruges flere steder

        Log.d(TAG, "valideringEmail: startes");
        boolean valid = true;

        //TODO evt lav check om emailen er valid og er af formen xxx@xxx.com

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            this.password_editTxt.setError("Indtast password");
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        } else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            this.email_editTxt.setError("Indtast E-mail.");
            valid = false;
        } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            this.password_editTxt.setError("Indtast password");
            valid = false;
        }

        Log.d(TAG, "valideringEmail: validering returneres");
        return valid;
    }

    private void hentMøderFraFire() {
        showProgressDialog();
        FirebaseFirestore.getInstance().collection("Møder")
                .whereEqualTo("mødeholderID", firebaseAuth.getCurrentUser().getUid())
                .whereEqualTo("afholdt", false)
                .get()
                .addOnCompleteListener(new HentMøderListener());
    }

    private void hentBrugerFraFire() {
        showProgressDialog();
        Log.d(TAG, "Gået ind i metoden som henter bruger fra Firestore");
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("Mødeholder").document(firebaseAuth.getUid());
        docRef.get().addOnCompleteListener(new HentBrugerListener());
    }

    public void næsteSide() {
        //Stop loading efter firebase er færdig med at give svar
        hideProgressDialog();

        //Giver lige en besked
        Toast.makeText(this,
                "Logget ind med: " + firebaseAuth.getCurrentUser().getEmail(),
                Toast.LENGTH_SHORT).show();

        //Skifter til næste aktivitet
        Intent myIntent = new Intent(this, Navigation_akt.class);
        startActivity(myIntent);
    }

    class LoginListener implements OnCompleteListener<AuthResult> {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            hideProgressDialog();
            if (task.isSuccessful()) {
                //Login
                Log.d(TAG, "signInWithEmail:success");
                hentBrugerFraFire();

            } else {
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

    class HentBrugerListener implements OnCompleteListener<DocumentSnapshot> {

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            hideProgressDialog();
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "dokumentet eksitetrer, og tingene smides til logikken");
                    personData.setMødeholder(document.get("fornavn").toString(), document.get("efternavn").toString(),
                            document.get("email").toString(),
                            document.get("password").toString(),
                            document.get("tlf").toString(),
                            document.get("virk_id").toString()
                    );


                    Log.d(TAG, "Værdier fra logik : " + personData.getMødeholder().getFornavn());
                    //mødeholder.setFornavn(document.get("fornavn").toString());
                    //mødeholder.setEfternavn(document.get("efternavn").toString());
                    //mødeholder.setEmail(document.get("email").toString());
                    //mødeholder.setPassword(document.get("password").toString());
                    // mødeholder.setTlf(document.get("tlf").toString());
                    //  mødeholder.setVirk_id(document.get("virk_id").toString());


                    // Log.d(TAG, "onComplete: møder holder navn: "+Mødeholder.getFornavn());
                }

                Log.d(TAG, "FÆRDIG med hentBrugerFraFire");
                //Log.d(TAG, "Værdier fra logik : "+ logik.getMødeholder().getFornavn());

                hentMøderFraFire();
            }
        }
    }

    class HentMøderListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            hideProgressDialog();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Møde mødeObj = new Møde(
                            document.get("navn").toString(),
                            document.get("tid").toString(),
                            document.get("sted").toString(),
                            document.get("dato").toString(),
                            document.get("formål").toString(),
                            document.get("mødeholderID").toString());
                    personData.tilføjMøde(mødeObj);

                    Log.d(TAG, "navn fra firebase: " + document.get("navn").toString());
                    Log.d(TAG, "mødelistens navn: " + mødeObj.getNavn());
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }

                næsteSide();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

        }
    }

}
