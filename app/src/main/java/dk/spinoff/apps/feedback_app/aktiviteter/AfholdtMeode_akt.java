package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import dk.spinoff.apps.feedback_app.FeedbackSpørgsmål;
import dk.spinoff.apps.feedback_app.Møde;
import dk.spinoff.apps.feedback_app.PersonData;
import com.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.adaptere.SpørgsmålAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AfholdtMeode_akt extends BaseActivity implements View.OnClickListener {

    private TextView mødenavn, dato;
    private Møde møde;
    private ImageView buttonInfo;



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

        buttonInfo = findViewById(R.id.afholdt_info_imageView);
        buttonInfo.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        if (v==buttonInfo){

            Intent intent = new Intent(getApplicationContext(), AfholdtMoedeInfo_akt.class);
            intent.putExtra("MODENAVN", møde.getNavn());
            intent.putExtra("MODEID", møde.getMødeIDtildeltager());
            intent.putExtra("FORMAL", møde.getFormål());
            intent.putExtra("STED", møde.getSted());
            intent.putExtra("DATO", møde.getDato().toString());
            intent.putExtra("PLANLAGTTIDSTART", møde.getStartTid());
            intent.putExtra("PLANLAGTTIDSLUT", møde.getSlutTid());

            startActivity(intent);

        }

    }
}
