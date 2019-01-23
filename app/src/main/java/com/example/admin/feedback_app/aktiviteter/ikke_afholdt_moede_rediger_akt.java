package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ikke_afholdt_moede_rediger_akt extends AppCompatActivity implements View.OnClickListener {

    private TextView navn, sted, formål, dato, startTid, slutTid;

    private String navnString, stedString, formålString, datoString, tidStartString, tidSlutString;

    private Button button;

    private int indeks;

    private Møde møde;

    FirebaseFirestore firebaseFirestore;

    PersonData personData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikke_afholdt_moede_rediger_akt);

        personData = PersonData.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        navn = findViewById(R.id.redigerMødenavn);
        sted = findViewById(R.id.redigerMødested);
        formål = findViewById(R.id.redigerMødeformål);
        dato = findViewById(R.id.redigerMødeDato);
        startTid = findViewById(R.id.redigerMødeStartTidspunkt);
        slutTid = findViewById(R.id.redigerMødeSlutTidspunkt);

        button = findViewById(R.id.rediger_profil_gem_ændringer_button);
        button.setOnClickListener(this);

        navnString = getIntent().getStringExtra("NAVN");
        stedString = getIntent().getStringExtra("STED");
        formålString = getIntent().getStringExtra("FORMÅL");
        datoString = getIntent().getStringExtra("DATO");
        tidStartString = getIntent().getStringExtra("TIDSTART");
        tidSlutString = getIntent().getStringExtra("TIDSLUT");

        navn.setText(navnString);
        sted.setText(stedString);
        formål.setText(formålString);
        dato.setText(datoString);
        startTid.setText(tidStartString);
        slutTid.setText(tidSlutString);

        indeks = getIntent().getIntExtra("INDEKS",0);


        Log.d("hej", "onCreate: "+indeks);

    }

    @Override
    public void onClick(View v) {
        if(v==button){
            //Hvis det ikke er sket nogen ændring
            if(navn.getText().toString().equals(navnString)&& sted.getText().toString().equals(stedString)&& formål.getText().toString().equals(formålString)&&
                    dato.getText().toString().equals(datoString)&&startTid.getText().toString().equals(tidStartString)&&slutTid.getText().toString().equals(tidSlutString)){
                Toast.makeText(getApplicationContext(), "Ingen ændring blev foretaget", Toast.LENGTH_SHORT).show();
             finish();

            }
            else {
                //Læg ændringer op i firebase og i mødeobjekt med pågældende indeks

                //opdaterer mødeobjekt

                møde.setNavn(navn.getText().toString());
                møde.setSted(sted.getText().toString());
                møde.setFormål(formål.getText().toString());
                møde.setDato(dato.getText().toString());
                møde.setStartTid(startTid.getText().toString());
                møde.setSlutTid(slutTid.getText().toString());
                //personData skal opdateres

                //firebaseFirestore.collection("Møder").document(personData.getMøde);


                finish();
            }



        }
    }
}
