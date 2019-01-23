package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.admin.feedback_app.FeedbackSpørgsmål;
import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.adaptere.SpørgsmålAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AfholdtMeode_akt extends AppCompatActivity {

    private TextView mødenavn, dato;
    private Møde møde;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_meode_akt);

        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                int indeks = bundle.getInt("INDEKS");
                møde = PersonData.getInstance().getAfholdteMøder().get(indeks);

            }
        }
        mødenavn = findViewById(R.id.afholdt_mødenavn);
        mødenavn.setText(møde.getNavn());

        dato = findViewById(R.id.tvDato_Afholdt);
        dato.setText(møde.getDato());


        ExpandableListView listView = findViewById(R.id.afhold_kommentarer);

        List<String> grouper = Arrays.asList(FeedbackSpørgsmål.SPØRGSMÅL);

        SpørgsmålAdapter adapter = new SpørgsmålAdapter(this, grouper, PersonData.getInstance().getFeedbackTilMøde(møde.getMødeID()));

        listView.setAdapter(adapter);

    }

    private HashMap<String, List<String>> hentDummyData(List<String> grouper){
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : grouper){
            List<String> undergrouper = new ArrayList<>();
            int antal = new Random().nextInt(6);

            for(int i = 0; i <= antal; i++){
                undergrouper.add("Kommentar " + (i+1));
            }

            map.put(s,undergrouper);
        }

        return map;
    }

}
