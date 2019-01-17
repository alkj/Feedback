package com.example.admin.feedback_app.fragmenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.AendreOplysninger;
import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.aktiviteter.RedigerProfilOplysninger_frg;
import com.google.firebase.auth.FirebaseAuth;


public class Profil_frg extends Fragment implements View.OnClickListener {

    private static String TAG = "profil fragment i navigations aktivitet";

    private Button logUd, skiftPassword, rediger;
    private TextView fornavn, efternavn, telefonnummer;
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
        rediger = v.findViewById(R.id.profil_rediger_button);

        fornavn = v.findViewById(R.id.aendre_fornavn_editTxt);
        efternavn = v.findViewById(R.id.aendre_efternavn_editTxt);
        telefonnummer = v.findViewById(R.id.aendre_tlf_editTxt);

        virksomhedsID = v.findViewById(R.id.aendre_virksomhedsid_textView);


        logUd.setOnClickListener(this);
        rediger.setOnClickListener(this);
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

        if (v == rediger) {

            Log.d(TAG, "onClick: rediger");

            RedigerProfilOplysninger_frg rediger = new RedigerProfilOplysninger_frg();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.navigation_fragment_container, rediger).addToBackStack(null).commit();

        }


        if (v == skiftPassword) {

            Log.d(TAG, "onClick: skift password");

            SkiftPassword_frg skiftPassword = new SkiftPassword_frg();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.navigation_fragment_container, skiftPassword).addToBackStack(null).commit();

        }


    }

    @Override
    public void onResume() {

        fornavn.setText(mødeholder.getFornavn());
        efternavn.setText(mødeholder.getEfternavn());
        telefonnummer.setText(mødeholder.getTlf());
        virksomhedsID.setText(mødeholder.getVirk_id());


        super.onResume();
    }
}
