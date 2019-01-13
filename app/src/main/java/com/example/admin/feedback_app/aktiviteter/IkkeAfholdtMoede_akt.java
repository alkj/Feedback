package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IkkeAfholdtMoede_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde
            møde;

    private TextView
            moedeNavn, moedeFormaal, moedeSted, tidStart, tidSlut, inviterede, fremmoedte;

    private Button
            startMoede, rediger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikke_afholdt_moede_akt);

        startMoede = findViewById(R.id.ikkeAfholdtStart_knap);
        startMoede.setOnClickListener(this);

        rediger = findViewById(R.id.ikkeAfholdt_redigerKnap);
        rediger.setOnClickListener(this);

        moedeNavn = findViewById(R.id.tvMødeNavn);
        moedeFormaal = findViewById(R.id.tvMødeFormaal);
        moedeSted = findViewById(R.id.tvMødeSted);
        tidStart = findViewById(R.id.tvPlanlagtStarttid);
        tidSlut = findViewById(R.id.tvPlanlagtSluttid);
        inviterede = findViewById(R.id.tvInviterede);
        fremmoedte = findViewById(R.id.tvFremmødte);

        moedeNavn.setText(møde.getNavn());
        moedeFormaal.setText(møde.getFormål());
        moedeSted.setText(møde.getSted());
        tidStart.setText(møde.getTid());

    }


    @Override
    public void onClick(View view) {
        if (view == startMoede) {
            getCurrentTime();
        }
    }


    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDato = "aktuel tid: " + mdformat.format(calendar.getTime());
        return strDato;
    }



}
