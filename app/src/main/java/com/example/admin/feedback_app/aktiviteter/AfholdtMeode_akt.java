package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.FeedbackTilFirebase;
import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AfholdtMeode_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde møde;

    private FeedbackTilFirebase feedback;

    private TextView moedeNavn, moedeID;

    private Button moedeInfo_knap, overordnet_knap, dagsorden_Knap, kommentarer_knap;

    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_meode_akt);

        moedeInfo_knap = findViewById(R.id.AfholdtMoedeInfo_knap);
        moedeInfo_knap.setOnClickListener(this);

        overordnet_knap = findViewById(R.id.AfholdtOverordnetStatistik_knap);
        overordnet_knap.setOnClickListener(this);

        dagsorden_Knap = findViewById(R.id.AfholdtDagsorden_knap);
        dagsorden_Knap.setOnClickListener(this);

        kommentarer_knap = findViewById(R.id.AfholdtKommentarer_knap);
        kommentarer_knap.setOnClickListener(this);


        moedeNavn = findViewById(R.id.tvMødeNavn);
        moedeID = findViewById(R.id.tvMødeID);

        //TODO: håndtere at mødet ikke findes
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                int indeks = bundle.getInt("INDEKS");
                møde = PersonData.getInstance().getAfholdteMøder().get(indeks);
            }
        }

        moedeNavn.setText(møde.getNavn());
        moedeID.setText(møde.getFormål());

        pieChart = findViewById(R.id.pieChartStart);

        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(10f);
        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);

        /*
        IValueFormatter percentFormatter = pieChart.getDefaultValueFormatter();
        percentFormatter.getFormattedValue();
        */
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(50f);
        pieChart.setHoleColor(R.color.colorMørkegrå);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(50f);

        pieChart.setCenterText("Feedback i %");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(14);


        /*
        float smiley1 = feedback.getIntSpørgsmål1();
        float smiley2 = feedback.getIntSpørgsmål2();
        float smiley3 = feedback.getIntSpørgsmål3();
        float smiley4 = feedback.getIntSpørgsmål4();
        */

        ArrayList<PieEntry> yVaerdier = new ArrayList<PieEntry>(){};
        yVaerdier.add(new PieEntry(50f, "Meget Tilfreds"));
        yVaerdier.add(new PieEntry(30f, "Tilfreds"));
        yVaerdier.add(new PieEntry(10f, "Utilfreds"));
        yVaerdier.add(new PieEntry(10f, "Meget Utilfreds"));

        PieDataSet dataset = new PieDataSet(yVaerdier, "feedback");
        dataset.setSliceSpace(4f);
        dataset.setSelectionShift(8f);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setLabel("");

        int[] colors = {
                getColor(R.color.colorMegetGlad),
                getColor(R.color.colorGlad),
                getColor(R.color.colorSur),
                getColor(R.color.colorMegetSur)
        };
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        data.setValueTextSize(10f);
        data.setValueTypeface(Typeface.DEFAULT_BOLD);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

    }

    @Override
    public void onClick(View view) {

        if (view == moedeInfo_knap) {
            Intent intent = new Intent (this, Afholdt_MoedeInformationer_akt.class);
            startActivity(intent);
        }
        else if (view == overordnet_knap) {
            Intent intent = new Intent (this, AfholdtOverordnet_akt.class);
            startActivity(intent);
        }
        else if (view == dagsorden_Knap) {

        }
        else if (view == kommentarer_knap) {

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
