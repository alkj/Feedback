package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.feedback_app.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IkkeAfholdt_frg.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IkkeAfholdt_frg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IkkeAfholdt_frg extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ikke_afholdt, container, false);
    }

 }
