package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class StartSkaerm_akt extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn, feedback_btn;
    private EditText mødeId_editTxt;
    private FirebaseAuth firebaseAuth;

    private SharedPreferences prefs;
    private Møde mødet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);



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




            //TODO: indlæs inputet fra editText'en og finde det tilhørende møde


            //updateProgressDialog("Henter møderne");
            FirebaseFirestore.getInstance().collection("Møder")
                    .whereEqualTo("mødeIDtildeltager", mødeID)
                    .get()
                    .addOnCompleteListener(new FindMødeListener());

            if (mødet.getMødeIDtildeltager()==null) {
                Toast.makeText(this, "Forkert møde-ID ", Toast.LENGTH_SHORT).show();
            }


            else {

                //Starter feedback aktiviteten
                Intent intent = new Intent(this, GivFeedback_akt.class);
                intent.putExtra("MØDEID", mødet.getMødeID());
                intent.putExtra("MØDEIDdel",mødet.getMødeIDtildeltager());
                startActivity(intent);
                //Log.d("debug, virker det",personData.getFeedbackTilDetteMøde().getMødeID());

            }
        }
    }

    class FindMødeListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String mødeID = document.get("mødeID").toString();
                    String mødeIDtildeltager = document.get("mødeIDtildeltager").toString();
                    mødet.setMødeID(mødeID);
                    mødet.setMødeIDtildeltager(mødeIDtildeltager);

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