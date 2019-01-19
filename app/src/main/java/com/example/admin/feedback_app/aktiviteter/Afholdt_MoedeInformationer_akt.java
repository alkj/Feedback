package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;

public class Afholdt_MoedeInformationer_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde
            møde;

    private TextView
            moedeNavn, moedeFormaal, moedeSted, tidStart, tidSlut, aktuelTidStart, aktuelTidSlut, inviterede, fremmoedte, moedeID;

    private Button tilbage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt__moede_informationer_akt);

        tilbage = findViewById(R.id.afholdtMoedeInfoTilbage);
        tilbage.setOnClickListener(this);

        moedeNavn = findViewById(R.id.tvMødeInfoMødeNavn);
        //moedeNavn.setText(møde.getNavn());

        moedeFormaal = findViewById(R.id.tvMødeInfoMødeFormaal);
        //moedeFormaal.setText(møde.getFormål());

        moedeSted = findViewById(R.id.tvMødeInfoMødeSted);
        //moedeSted.setText(møde.getSted());

        moedeID = findViewById(R.id.tvMødeInfoMødeID);
        //moedeID.setText(møde.getMødeIDtildeltager());

        tidStart = findViewById(R.id.tvMødeInfoPlanlagtStarttid);
        //tidStart.setText(møde.getStartTid());

        tidSlut = findViewById(R.id.tvMødeinfoPlanlagtSluttid);
        //tidSlut.setText(møde.getSlutTid());

        aktuelTidStart = findViewById(R.id.tvMødeInfoAktuelStarttid);

        aktuelTidSlut = findViewById(R.id.tvMødeInfoAktuelSluttid);

        inviterede = findViewById(R.id.tvMødeInfoInviterede);

        fremmoedte = findViewById(R.id.tvMødeInfoFremmødte);
    }

    @Override
    public void onClick(View view) {
        if (view == tilbage) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
