package dk.spinoff.apps.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;
import java.util.UUID;

import dk.spinoff.apps.feedback_app.Møde;
import dk.spinoff.apps.feedback_app.PersonData;
import dk.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.VibratorManager;
import dk.spinoff.apps.feedback_app.dialogs.DatoPickerDialog_frg;
import dk.spinoff.apps.feedback_app.dialogs.TidPickerDialog_frg;


public class OpretMoede_2_frg extends Fragment implements View.OnClickListener {

    private String mødenavnS, mødeformålS, stedS;
    private TextView starttid_txt, sluttid_txt;
    private TextView dato_txt;
    private Button opretMoede_knap;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private DialogFragment dialog_starttid, dialog_sluttid, dialog_dato;

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

        opretMoede_knap = v.findViewById(R.id.videreBTN);
        opretMoede_knap.setOnClickListener(this);

        starttid_txt = v.findViewById(R.id.tidStart);
        starttid_txt.setOnClickListener(this);

        sluttid_txt = v.findViewById(R.id.tidSlut);
        sluttid_txt.setOnClickListener(this);

        dato_txt = v.findViewById(R.id.dato);
        dato_txt.setOnClickListener(this);

        Bundle bundle = new Bundle();
        bundle.putInt(TidPickerDialog_frg.VIEW_ID, R.id.tidStart);
        dialog_starttid = new TidPickerDialog_frg();
        dialog_starttid.setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt(TidPickerDialog_frg.VIEW_ID, R.id.tidSlut);
        dialog_sluttid = new TidPickerDialog_frg();
        dialog_sluttid.setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt(DatoPickerDialog_frg.VIEW_ID, R.id.dato);
        dialog_dato = new DatoPickerDialog_frg();
        dialog_dato.setArguments(bundle);


        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tidStart:
                dialog_starttid.show(getActivity().getSupportFragmentManager(), "tidStartPicker");
                break;
            case R.id.tidSlut:
                dialog_sluttid.show(getActivity().getSupportFragmentManager(), "tidSlutPicker");
                break;
            case R.id.dato:
                dialog_dato.show(getActivity().getSupportFragmentManager(), "datoPicker");
                break;
            case R.id.videreBTN:

                if(!validering()){
                    VibratorManager.vibrerMønster(getContext(),VibratorManager.FEJL_VIB,-1);
                    return;
                }

                //Log.d(TAG, "Opret møde knappen er trykket");
                String uniqueID = UUID.randomUUID().toString();
                //Log.d(TAG, "ID er: " + uniqueID);
                Møde møde = new Møde();
                møde.setNavn(mødenavnS);
                //Log.d(TAG,møde.getNavn() );
                møde.setFormål(mødeformålS);
                møde.setDato(dato_txt.getText().toString());
                møde.setStartTid(starttid_txt.getText().toString());
                møde.setSlutTid(sluttid_txt.getText().toString());
                møde.setSted(stedS);
                møde.setMødeholderID(mAuth.getUid());
                møde.setMødeIDtildeltager(generateRandomString());
                møde.setMødeID(uniqueID);
                møde.setIgang(false);
                PersonData.getInstance().tilføjMøde(møde);



                mFirestore.collection("Møder").document(uniqueID).set(møde).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),
                                    "Møde oprettet",
                                    Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();


                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Fejl med oprettelse af møde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                break;
        }
    }

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
    private boolean validering(){
        boolean valid = true;

        if (TextUtils.isEmpty(this.dato_txt.getText().toString())) {
            this.dato_txt.setError("Indtast mødets dato");
            valid = false;
        }
        if (TextUtils.isEmpty(this.starttid_txt.getText().toString())) {
            this.starttid_txt.setError("Indtast mødets start tidspunkt");
            valid = false;
        }
        if (TextUtils.isEmpty(this.sluttid_txt.getText().toString())) {
            this.sluttid_txt.setError("Indtast mødets slut tidspunkt");
            valid = false;
        }
        return valid;
    }

}
