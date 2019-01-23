package com.example.admin.feedback_app.aktiviteter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IkkeAfholdtMoede_akt extends AppCompatActivity implements OnClickListener {

    private Møde møde;

    private ShimmerTextView moedeID;
    private Shimmer shimmer;

    private TextView navn, formaal, sted, tidspunkt, dato, status, mødeID, startTidspunkt;

    private Button startMoede, afslutMoede;

    private ImageView slet, rediger;

    private Chronometer forloebtTid;

    FirebaseFirestore firebaseFirestore;
    private int indeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikke_afholdt_moede_akt);

        firebaseFirestore = FirebaseFirestore.getInstance();

        moedeID = findViewById(R.id.tvMødeID);

        slet = findViewById(R.id.imageViewSkraldespand);
        rediger = findViewById(R.id.imageViewBlyant);

        navn = findViewById(R.id.tvNavn);
        formaal = findViewById(R.id.tvFormål);
        sted = findViewById(R.id.tvSted);
        tidspunkt = findViewById(R.id.tvTidspunkt);
        dato = findViewById(R.id.tvDato);
        status = findViewById(R.id.tvStatus);
        mødeID = findViewById(R.id.tvMødeID);
        startTidspunkt = findViewById(R.id.tvStartTidspunkt);
        startTidspunkt.setText("");

        forloebtTid = findViewById(R.id.tvTimer);
        forloebtTid.stop();

        startMoede = findViewById(R.id.buttonStartMøde);
        startMoede.setOnClickListener(this);
        afslutMoede = findViewById(R.id.buttonAfslutMøde);
        afslutMoede.setVisibility(View.INVISIBLE);
        afslutMoede.setOnClickListener(this);



        //TODO: håndtere at mødet ikke findes
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                indeks = bundle.getInt("INDEKS");
                møde = PersonData.getInstance().getIkkeAfholdteMøder().get(indeks);
            }
        }

        navn.setText(møde.getNavn());
        formaal.setText(møde.getFormål());
        sted.setText(møde.getSted());
        tidspunkt.setText(møde.getStartTid()+" - "+møde.getSlutTid());
        dato.setText(møde.getDato());
        status.setText("Ikke i gang");
        mødeID.setText(møde.getMødeIDtildeltager());

        shimmer = new Shimmer();
        shimmer.start(moedeID);
        shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_LTR);



    }


    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDato = mdformat.format(calendar.getTime());
        return strDato;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v == startMoede) {

            if (startMoede.getText().equals("Start Møde")) {

                Log.i("hej", "onClick: start møde klik");

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

                        forloebtTid.start();
                        forloebtTid.setBase(SystemClock.elapsedRealtime());

                        startTidspunkt.setText(getCurrentTime());
                        møde.setStartTid(getCurrentTime());

                        startMoede.setVisibility(View.INVISIBLE);
                        afslutMoede.setVisibility(View.VISIBLE);

                        status.setText("I gang");
                        status.setTextColor(ContextCompat.getColor(IkkeAfholdtMoede_akt.this,R.color.colorMegetGlad));

                        rediger.setVisibility(View.INVISIBLE);
                        slet.setVisibility(View.INVISIBLE);

                        møde.setIgang(true);

                        firebaseFirestore.collection("Møder").document(møde.getMødeID()).set(møde);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else if(v == afslutMoede) {
            Log.i("hej","else kommer igennem");
            final AlertDialog.Builder builder = new AlertDialog.Builder(IkkeAfholdtMoede_akt.this);
            builder.setMessage("Er du sikker på at du vil afslutte mødet?");
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

                    møde.setIgang(false);
                    møde.setAfholdt(true);
                    møde.setStartTid(møde.getStartTid());
                    møde.setSlutTid(getCurrentTime());

                    //møde objekt op til fgire

                    firebaseFirestore.collection("Møder").document(møde.getMødeID()).set(møde);

                    Toast.makeText(getApplicationContext(), "Dit møde er nu aflsuttet", Toast.LENGTH_SHORT).show();



                    finish();

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


}
