package com.example.admin.feedback_app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class Moedeoversigt_frg extends Fragment implements View.OnClickListener {

    LinearLayout afholdte_btn, ikkeAfholdte_btn;
    Drawable selected_bg, unselected_bg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moedeoversigt, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        indlæsFragment(new IkkeAfholdt_frg());

        afholdte_btn = (LinearLayout)view.findViewById(R.id.moede_afholdt_btn);
        ikkeAfholdte_btn = (LinearLayout)view.findViewById(R.id.moede_ikkeAfholdt_btn);

        afholdte_btn.setOnClickListener(this);
        ikkeAfholdte_btn.setOnClickListener(this);

        Resources res = getResources();
        selected_bg = res.getDrawable(R.drawable.moeder_tab_selected_bg, null);
        unselected_bg = res.getDrawable(R.drawable.moeder_tab_unselected_bg, null);
    }

    private void indlæsFragment(Fragment fragment){
        if (fragment != null){

            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moede_fragHolder, fragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {

        if (view == ikkeAfholdte_btn) {
            afholdte_btn.setBackground(unselected_bg);
            ikkeAfholdte_btn.setBackground(selected_bg);
            indlæsFragment(new IkkeAfholdt_frg());

        }
        else if (view == afholdte_btn) {
            afholdte_btn.setBackground(selected_bg);
            ikkeAfholdte_btn.setBackground(unselected_bg);
            indlæsFragment(new Afholdt_frg());
        }

    }
}
