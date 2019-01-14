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

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profil_frg extends Fragment implements View.OnClickListener {
    private Button logUd, skiftPassword, gem;
    private EditText fornavn, efternavn, telefonnummer;
    private TextView virksomhedsID;
    private PersonData personData;
    private FirebaseAuth mAuth;
    private Mødeholder mødeholder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        personData = PersonData.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mødeholder = PersonData.getInstance().getMødeholder();


        logUd = v.findViewById(R.id.profil_logUd_button);
        skiftPassword = v.findViewById(R.id.profil_skiftPassword_button);
        gem = v.findViewById(R.id.profil_gem_button);

        fornavn = v.findViewById(R.id.profil_fornavn_editTxt);
        efternavn = v.findViewById(R.id.profil_efternavn_editTxt);
        telefonnummer = v.findViewById(R.id.profil_tlf_editTxt);

        virksomhedsID = v.findViewById(R.id.profil_virksomhedsid_textView);


        logUd.setOnClickListener(this);
        skiftPassword.setOnClickListener(this);

        fornavn.setText(mødeholder.getFornavn());
        efternavn.setText(mødeholder.getEfternavn());
        telefonnummer.setText(mødeholder.getTlf());
        virksomhedsID.setText(mødeholder.getVirk_id());

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == logUd) {
            mAuth.signOut();
            personData.ryd();
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
