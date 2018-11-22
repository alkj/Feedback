package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.dialogs.DatoPickerDialog_frg;
import com.example.admin.feedback_app.dialogs.TidPickerDialog_frg;
//import com.example.admin.feedback_app.fragmenter.DatoPickerDialog_frg;
//import com.example.admin.feedback_app.fragmenter.TidPickerDialog_frg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class OpretMoede_akt extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "opretMøde";

    private Button annuler_knap, opretMoede_knap;
    TextView tid_txt;
    TextView dato_txt;
    EditText mødeNavn, mødeFormål, sted;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private com.example.admin.feedback_app.mødeholder mødeholder;


    @Override
    protected void onCreate(Bundle savedInstancesBundle) {
        super.onCreate(savedInstancesBundle);
        setContentView(R.layout.activity_opret_moede);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        opretMoede_knap = findViewById(R.id.opretmoede_opret_btn);
        opretMoede_knap.setOnClickListener(this);

        annuler_knap = findViewById(R.id.opretmoede_anuller_btn);
        annuler_knap.setOnClickListener(this);

        tid_txt = findViewById(R.id.opretmoeder_tid_txt);
        tid_txt.setOnClickListener(this);

        dato_txt = findViewById(R.id.opretmoeder_dato_txt);
        dato_txt.setOnClickListener(this);

        mødeNavn = findViewById(R.id.opretmoede_navn_editTxt);
        mødeFormål = findViewById(R.id.opretmoede_formaal_editTxt);
        sted = findViewById(R.id.opretmoeder_sted_editTxt);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.opretmoeder_tid_txt:
                DialogFragment dialog_tid = new TidPickerDialog_frg();
                dialog_tid.show(getSupportFragmentManager(), "tidPicker");
                break;
            case R.id.opretmoeder_dato_txt:
                DialogFragment dialog_dato = new DatoPickerDialog_frg();
                dialog_dato.show(getSupportFragmentManager(), "datoPicker");
                break;
            case R.id.opretmoede_anuller_btn:
                if (view == annuler_knap){
                    //Lukker aktiviteten og går tilbage til forrige
                    finish();
                    };
                                    break;
            case R.id.opretmoede_opret_btn:
                Log.d(TAG, "Opret møde knappen er trykket");
                Møde møde = new Møde();
                møde.setNavn(mødeNavn.getText().toString());
                Log.d(TAG,møde.getNavn() );
                møde.setFormål(mødeFormål.getText().toString());
                møde.setDato(dato_txt.getText().toString());
                møde.setTid(tid_txt.getText().toString());
                møde.setSted(sted.getText().toString());
                møde.setMødeholderID(mAuth.getUid());


                mFirestore.collection("møder").document().set(møde);
                Log.d(TAG, "Mødet er oprettet og aktiviteten lukkes");
                finish();
                break;

        }
    }
}
