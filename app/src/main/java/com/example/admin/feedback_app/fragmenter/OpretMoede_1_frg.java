package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.feedback_app.R;


public class OpretMoede_1_frg extends Fragment{

    private EditText mødeNavn, mødeFormål, sted;
    private Button videre;
    String smødeNavn, smødeFormål, ssted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_opretmoede_1, container, false);


        videre = v.findViewById(R.id.videreBTN);
        mødeNavn = v.findViewById(R.id.dato);
        mødeFormål = v.findViewById(R.id.tidStart);
        sted = v.findViewById(R.id.tidSlut);

        videre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validering())
                smødeNavn = mødeNavn.getText().toString();
                smødeFormål = mødeFormål.getText().toString();
                ssted = sted.getText().toString();



                Fragment fragment = new OpretMoede_2_frg();
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                bundle.putString("mødenavn",smødeNavn);
                bundle.putString("mødeformål",smødeFormål);
                bundle.putString("sted",ssted);
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("opretmøde_1");
                fragmentTransaction.replace(R.id.navigation_fragment_container,fragment);
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                fragmentTransaction.commit();

            }
        });


        // Inflate the layout for this fragment
        return v;
    }


    private boolean validering(){
        boolean ret = true;

        //TODO lav validering


        return ret;
    }


}
