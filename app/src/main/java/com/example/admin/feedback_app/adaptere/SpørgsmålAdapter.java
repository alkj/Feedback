package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.views.HorizontalStackedBarChart;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpørgsmålAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private HashMap<String, List<String>> childMap;
    private int[] colors;

    public SpørgsmålAdapter(Context context, List<String> groupList, HashMap<String, List<String>> childMap) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = childMap;
        this.colors = new int[] {
                context.getColor(R.color.colorMegetSur),
                context.getColor(R.color.colorSur),
                context.getColor(R.color.colorGlad),
                context.getColor(R.color.colorMegetGlad)
        };
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
        String tekst = (String) getChild(groupPosition,childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exlist_child_item_kom, null);
        }

        ((TextView)convertView.findViewById(R.id.exlist_child_txtView))
                .setText(tekst);

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

        Random random = new Random();
        barChart.setValues(new int[] {
                random.nextInt(10)+1,
                random.nextInt(10)+1,
                random.nextInt(10)+1,
                random.nextInt(10)+1
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
