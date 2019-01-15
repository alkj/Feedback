package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AfholdtMeode_akt extends AppCompatActivity implements View.OnClickListener {

    private Møde møde;

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
        moedeID.setText(møde.getMødeID());

        pieChart = findViewById(R.id.pieChartStart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(R.color.colorHvid);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>(){};
        yValues.add(new PieEntry(5f, "meget tilfreds"));
        yValues.add(new PieEntry(2f, "tilfreds"));
        yValues.add(new PieEntry(14f, "utilfreds"));
        yValues.add(new PieEntry(2f, "meget utilfreds"));

        PieDataSet dataset = new PieDataSet(yValues, "feedback");
        dataset.setSliceSpace(4f);
        dataset.setSelectionShift(5f);

        int[] colors = {
                getColor(R.color.colorMegetGlad),
                getColor(R.color.colorGlad),
                getColor(R.color.colorSur),
                getColor(R.color.colorMegetSur)
        };
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.GREEN);

        pieChart.setData(data);

    }

    @Override
    public void onClick(View view) {

        if (view == moedeInfo_knap) {
            Intent intent = new Intent (this, Afholdt_MoedeInformationer_akt.class);
            startActivity(intent);
        }
        else if (view == overordnet_knap) {

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
