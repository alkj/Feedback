package com.example.admin.feedback_app.fragmenter;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.feedback_app.R;


public class Moedeoversigt_frg extends Fragment implements View.OnClickListener {

    View afholdte_btn, ikkeAfholdte_btn, opretmoede_btn;
    TextView afholdte_txt, ikkeAfholdte_txt;
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

        //View/Knapper
        afholdte_btn = view.findViewById(R.id.moede_afholdt_btn);
        ikkeAfholdte_btn = view.findViewById(R.id.moede_ikkeAfholdt_btn);
        opretmoede_btn = view.findViewById(R.id.moede_opret_btn);

        afholdte_btn.setOnClickListener(this);
        ikkeAfholdte_btn.setOnClickListener(this);
        opretmoede_btn.setOnClickListener(this);

        //Tekst
        afholdte_txt = view.findViewById(R.id.moede_afholdt_txt);
        ikkeAfholdte_txt = view.findViewById(R.id.moede_ikkeafholdt_txt);

        ikkeAfholdte_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        //Drawables
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

        //TODO: opret xml fil til at ændre udseende på afholdte og ikke afholdte
        if (view == ikkeAfholdte_btn) {
            afholdte_btn.setBackground(unselected_bg);
            ikkeAfholdte_btn.setBackground(selected_bg);

            afholdte_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ikkeAfholdte_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            indlæsFragment(new IkkeAfholdt_frg());

        }
        else if (view == afholdte_btn) {
            afholdte_btn.setBackground(selected_bg);
            ikkeAfholdte_btn.setBackground(unselected_bg);

            afholdte_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ikkeAfholdte_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            indlæsFragment(new Afholdt_frg());
        }
        else if ( view == opretmoede_btn){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navigation_fragment_container, new OpretMoede_frg())
                    .commit();
        }

    }
}
