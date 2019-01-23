package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;
import com.example.admin.feedback_app.views.HorizontalStackedBarChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpørgsmålAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private HashMap<String,List<Svar>> childMap;
    private int[] colors;
    private List<List<Svar>> childListe;

    public SpørgsmålAdapter(Context context, List<String> groupList, List<List<Svar>> childListe) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = bygMap(childListe);
        this.childListe = childListe;
        this.colors = new int[] {
                context.getColor(R.color.colorMegetSur),
                context.getColor(R.color.colorSur),
                context.getColor(R.color.colorGlad),
                context.getColor(R.color.colorMegetGlad)
        };


    }

    private HashMap<String, List<Svar>> bygMap(List<List<Svar>> feedback){
        HashMap<String,List<Svar>> map = new HashMap<>();

        for (int i = 0; i < groupList.size() ; i++){
            List<Svar> svar = new ArrayList<>();
            for (List<Svar> person : feedback) {
                Svar nsvar = person.get(i);
                if (nsvar.getTekst() != null && nsvar.getTekst().length() > 0)
                    svar.add(nsvar);

            }
            map.put(groupList.get(i),svar);

        }

        return map;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childMap.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMap.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Svar svar = (Svar) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exlist_child_item_kom, null);
        }

        ((TextView)convertView.findViewById(R.id.exlist_child_txtView))
                .setText(svar.getTekst());

        View farve = convertView.findViewById(R.id.exlist_child_farve);

        farve.setBackgroundColor(colors[svar.getSmiley()-1]);

        return convertView;
    }


    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String tekst = (String) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exlist_group_item_kom, null);
        }

        ((TextView)convertView.findViewById(R.id.exlist_group_txtView))
                .setText(tekst);

        HorizontalStackedBarChart barChart = convertView.findViewById(R.id.exlist_group_bar);

        barChart.setColors(colors);
        int antal_1 = 0, antal_2 = 0, antal_3 = 0, antal_4 = 0;
        for (List<Svar> person : childListe ){
            Svar svar = person.get(groupPosition);
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

        barChart.setValues(new int[] {
                antal_1,
                antal_2,
                antal_3,
                antal_4
        });

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
