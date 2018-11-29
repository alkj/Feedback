package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;


public class Hjem_frg extends Fragment {



    private TextView fraFire, fraFire2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hjem, container, false);

        fraFire = v.findViewById(R.id.textViewFire);
        fraFire2 = v.findViewById(R.id.textViewfraFire2);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        opdaterTekst();
    }

    private void opdaterTekst(){
        Mødeholder mødeholder = PersonData.getInstance().getMødeholder();
        fraFire.setText("Dit navn er "+mødeholder.getFornavn());
        fraFire2.setText("Din mail er "+mødeholder.getEmail());
    }

}
