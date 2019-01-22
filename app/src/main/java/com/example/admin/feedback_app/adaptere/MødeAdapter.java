package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.views.HorizontalStackedBarChart;

import java.util.List;
import java.util.Random;

public class MødeAdapter extends ArrayAdapter<Møde> {
    private final int layoutId;


    public MødeAdapter(Context context, int resource, List<Møde> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutId, null);
        }

        Møde møde = getItem(position);

        if (møde != null){
            TextView mødenavn = view.findViewById(R.id.list_moednavn);
            TextView mødedato = view.findViewById(R.id.list_moededato);
            TextView mødeID = view.findViewById(R.id.list_moedeID);

            HorizontalStackedBarChart barChart = view.findViewById(R.id.list_chart);

            if (møde.isAfholdt()){
                int[] colors = {
                        getContext().getColor(R.color.colorMegetSur),
                        getContext().getColor(R.color.colorSur),
                        getContext().getColor(R.color.colorGlad),
                        getContext().getColor(R.color.colorMegetGlad)
                };
                barChart.setColors(colors);

                Random random = new Random();
                barChart.setValues(new int[] {
                        random.nextInt(10)+1,
                        random.nextInt(10)+1,
                        random.nextInt(10)+1,
                        random.nextInt(10)+1
                });
            }
            else
                barChart.setVisibility(View.GONE);


            mødenavn.setText(møde.getNavn());
            mødeID.setText(møde.getDato());
            mødedato.setText("");

            //sætter nogle ting anderledes iforhold til hvis mødet er afholdt
            if (!møde.isAfholdt()){
                mødeID.setText("Møde-ID:  " + møde.getMødeIDtildeltager());
                mødedato.setText(møde.getDato());
            }
        }

        return view;
    }
}
