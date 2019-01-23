package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;

public class ikke_afholdt_moede_rediger_akt extends AppCompatActivity {

    private TextView navn, sted, formål, dato, startTid, slutTid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikke_afholdt_moede_rediger_akt);

        navn = findViewById(R.id.redigerMødenavn);
        sted = findViewById(R.id.redigerMødested);
        formål = findViewById(R.id.redigerMødeformål);
        dato = findViewById(R.id.redigerMødeDato);
        startTid = findViewById(R.id.redigerMødeStartTidspunkt);
        slutTid = findViewById(R.id.redigerMødeSlutTidspunkt);


    }
}
