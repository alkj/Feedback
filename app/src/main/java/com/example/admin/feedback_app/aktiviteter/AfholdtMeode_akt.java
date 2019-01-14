package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;

public class AfholdtMeode_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde møde;

    private TextView moedeNavn, moedeID;

    private Button moedeInfo, overordnet_knap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_meode_akt);

        moedeInfo = findViewById(R.id.AfholdtMoedeInfo_knap);
        moedeInfo.setOnClickListener(this);

        overordnet_knap = findViewById(R.id.AfholdtOverordnetStatistik_knap);
        overordnet_knap.setOnClickListener(this);

        moedeNavn = findViewById(R.id.tvMødeNavn);
        moedeID = findViewById(R.id.tvMødeID);

        //TODO: håndtere at mødet ikke findes
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                int indeks = bundle.getInt("INDEKS");
                møde = PersonData.getInstance().getIkkeAfholdteMøder().get(indeks);
            }
        }

        moedeNavn.setText(møde.getNavn());
        moedeID.setText(møde.getMødeID());

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
