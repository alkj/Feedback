package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.R;

public class beforeGivFeedback_akt extends AppCompatActivity {

    private Button givFeed;

    private TextView dagsorden, sted, dato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_giv_feedback_akt);

        dagsorden = findViewById(R.id.dagsordenTV);
        sted = findViewById(R.id.stedTV);
        dato = findViewById(R.id.datoTV);

        String dagsordenString = getIntent().getStringExtra("DAGSORDEN");
        String datoString = getIntent().getStringExtra("DATO");
        String stedString = getIntent().getStringExtra("STED");

        dagsorden.setText(dagsordenString);
        dato.setText(datoString);
        sted.setText(stedString);

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
