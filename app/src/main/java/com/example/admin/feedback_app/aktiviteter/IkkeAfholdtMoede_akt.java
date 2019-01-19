package com.example.admin.feedback_app.aktiviteter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.MoedeStartet_frg;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IkkeAfholdtMoede_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde
            møde;

    private ShimmerTextView moedeID;
    private Shimmer shimmer;

    private TextView
            moedeNavn, moedeFormaal, moedeSted, tidStart, tidSlut, inviterede, fremmoedte, moedeDato;

    private Button
            inviter, startMoede, rediger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikke_afholdt_moede_akt);

        //inviter = findViewById(R.id.ikkeAfholdtInviter_knap);
        //inviter.setOnClickListener(this);

        startMoede = findViewById(R.id.ikkeAfholdtStart_knap);
        startMoede.setOnClickListener(this);

        //rediger = findViewById(R.id.ikkeAfholdt_redigerKnap);
        //rediger.setOnClickListener(this);

        moedeNavn = findViewById(R.id.tvMødeNavn);
        moedeFormaal = findViewById(R.id.tvMødeFormaal);
        moedeSted = findViewById(R.id.tvMødeSted);
        moedeID = findViewById(R.id.tvMødeID);
        moedeDato = findViewById(R.id.tvMødeDato);
        tidStart = findViewById(R.id.tvPlanlagtStarttid);
        tidSlut = findViewById(R.id.tvPlanlagtSluttid);
        inviterede = findViewById(R.id.tvInviterede);
        fremmoedte = findViewById(R.id.tvFremmødte);

        //TODO: håndtere at mødet ikke findes
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                int indeks = bundle.getInt("INDEKS");
                møde = PersonData.getInstance().getIkkeAfholdteMøder().get(indeks);
            }
        }

        moedeNavn.setText(møde.getNavn());
        moedeFormaal.setText(møde.getFormål());
        moedeSted.setText(møde.getSted());
        moedeID.setText(møde.getMødeIDtildeltager());
        moedeDato.setText(møde.getDato());
        tidStart.setText(møde.getStartTid());
        tidSlut.setText(møde.getSlutTid());
        //inviterede.setText(møde.getAntalInviterede);
        //fremmoedte.setText(møde.getFremmoedte);

        shimmer = new Shimmer();
        shimmer.start(moedeID);
        shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_LTR);

    }


    @Override
    public void onClick(View view) {
        if (view == startMoede) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(IkkeAfholdtMoede_akt.this);
            builder.setMessage("Er du sikker på at du vil starte mødet?");
            builder.setCancelable(true);
            builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   // sendData();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerMødeStart,new MoedeStartet_frg());
                    fragmentTransaction.commit();




                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private void sendData() {
        Bundle bundle = new Bundle();
        bundle.putString("Møde ID", moedeID.getText().toString());
        bundle.putString("Møde Navn", moedeNavn.getText().toString());
        bundle.putString("Møde Formål", moedeFormaal.getText().toString());
        bundle.putString("Inviterede", inviterede.getText().toString());
        bundle.putString("Planlagt Starttid", tidStart.getText().toString());
        bundle.putString("Planlagt Sluttid", tidSlut.getText().toString());

        MoedeStartet_frg moedeStartet_frg = new MoedeStartet_frg();
        moedeStartet_frg.setArguments(bundle);

    }


    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDato = "aktuel tid: " + mdformat.format(calendar.getTime());
        return strDato;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
