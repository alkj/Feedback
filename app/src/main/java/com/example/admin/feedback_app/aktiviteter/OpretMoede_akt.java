package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.dialogs.DatoPickerDialog_frg;
import com.example.admin.feedback_app.dialogs.TidPickerDialog_frg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;
import java.util.UUID;


public class OpretMoede_akt extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "opretMøde";

    private Button annuler_knap, opretMoede_knap;
    TextView starttid_txt, sluttid_txt;
    TextView dato_txt;
    EditText mødeNavn, mødeFormål, sted;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private Mødeholder mødeholder;


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

        starttid_txt = findViewById(R.id.opretmoeder_starttid_txt);
        starttid_txt.setOnClickListener(this);

        sluttid_txt = findViewById(R.id.opretmoeder_sluttid_txt);
        sluttid_txt.setOnClickListener(this);

        dato_txt = findViewById(R.id.opretmoeder_dato_txt);
        dato_txt.setOnClickListener(this);

        mødeNavn = findViewById(R.id.opretmoede_navn_editTxt);
        mødeFormål = findViewById(R.id.opretmoede_formaal_editTxt);
        sted = findViewById(R.id.opretmoeder_sted_editTxt);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.opretmoeder_starttid_txt:
                DialogFragment dialog_starttid = new TidPickerDialog_frg();
                dialog_starttid.show(getSupportFragmentManager(), "tidPicker");
                break;
            case R.id.opretmoeder_sluttid_txt:
                DialogFragment dialog_sluttid = new TidPickerDialog_frg();
                dialog_sluttid.show(getSupportFragmentManager(), "tidPicker");
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
                String uniqueID = UUID.randomUUID().toString();
                Log.d(TAG, "ID er: " + uniqueID);
                Møde møde = new Møde();
                møde.setNavn(mødeNavn.getText().toString());
                Log.d(TAG,møde.getNavn() );
                møde.setFormål(mødeFormål.getText().toString());
                møde.setDato(dato_txt.getText().toString());
                møde.setStartTid(starttid_txt.getText().toString());
                møde.setSlutTid(sluttid_txt.getText().toString());
                møde.setSted(sted.getText().toString());
                møde.setMødeholderID(mAuth.getUid());
                møde.setMødeIDtildeltager(generateRandomString());
                møde.setMødeID(uniqueID);
                møde.setIgang(false);

                mFirestore.collection("Møder").document(uniqueID).set(møde).addOnCompleteListener(new OprettetListener(møde));

                break;

        }
    }
    /*
    Metode der generer ranbdom string på 4 cifre, som deltager skal indtaste
     */
    public String generateRandomString(){

        String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz1234567890";
        int RANDOM_STRING_LENGTH = 4;

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * Laver random nummer
     * @return int
     */
    private int getRandomNumber() {
        String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz1234567890";
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }


    class OprettetListener implements OnCompleteListener<Void>{

        private final Møde møde;

        private OprettetListener(Møde møde){
            this.møde = møde;
        }

        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Log.d(TAG, "Mødet er oprettet og aktiviteten lukkes");
                PersonData.getInstance().tilføjMøde(møde);
                finish();
            }
            else {
                Log.w(TAG, "Mødet blev ikke oprettet!", task.getException());
            }
        }
    }
}
