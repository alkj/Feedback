package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spinoff.apps.feedback_app.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;

public class beforeGivFeedback_akt extends BaseActivity {

    //private Button givFeed;

    private TextView sted, dato, navn, mødeHolderNavn, formål, tidStart, tidSlut;

    private ShimmerButton givFeed;
    private Shimmer shimmer;

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
        final boolean igang = getIntent().getBooleanExtra("igang",false);

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
                if(igang) {

                    Intent intent = new Intent(getApplicationContext(), Overholdt_Giv_Feedback_akt.class);
                    intent.putExtra("MØDEID", getIntent().getStringExtra("MØDEID"));
                    intent.putExtra("MØDEIDdel", getIntent().getStringExtra("MØDEIDdel"));
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Dette møde er ikke i gang", Toast.LENGTH_SHORT).show();
                }

            }
        });

        shimmer = new Shimmer();
        shimmer.start(givFeed);

    }





}
