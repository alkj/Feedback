package dk.spinoff.apps.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dk.spinoff.apps.feedback_app.Møde;
import dk.spinoff.apps.feedback_app.PersonData;
import dk.spinoff.apps.feedback_app.R;

public class AfholdtMoedeInfo_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde møde;

    private TextView textViewMoedeNavn, textViewMoedeID, textViewFormaal, textViewSted, textViewDato, textViewPlanlagtTid, textViewAktuelTid;
    private Button buttonTilbage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_moede_info);

        textViewMoedeNavn = (TextView) findViewById(R.id.afholdtMoedeInfo_moedeNavn_textView);
        textViewMoedeID = (TextView) findViewById(R.id.afholdtMoedeInfo_MoedeID_TextView);
        textViewFormaal = (TextView) findViewById(R.id.afholdtMoedeInfo_formaal_textView);
        textViewSted = (TextView) findViewById(R.id.afholdtMoedeInfo_sted_TextView);
        textViewDato = (TextView) findViewById(R.id.afholdtMoedeInfo_dato_textView);
        textViewPlanlagtTid = (TextView) findViewById(R.id.afholdtMoedeInfo_planlagtTidspunkt_textView);
        textViewAktuelTid = (TextView) findViewById(R.id.afholdtMoedeInfo_AktueltTidspunkt_textView);
        buttonTilbage = (Button) findViewById(R.id.afholdtMoedeInfo_afslut_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int indeks = bundle.getInt("INDEKS");
            møde = PersonData.getInstance().getAfholdteMøder().get(indeks);
        }

        textViewMoedeNavn.setText(bundle.getString("MODENAVN"));
        textViewMoedeID.setText(bundle.getString("MODEID"));
        textViewFormaal.setText(bundle.getString("FORMAL"));
        textViewSted.setText(bundle.getString("STED"));
        textViewDato.setText(bundle.getString("DATO"));

        String start = bundle.getString("PLANLAGTTIDSTART");
        String slut = bundle.getString("PLANLAGTTIDSLUT");
        textViewPlanlagtTid.setText(start + " - " + slut);

        String aktStart = bundle.getString("AKTUELTIDSTART");
        String aktSlut = bundle.getString("AKTUELTIDSLUT");
        textViewAktuelTid.setText(aktStart + " - " + aktSlut);

        buttonTilbage.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

