package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;



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

        login_btn.setOnClickListener(this);
        feedback_btn.setOnClickListener(this);

        //Input felt
        mødeId_editTxt = (EditText)findViewById(R.id.startskærm_editTxt);


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
                Toast.makeText(this, "Indtast ID", Toast.LENGTH_SHORT).show();
                return;
            }

            //Hvis man allerede har givet feedback
            Set<String> gg = prefs.getStringSet("key", null);
            if(gg!=null) {
                for (String s : gg) {

                    if (s.equals(mødeID)) {
                        Toast.makeText(this, "Du har allerede givet feedback", Toast.LENGTH_SHORT).show();
                        Log.i("hej", "mødeID er lig med et brygt et");
                        return;
                    }
                }
            }
            hentMøderFraFirebase(mødeID);
            checkOmIdPasserMedMødeID();
        }
    }

    private void checkOmIdPasserMedMødeID() {

        showProgressDialog();
        updateProgressDialog("Henter møde");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (mødet.getMødeIDtildeltager()==null) {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Forkert møde-ID ", Toast.LENGTH_SHORT).show();
                }


                else {

                    hideProgressDialog();

                    //Starter feedback aktiviteten
                    Intent intent = new Intent(getApplicationContext(), beforeGivFeedback_akt.class);
                    intent.putExtra("MØDEID", mødet.getMødeID());
                    intent.putExtra("MØDEIDdel",mødet.getMødeIDtildeltager());
                    intent.putExtra("DATO",mødet.getDato());
                    intent.putExtra("STED",mødet.getSted());
                    startActivity(intent);

                }

            }
        }, 2000);
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
                    String dagsorden = document.get("dagsorden").toString();
                    String sted = document.get("sted").toString();
                    String dato = document.get("dato").toString();

                    mødet.setMødeID(mødeID);
                    mødet.setMødeIDtildeltager(mødeIDtildeltager);
                    mødet.setSted(sted);
                    mødet.setDato(dato);

                    Log.d("debug, hvad bliver", "Mødeid'et bliver: " + mødeID);

                }


                //næsteSide();
            } else {
                Log.d("debug, det er lrt", "hvorfor fejler den aldrig");
                //hent enheder i set

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