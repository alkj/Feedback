package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.R;


public class Feedback_frg extends Fragment {

    private static String TAG = "Feedback fragment";

    private Button knapVidere, knapTilbage;
    private EditText editTextuddybning;
    private TextView textViewSpørgsmål, textViewIndeks;

    private static final String
            SPØRGSMÅL_TEKST = "Spørgsmål";

    private String spørgsmålTilFragment;

    public OnFragmentInteractionListener onFragmentInteractionListener;

    public Feedback_frg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Feedback_frg.
     */
    // TODO: Rename and change types and number of parameters
    public static Feedback_frg newInstance(String spørgsmål) {
        Feedback_frg fragment = new Feedback_frg();
        Bundle args = new Bundle();
        args.putString(SPØRGSMÅL_TEKST, spørgsmål);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: nyt fragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spørgsmålTilFragment = getArguments().getString(SPØRGSMÅL_TEKST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        editTextuddybning = view.findViewById(R.id.feedback_udybning_editTxt);
        textViewSpørgsmål = view.findViewById(R.id.feedback_spørgsmål_txtView);

        textViewSpørgsmål.setText(spørgsmålTilFragment);


        //TODO: smiley knapper
        /*knapVidere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFragmentInteractionListener.onFragmentInteraction(4, editTextuddybning.getText().toString());

            }
        });*/

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int smiley, String tekstUddybning);
    }
}
