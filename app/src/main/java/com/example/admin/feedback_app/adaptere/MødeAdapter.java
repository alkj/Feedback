package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;
import com.example.admin.feedback_app.views.HorizontalStackedBarChart;

import java.util.List;
import java.util.Random;

public class MødeAdapter extends ArrayAdapter<Møde> {
    private final int layoutId;
    private int antal_1, antal_2, antal_3, antal_4;


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
                /*løber igennem alle svarene fra samtlige deltager og sørger for at tælle op af hvor mange af
                de forskellige smileys der er givet 
                 */

                for (List<Svar> person : PersonData.getInstance().getFeedbackTilMøde(møde.getMødeID())){
                    for (Svar svar : person){
                        if (svar.getSmiley()==1){
                            antal_1++;
                        }
                        else if (svar.getSmiley()==2){
                            antal_2++;
                        }
                        else if (svar.getSmiley()==3){
                            antal_3++;
                        }
                        else if (svar.getSmiley()==4){
                            antal_4++;
                        }

                    }

                }
                int[] colors = {
                        getContext().getColor(R.color.colorMegetSur),
                        getContext().getColor(R.color.colorSur),
                        getContext().getColor(R.color.colorGlad),
                        getContext().getColor(R.color.colorMegetGlad)
                };
                barChart.setColors(colors);

                Random random = new Random();
                barChart.setValues(new int[] {
                        antal_1,
                        antal_2,
                        antal_3,
                        antal_4
                });
            }
            else
                barChart.setVisibility(View.GONE);


            mødenavn.setText(møde.getNavn());
            mødeID.setText(møde.getDato());
            mødedato.setText("");

            if (!møde.isAfholdt()){
                mødeID.setText("Møde-ID:  " + møde.getMødeIDtildeltager());
                mødedato.setText(møde.getDato());
            }
        }

        return view;
    }
}
