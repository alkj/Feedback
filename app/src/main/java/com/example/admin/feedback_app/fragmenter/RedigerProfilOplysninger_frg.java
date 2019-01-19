package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class RedigerProfilOplysninger_frg extends Fragment implements View.OnClickListener {

    private static final String TAG = "RedigerProfilOplys";

    private PersonData personData;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private EditText editTextFornavn, editTextEfternavn, editTextVirkid, editTextTlf;
    private Button buttonGem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_rediger_profil_oplysninger, container, false);
        personData = PersonData.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        editTextFornavn = rod.findViewById(R.id.rediger_profil_fornavn_editText);
        editTextEfternavn = rod.findViewById(R.id.rediger_profil_efternavn_editTxt);
        editTextVirkid = rod.findViewById(R.id.rediger_profil_virksomheds_id_editTxt);
        editTextTlf = rod.findViewById(R.id.rediger_profil_tlf_editText);


        buttonGem = rod.findViewById(R.id.rediger_profil_gem_ændringer_button);

        buttonGem.setOnClickListener(this);

        editTextFornavn.setText(personData.getMødeholder().getFornavn());
        editTextEfternavn.setText(personData.getMødeholder().getEfternavn());
        editTextTlf.setText(personData.getMødeholder().getTlf());
        editTextVirkid.setText(personData.getMødeholder().getVirk_id());

        ViewCompat.setTransitionName(editTextFornavn, "fornavn");
        ViewCompat.setTransitionName(editTextEfternavn, "efternavn");
        ViewCompat.setTransitionName(editTextTlf, "telefonnummer");
        ViewCompat.setTransitionName(editTextVirkid, "virksomhedsid");
        ViewCompat.setTransitionName(buttonGem, "rediger");

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonGem) {
            //tjekker om der er lavet ændringer, før det bliver sendt til firebase.
            if (!editTextFornavn.getText().toString().equals(personData.getMødeholder().getFornavn())
                    || !editTextEfternavn.getText().toString().equals(personData.getMødeholder().getEfternavn().toString())
                    || !editTextVirkid.getText().toString().equals(personData.getMødeholder().getVirk_id().toString())
                    || !editTextTlf.getText().toString().equals(personData.getMødeholder().getTlf().toString())) {

                personData.getMødeholder().setFornavn(editTextFornavn.getText().toString());
                personData.getMødeholder().setEfternavn(editTextEfternavn.getText().toString());
                personData.getMødeholder().setVirk_id(editTextVirkid.getText().toString());
                personData.getMødeholder().setTlf(editTextTlf.getText().toString());

                Log.d(TAG, "onClick: data klar til at blive sendt til firebase");
                firebaseFirestore.collection("Mødeholder").document(firebaseAuth.getUid()).set(personData.getMødeholder());
                Log.d(TAG, "onClick: data er blevet sendt.");
                Toast.makeText(this.getActivity(), "information gemt", Toast.LENGTH_SHORT).show();
                this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.navigation_fragment_container, new Profil_frg()).commit();

            } else {
                Toast.makeText(this.getActivity(), "ingen ændringer fundet", Toast.LENGTH_SHORT).show();
                this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.navigation_fragment_container, new Profil_frg()).commit();
            }
        }
    }


}
