package com.example.admin.feedback_app.aktiviteter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.admin.feedback_app.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class AfholdtOverordnet_akt extends AppCompatActivity implements View.OnClickListener {

    private BarChart chart;
    private Button tilbage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_overordnet_akt);

        tilbage = findViewById(R.id.afholdtOverodnetTilbage);
        tilbage.setOnClickListener(this);

        setTitle("StackedBarActivity");

        chart = findViewById(R.id.barChart);

        chart.getDescription().setEnabled(false);

        /*sætter maks entry værdi - hvis der er flere entries (data) end maks værdien, vil ingen
        værdier blive tegnet*/
        //chart.setMaxVisibleValueCount(40);

        //Der kan nu kun zoomes ind på x og y aksen separat og ikke samtidig
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(R.color.colorMørkegrå);
        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);


        //Ændrer y-akse
        YAxis leftAxis = chart.getAxisLeft();
        chart.getAxisRight().setEnabled(false);
        leftAxis.setAxisMinimum(1f); // ærstatter setStartAtZero(true)
        leftAxis.setTextColor(Color.WHITE);

        //Ændrer x-akse
        XAxis xLabels = chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);
        xLabels.setTextColor(Color.WHITE);

        // chart.setDrawXLabels(false);
        // chart.setDrawYLabels(false);

        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(10f);
        legend.setFormToTextSpace(4f);
        legend.setXEntrySpace(6f);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(10f);

        // chart.setDrawLegend(false);


        float smileyMegetTilfreds, smileyTilfreds, smileyUtilfreds, smileyMegetUtilfreds;
        smileyMegetTilfreds = 6f;
        smileyTilfreds = 2f;
        smileyUtilfreds = 2f;
        smileyMegetUtilfreds = 5f;

        float[] feedbackArray = new float[] {
                smileyMegetTilfreds,
                smileyTilfreds,
                smileyUtilfreds,
                smileyMegetUtilfreds};

        //Arrayliste som indeholder entries (data) til bare charten
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(1f, new float[] {10, 2, 1, 1}));
        values.add(new BarEntry(2f, new float[] {0, 0, 10,4}));
        values.add(new BarEntry(3f, new float[] {2, 7, 4, 1}));
        values.add(new BarEntry(4f, new float[] {2, 4, 6, 2}));
        values.add(new BarEntry(5f, feedbackArray));
        values.add(new BarEntry(6f, new float[] {5, 5, 1, 3}));
        values.add(new BarEntry(7f, new float[] {1, 8, 2, 1}));
        values.add(new BarEntry(8f, new float[] {14, 0, 0, 0}));


        BarDataSet dataSet;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(values, "");
            dataSet.setDrawIcons(false);
            dataSet.setColors(getColors());
            dataSet.setStackLabels(new String[]{"Meget Tilfred", "Tilfreds", "Utilfreds", "Meget Utilfreds"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 1));
            data.setValueTextColor(Color.WHITE);

            chart.setData(data);
        }

        chart.setFitBars(true); // få x-aksen til at tilpasse ligenøjagtig alle barer
        chart.invalidate(); // refresh


    }

    private int[] getColors() {

        int[] colors = {
                getColor(R.color.colorMegetGlad),
                getColor(R.color.colorGlad),
                getColor(R.color.colorSur),
                getColor(R.color.colorMegetSur)
        };
        //System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);

        return colors;
    }

    @Override
    public void onClick(View view) {
        if (view == tilbage) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
