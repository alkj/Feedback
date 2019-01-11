package com.example.admin.feedback_app.adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;

import java.util.List;

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

            mødenavn.setText(møde.getNavn());
            mødedato.setText(møde.getDato());
        }

        return view;
    }
}
