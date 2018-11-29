package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profil_frg extends Fragment implements View.OnClickListener {
    private Button logUd, skiftPassword, gem;
    private EditText fornavn, efternavn, telefonnummer;
    private TextView virksomhedsID;

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod = inflater.inflate(R.layout.fragment_profil, container, false);

        mAuth = FirebaseAuth.getInstance();


        logUd = rod.findViewById(R.id.profil_logUd_button);
        skiftPassword = rod.findViewById(R.id.profil_skiftPassword_button);
        gem = rod.findViewById(R.id.profil_gem_button);

        fornavn = rod.findViewById(R.id.profil_fornavn_editTxt);
        efternavn = rod.findViewById(R.id.profil_efternavn_editTxt);
        telefonnummer = rod.findViewById(R.id.profil_tlf_editTxt);

        virksomhedsID = rod.findViewById(R.id.profil_virksomhedsid_textView);


        logUd.setOnClickListener(this);
        skiftPassword.setOnClickListener(this);

        fornavn.setText("peter"); //TODO
        efternavn.setText("Jensen"); //TODO
        telefonnummer.setText("99999999"); //TODO hent info fra mødelederobjekt
        virksomhedsID.setText("DTU");

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == logUd) {
            mAuth.signOut();
            this.getActivity().finish();
        }

        if (v == gem) {

            //TODO
        }


        if (v == skiftPassword) {
            //TODO
        }


    }
}
