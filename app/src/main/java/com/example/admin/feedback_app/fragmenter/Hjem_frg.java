package com.example.admin.feedback_app.fragmenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.aktiviteter.OpretMoede_akt;


public class Hjem_frg extends Fragment implements View.OnClickListener {



    private Button opretMøde;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hjem, container, false);

        opretMøde = v.findViewById(R.id.hjem_opret_btn);
        opretMøde.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        opdaterTekst();
    }

    private void opdaterTekst(){
    }

    @Override
    public void onClick(View view) {
        if (view == opretMøde) {
            startActivity(new Intent(getContext(), OpretMoede_akt.class));
        }
    }
}
