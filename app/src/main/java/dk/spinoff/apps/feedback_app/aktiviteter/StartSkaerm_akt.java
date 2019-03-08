package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.spinoff.apps.feedback_app.Møde;
import com.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.VibratorManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Set;



public class StartSkaerm_akt extends BaseActivity implements View.OnClickListener {

    private Button login_btn, feedback_btn;
    private EditText mødeId_editTxt;
    private FirebaseAuth firebaseAuth;

    private SharedPreferences prefs;
    private Møde mødet;

    private FirebaseAnalytics firebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);


        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "startskærm");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name test");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "anden aktivitet");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Knapper
        login_btn = (Button)findViewById(R.id.startskaerm_login_btn);
        feedback_btn = (Button)findViewById(R.id.startskaerm_feedback_btn);

        mødeId_editTxt = (EditText)findViewById(R.id.startskærm_editTxt);

        login_btn.setOnClickListener(this);
        feedback_btn.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        mødet = new Møde();

        try {
            Set<String> gg = prefs.getStringSet("key", null);
            Log.i("hej", "udskriv brugte ids: " + gg);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onClick(View view) {

        String mødeID = mødeId_editTxt.getText().toString();

        if (view == login_btn){
            //Starter login aktiviteteten
            Intent intent = new Intent(this, Login_akt.class);
            startActivity(intent);
        }
        else if ( view == feedback_btn) {

            //hvis edittext er tom
            if(mødeId_editTxt.length()==0){
                VibratorManager.vibrerMønster(this,VibratorManager.FEJL_VIB,-1);
                mødeId_editTxt.setError("Indtast ID");
                Toast.makeText(this, "Indtast ID", Toast.LENGTH_SHORT).show();
                return;
            }

            //Hvis man allerede har givet feedback
            Set<String> gg = prefs.getStringSet("key", null);
            if(gg!=null) {
                for (String s : gg) {

                    if (s.equals(mødeID)) {
                        Toast.makeText(getApplicationContext(), "Du har allerede givet feedback", Toast.LENGTH_SHORT).show();
                        Log.i("hej", "mødeID er lig med et brygt et");
                        return;
                    }
                }
            }
            showProgressDialog();
            updateProgressDialog("Henter møde");
            hentMøderFraFirebase(mødeID);
        }
    }

    private void checkOmIdPasserMedMødeID() {

        showProgressDialog();
        updateProgressDialog("Henter møde");
        if (mødet.getMødeIDtildeltager()==null) {
            hideProgressDialog();
            VibratorManager.vibrerMønster(getApplicationContext(),VibratorManager.FEJL_VIB,-1);
            mødeId_editTxt.setError("Forkert møde-ID");
            Toast.makeText(getApplicationContext(), "Forkert møde-ID ", Toast.LENGTH_SHORT).show();
        }


        else {

            hideProgressDialog();

            //Starter feedback aktiviteten
            Intent intent = new Intent(getApplicationContext(), beforeGivFeedback_akt.class);
            intent.putExtra("MØDEID", mødet.getMødeID());
            intent.putExtra("MØDEIDdel", mødet.getMødeIDtildeltager());
            intent.putExtra("NAVN", mødet.getNavn());
            intent.putExtra("DATO", mødet.getDato());
            intent.putExtra("STED", mødet.getSted());
            intent.putExtra("FORMÅL", mødet.getFormål());
            intent.putExtra("TIDSTART", mødet.getStartTid());
            intent.putExtra("TIDSLUT", mødet.getSlutTid());
            intent.putExtra("igang", mødet.getIgang());

            startActivity(intent);
        }

    }

    private void hentMøderFraFirebase(String mødeID) {

        FirebaseFirestore.getInstance().collection("Møder")
                .whereEqualTo("mødeIDtildeltager", mødeID)
                .get()
                .addOnCompleteListener(new FindMødeListener());
    }


    class FindMødeListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String mødeID = document.get("mødeID").toString();
                    String mødeIDtildeltager = document.get("mødeIDtildeltager").toString();
                    String sted = document.get("sted").toString();
                    String dato = document.get("dato").toString();
                    String navn = document.get("navn").toString();
                    String formål = document.get("formål").toString();
                    String tidStart = document.get("startTid").toString();
                    String tidSlut = document.get("slutTid").toString();
                    Boolean igang = document.getBoolean("igang");

                    mødet.setMødeID(mødeID);
                    mødet.setMødeIDtildeltager(mødeIDtildeltager);
                    mødet.setNavn(navn);
                    mødet.setSted(sted);
                    mødet.setDato(dato);
                    mødet.setFormål(formål);
                    mødet.setStartTid(tidStart);
                    mødet.setSlutTid(tidSlut);
                    mødet.setIgang(igang);

                    Log.d("debug, hvad bliver", "Mødeid'et bliver: " + mødeID);

                }

                checkOmIdPasserMedMødeID();



            } else {
                Log.d("debug, det er lrt", "FEJLER !");


            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mødeId_editTxt.setText("");

        try {
            Set<String> gg = prefs.getStringSet("key", null);
        }
        catch (Exception e){

        }
    }
}