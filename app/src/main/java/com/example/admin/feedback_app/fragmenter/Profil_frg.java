package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.feedback_app.R;
import com.google.firebase.auth.FirebaseAuth;


public class Profil_frg extends Fragment implements View.OnClickListener {
    private Button logUd;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod = inflater.inflate(R.layout.fragment_profil, container, false);

        mAuth = FirebaseAuth.getInstance();

        logUd = rod.findViewById(R.id.logUd);
        logUd.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(logUd.getId()==i){
            mAuth.signOut();
            this.getActivity().finish();
        }
    }
}
