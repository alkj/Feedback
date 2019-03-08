package com.spinoff.apps.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spinoff.apps.feedback_app.Møde;
import com.spinoff.apps.feedback_app.PersonData;
import com.spinoff.apps.feedback_app.R;
import com.spinoff.apps.feedback_app.Svar;
import com.spinoff.apps.feedback_app.views.HorizontalStackedBarChart;

import java.util.List;

public class AfholdtMødeAdapter extends BaseAdapter {
    private final int[] colors;
    private Context context;
    private List<Møde> mødeList;

    public AfholdtMødeAdapter (Context context, List<Møde> mødeList){
        this.context = context;
        this.mødeList = mødeList;
        this.colors = new int[] {
                context.getColor(R.color.colorMegetSur),
                context.getColor(R.color.colorSur),
                context.getColor(R.color.colorGlad),
                context.getColor(R.color.colorMegetGlad)
        };
    }

    @Override
    public int getCount() {
        return mødeList.size();
    }

    @Override
    public Object getItem(int i) {
        return mødeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Møde møde = (Møde) getItem(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row_item_moede, null);
        }

        TextView tv_mødenavn = view.findViewById(R.id.list_moednavn);
        TextView tv_mødedato = view.findViewById(R.id.list_moededato);
        TextView tv_mødeID = view.findViewById(R.id.list_moedeID);

        HorizontalStackedBarChart barChart = view.findViewById(R.id.list_chart);

        barChart.setColors(colors);
        int antal_1 = 0, antal_2 = 0, antal_3 = 0, antal_4 = 0;

        List<List<Svar>> feedback = PersonData.getInstance().getFeedbackTilMøde(møde.getMødeID());

        if (feedback != null){
            for (List<Svar> person : feedback){
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
        }

        barChart.setValues(new int[] {
                antal_1,
                antal_2,
                antal_3,
                antal_4
        });

        tv_mødenavn.setText(møde.getNavn());
        tv_mødeID.setText(møde.getDato());
        tv_mødedato.setText("");

        return view;
    }
}
