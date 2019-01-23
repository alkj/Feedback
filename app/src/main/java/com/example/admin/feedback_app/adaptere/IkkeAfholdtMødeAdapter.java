package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;
import com.example.admin.feedback_app.views.HorizontalStackedBarChart;

import java.util.List;

public class IkkeAfholdtMødeAdapter extends BaseAdapter {
    private final int[] colors;
    private Context context;
    private List<Møde> mødeList;

    public IkkeAfholdtMødeAdapter(Context context, List<Møde> mødeList){
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

        view.findViewById(R.id.list_chart).setVisibility(View.GONE);

        tv_mødenavn.setText(møde.getNavn());
        tv_mødeID.setText("Møde-ID:  " + møde.getMødeIDtildeltager());
        tv_mødedato.setText(møde.getDato());

        return view;
    }
}
