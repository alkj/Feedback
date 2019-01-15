package com.example.admin.feedback_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.aktiviteter.OpretMoede_akt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AendreOplysninger extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mFirestore;
    private EditText navn, efternavn, mobilnummer;
    private TextView virkID;
    private Button gem;
    PersonData personData;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aendre_oplysninger);

        navn = (EditText) findViewById(R.id.aendre_fornavn_editTxt);
        efternavn = (EditText) findViewById(R.id.aendre_efternavn_editTxt);
        mobilnummer = (EditText) findViewById(R.id.aendre_tlf_editTxt);
        virkID = (TextView) findViewById(R.id.aendre_virksomhedsid_textView);

        gem = (Button) findViewById(R.id.aendre_gem_btn);
        gem.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        personData = PersonData.getInstance();
        //personData.getMødeholder().setEfternavn("Frank");
        mFirestore = FirebaseFirestore.getInstance();

        //Sæt tekstfelterne med de aktuelle værdier
        navn.setText(personData.mødeholder.getFornavn());
        efternavn.setText(personData.mødeholder.getEfternavn());
        mobilnummer.setText(personData.mødeholder.getTlf());
        virkID.setText(personData.mødeholder.getVirk_id());

       // Log.d("bæ", "lige før FB kommando");
        mFirestore.collection("Mødeholder").document(mAuth.getUid()).set(personData.getMødeholder());

    }

    @Override
    public void onClick(View v) {
        personData.getMødeholder().setFornavn(navn.getText().toString());
        personData.getMødeholder().setEfternavn(efternavn.getText().toString());
        personData.getMødeholder().setTlf(mobilnummer.getText().toString());

        mFirestore.collection("Mødeholder").document(mAuth.getUid()).set(personData.getMødeholder());


        finish();


    }
}
