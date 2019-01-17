package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.dialogs.DatoPickerDialog_frg;
import com.example.admin.feedback_app.dialogs.TidPickerDialog_frg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class OpretMoede_2_frg extends Fragment implements View.OnClickListener {

    private String mødenavnS, mødeformålS, stedS;
    private TextView starttid_txt, sluttid_txt;
    private TextView dato_txt;
    private  EditText mødeNavn, mødeFormål, sted, dagsorden;
    private Button opretMoede_knap;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_opretmoede_2, container, false);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();

        mødenavnS = mBundle.getString("mødenavn");
        mødeformålS = mBundle.getString("mødeformål");
        stedS = mBundle.getString("sted");

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        opretMoede_knap = v.findViewById(R.id.opretmoede_opret_btn);


        starttid_txt = v.findViewById(R.id.tidStart);
        starttid_txt.setOnClickListener(this);

        sluttid_txt = v.findViewById(R.id.tidSlut);
        sluttid_txt.setOnClickListener(this);

        dato_txt = v.findViewById(R.id.dato);
        dato_txt.setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tidStart:
                DialogFragment dialog_starttid = new TidPickerDialog_frg();
                dialog_starttid.show(getActivity().getSupportFragmentManager(), "tidPicker");
                break;
            case R.id.tidSlut:
                DialogFragment dialog_slutrid = new TidPickerDialog_frg();
                dialog_slutrid.show(getActivity().getSupportFragmentManager(), "tidPicker");
                break;
            case R.id.dato:
                DialogFragment dialog_dato = new DatoPickerDialog_frg();
                dialog_dato.show(getActivity().getSupportFragmentManager(), "datoPicker");
                break;
        }
    }
}
