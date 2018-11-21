package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.aktiviteter.Navigation_akt;
import com.example.admin.feedback_app.aktiviteter.mødeholder;


public class Hjem_frg extends Fragment {



    private TextView fraFire, fraFire2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hjem, container, false);

        fraFire = v.findViewById(R.id.textViewFire);
        fraFire2 = v.findViewById(R.id.textViewfraFire2);

        fraFire.setText("Dit navn er "+Navigation_akt.mødeholder.getFornavn());
        fraFire2.setText("Din mail er "+Navigation_akt.mødeholder.getEmail());

        // Inflate the layout for this fragment
        return v;
    }

}
