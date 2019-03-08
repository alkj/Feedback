package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import dk.spinoff.apps.feedback_app.Møde;
import dk.spinoff.apps.feedback_app.PersonData;
import com.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.Svar;
import dk.spinoff.apps.feedback_app.VibratorManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Login_akt extends BaseActivity implements View.OnClickListener {

    /**
     * Inspiration fået fra firebase's egen hjemmeside: https://firebase.google.com/docs/auth/android/password-auth
     */

    private static final String TAG = "login";

    private Button login_btn, nyBruger_btn;
    private CheckBox test_checkBox;
    private EditText email_editTxt, password_editTxt;
    private FirebaseAuth firebaseAuth;
    private PersonData personData;
    private int taskCount = 0;

    private final boolean DEBUG = true;
    private final String TEST_EMAIL = "nicolaidam96@gmail.com", TEST_PASSWORD = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Knapper
        login_btn = findViewById(R.id.login_login_btn);
        nyBruger_btn = findViewById(R.id.login_nyBruger_btn);
        test_checkBox = findViewById(R.id.login_checkBox);

        login_btn.setOnClickListener(this);
        nyBruger_btn.setOnClickListener(this);
        test_checkBox.setOnClickListener(this);

        //Input felter
        email_editTxt = findViewById(R.id.login_brugernavn_editTxt);
        password_editTxt = findViewById(R.id.login_password_editTxt);

        if (DEBUG) {
            test_checkBox.setVisibility(View.VISIBLE);

        }

        }

    @Override
    public void onStart() {
        super.onStart();

        personData = PersonData.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
            showProgressDialog();
            hentBrugerFraFire();
        }
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
        } else if (view == test_checkBox) {
            if (test_checkBox.isChecked()){
                email_editTxt.setText(TEST_EMAIL);
                email_editTxt.setInputType(InputType.TYPE_NULL);
                password_editTxt.setText(TEST_PASSWORD);
                password_editTxt.setInputType(InputType.TYPE_NULL);
            }
            else {
                email_editTxt.setText("");
                email_editTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                password_editTxt.setText("");
                password_editTxt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        }
    }

    private void login(String email, String password) {

        if (!validering(email, password)) {
            VibratorManager.vibrerMønster(this,VibratorManager.FEJL_VIB,-1);
            return;
        }

        //Vis loading hvis valideringen er okay, og forsøg at login
        showProgressDialog();
        updateProgressDialog("Logger ind");
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new LoginListener());
    }

    private boolean validering(String email, String password) {
        //TODO: lav en valideringsklasse til email da det bruges flere steder
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
        return valid;
    }

    /*
    Metode der henter alle brugerens møder fra firebase, og som så tilføjer en completeListener. Som så
    Smider det videre til at hente mødets feedback hvis det er afholdt
     */
    private void hentMøderFraFire() {
        updateProgressDialog("Henter møderne");
        FirebaseFirestore.getInstance().collection("Møder")
                .whereEqualTo("mødeholderID", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new HentMøderListener());
    }
    /*
    Henter brugeren fra firebase, og når det er gjort sender den automatisk videre til hent møder
     */
    private void hentBrugerFraFire() {
        updateProgressDialog("Henter bruger oplysninger");
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("Mødeholder").document(firebaseAuth.getUid());
        docRef.get().addOnCompleteListener(new HentBrugerListener());
    }


    /*
    Sørger for at sende det videre til næste side/aktivitet når alt er klaret
     */
    private void næsteSide() {
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
            if (task.isSuccessful()) {
                //Login
                if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                    hentBrugerFraFire();
                }
                else {

                    Toast.makeText(getApplicationContext(),
                            "Email er ikke verificeret",
                            Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }
            else {

                //TODO: giv en bedre beskrivelse af årsagen til fejlen
                //TODO: custom Toast feks. rød til fejl
                Toast.makeText(getApplicationContext(),
                        "Login fejlede",
                        Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        }
    }

    class HentBrugerListener implements OnCompleteListener<DocumentSnapshot> {

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    personData.setMødeholder(document.get("fornavn").toString(), document.get("efternavn").toString(),
                            document.get("email").toString(),
                            document.get("password").toString(),
                            document.get("virk_id").toString(),
                            document.get("tlf").toString()
                    );

                }

                hentMøderFraFire();
            }
        }
    }

    class HentMøderListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()){
                    næsteSide();
                    return;
                }
                taskCount += task.getResult().size();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Møde mødeObj = new Møde(
                            document.get("navn").toString(),
                            document.get("formål").toString(),
                            document.get("dato").toString(),
                            document.get("startTid").toString(),
                            document.get("slutTid").toString(),
                            document.get("sted").toString(),
                            document.get("mødeholderID").toString(),
                            document.getBoolean("afholdt")

                    );
                    mødeObj.setIgang(document.getBoolean("igang"));
                    mødeObj.setMødeIDtildeltager(document.get("mødeIDtildeltager").toString());
                    mødeObj.setMødeID(document.get("mødeID").toString());

                    try {
                        mødeObj.setFaktiskStartTid(document.get("faktiskStartTid").toString());
                    } catch (Exception e){
                        mødeObj.setFaktiskStartTid("ikke startet");
                        Log.d(TAG, "onComplete: info ikke gemt i firebase");
                    }


                    personData.tilføjMøde(mødeObj);

                    if (mødeObj.isAfholdt()) { //TODO: fejl
                        FirebaseFirestore.getInstance()
                                .collection("Feedback")
                                .whereEqualTo("mødeId", mødeObj.getMødeID())
                                .get().addOnCompleteListener(new HentFeedbackListener(mødeObj.getMødeID()));
                    }

                    taskCount--;
                }

                //Sorterer møderne så de ligger i sorteret rækkefølge ud fra dato
                //personData.sorterMøderne();

                if (taskCount <= 0)
                    næsteSide();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

        }
    }

    class HentFeedbackListener implements OnCompleteListener<QuerySnapshot>{

        private String mødeid;

        public HentFeedbackListener(String mødeid){
            this.mødeid = mødeid;
            taskCount++;
        }

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()){
                List<List<Svar>> feedback = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()){
                    Map<String, Object> map = document.getData();
                    List<Svar> svarListe = new ArrayList<>();

                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if (entry.getKey().equals("svar")) {
                            List<Object> person = (List<Object>) entry.getValue();
                            for (Object obj : person){
                                svarListe.add(Svar.getSvar(obj.toString()));
                            }
                        }
                    }

                    feedback.add(svarListe);
                }

                if (feedback.size() > 0)
                    PersonData.getInstance().tilføjFeedback(mødeid, feedback);

                taskCount--;

                if (taskCount <= 0){
                    næsteSide();
                }
            }
        }
    }
}
