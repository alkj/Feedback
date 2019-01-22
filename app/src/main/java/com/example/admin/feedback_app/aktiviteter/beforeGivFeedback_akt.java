package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.R;

public class beforeGivFeedback_akt extends AppCompatActivity {

    private Button givFeed;

    private TextView sted, dato, navn, mødeHolderNavn, formål, tidStart, tidSlut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_giv_feedback_akt);

        navn = findViewById(R.id.navnTV);
        sted = findViewById(R.id.stedTV);
        dato = findViewById(R.id.datoTV);

        formål = findViewById(R.id.formål);
        tidStart = findViewById(R.id.tidStart);
        tidSlut = findViewById(R.id.tidSlut);

        String navnString = getIntent().getStringExtra("NAVN");
        String datoString = getIntent().getStringExtra("DATO");
        String stedString = getIntent().getStringExtra("STED");
        //String mødeHolderNavnString = getIntent().getStringExtra("MØDEHOLDERNAVN");
        String formålString = getIntent().getStringExtra("FORMÅL");
        String tidStartString = getIntent().getStringExtra("TIDSTART");
        String tidSlutString = getIntent().getStringExtra("TIDSLUT");

        navn.setText(navnString);
        dato.setText(datoString);
        sted.setText(stedString);
        //mødeHolderNavn.setText(mødeHolderNavnString);
        formål.setText(formålString);
        tidStart.setText(tidStartString);
        tidSlut.setText(tidSlutString);

        givFeed = findViewById(R.id.buttonStartFeed);
        givFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Overholdt_Giv_Feedback_akt.class);
                intent.putExtra("MØDEID", getIntent().getStringExtra("MØDEID"));
                intent.putExtra("MØDEIDdel",getIntent().getStringExtra("MØDEIDdel"));
                startActivity(intent);
                finish();

            }
        });

    }





}
