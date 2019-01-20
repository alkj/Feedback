package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.aktiviteter.Navigation_akt;
import com.google.firebase.auth.FirebaseAuth;


public class Profil_frg extends Fragment implements View.OnClickListener {

    private static final String TAG = "profilfragment";

    private Button logUd, skiftPassword, redigerButton;
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
        redigerButton = v.findViewById(R.id.profil_rediger_button);

        fornavn = v.findViewById(R.id.profil_fornavn_textView);
        efternavn = v.findViewById(R.id.profil_efternavn_textView);
        telefonnummer = v.findViewById(R.id.profil_tlf_textView);
        virksomhedsID = v.findViewById(R.id.profil_virksomhedsid_textView);


        logUd.setOnClickListener(this);
        redigerButton.setOnClickListener(this);
        skiftPassword.setOnClickListener(this);

        fornavn.setText(mødeholder.getFornavn());
        efternavn.setText(mødeholder.getEfternavn());
        telefonnummer.setText(mødeholder.getTlf());
        virksomhedsID.setText(mødeholder.getVirk_id());

  //      ViewCompat.setTransitionName(fornavn, "fornavn");
  //      ViewCompat.setTransitionName(efternavn, "efternavn");
  //      ViewCompat.setTransitionName(telefonnummer, "telefonnummer");
  //      ViewCompat.setTransitionName(virksomhedsID, "virksomhedsid");
  //      ViewCompat.setTransitionName(redigerButton, "redigerButton");



        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == logUd) {
            mAuth.signOut();
            personData.ryd();
            this.getActivity().finish();
        }

        if (v == redigerButton) {

            Log.d(TAG, "onClick: redigerButton");

            RedigerProfilOplysninger_frg redigerFrag = new RedigerProfilOplysninger_frg();
            redigerFrag.setEnterTransition(new AutoTransition().setDuration(100));

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navigation_fragment_container, redigerFrag).commit();

        }


        if (v == skiftPassword) {

            Log.d(TAG, "onClick: skift password");

            SkiftPassword_frg skiftPasswordFrag = new SkiftPassword_frg();
            skiftPasswordFrag.setEnterTransition(new AutoTransition().setDuration(100));

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navigation_fragment_container, skiftPasswordFrag).commit();




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
