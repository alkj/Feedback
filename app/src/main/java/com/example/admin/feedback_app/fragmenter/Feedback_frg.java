package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.feedback_app.FeedbackManager;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;
import com.example.admin.feedback_app.aktiviteter.Overholdt_Giv_Feedback_akt;


public class Feedback_frg extends Fragment implements View.OnClickListener {

    private static String TAG = "Feedback fragment";

    private ImageView[] knapperHumoer;
    private EditText editTextuddybning;
    private TextView textViewSpørgsmål, textViewIndeks;

    private static final String
            SPØRGSMÅL_TEKST = "Spørgsmål",
            NUMMER = "Nummer";

    private String spørgsmålTilFragment;
    private int nummer;
    private final float ALPHA_VALGT = 1f, ALPHA_IKKE_VALGT = 0.5f;
    private FeedbackManager feedbackManager;
    private Svar svar;

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
    public static Feedback_frg newInstance(int nummer, String spørgsmål) {
        Feedback_frg fragment = new Feedback_frg();
        Bundle args = new Bundle();
        args.putString(SPØRGSMÅL_TEKST, spørgsmål);
        args.putInt(NUMMER, nummer);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: nyt fragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spørgsmålTilFragment = getArguments().getString(SPØRGSMÅL_TEKST);
            nummer = getArguments().getInt(NUMMER);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        //EditText
        editTextuddybning = view.findViewById(R.id.feedback_udybning_editTxt);
        editTextuddybning.setVisibility(View.GONE);
        editTextuddybning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                svar.setTekst(editTextuddybning.getText().toString());
                feedbackManager.indsætFeedback(nummer, svar);
            }
        });

        //TextView
        textViewSpørgsmål = view.findViewById(R.id.feedback_spørgsmål_txtView);
        textViewSpørgsmål.setText(spørgsmålTilFragment);

        //Knapper
        knapperHumoer = new ImageView[]{
                view.findViewById(R.id.imageBMegetSur),
                view.findViewById(R.id.imageBSur),
                view.findViewById(R.id.imageBTilfreds),
                view.findViewById(R.id.imageBMegetTilfreds)
        };

        for (ImageView knap : knapperHumoer){
            knap.setAlpha(ALPHA_IKKE_VALGT);
            knap.setOnClickListener(this);
        }

        //Feedback
        feedbackManager = FeedbackManager.getInstance();
        if (feedbackManager.erFeedbackUdfyldt(nummer)){
            svar = feedbackManager.hentFeedback(nummer);
            knapperHumoer[svar.getSmiley()-1].setAlpha(ALPHA_VALGT);
            editTextuddybning.setVisibility(View.VISIBLE);
            editTextuddybning.setText(svar.getTekst());
        }
        else {
            svar = new Svar();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < knapperHumoer.length; i++){
            knapperHumoer[i].setAlpha(ALPHA_IKKE_VALGT);
            if (view == knapperHumoer[i]){
                knapperHumoer[i].setAlpha(ALPHA_VALGT);
                svar.setSmiley(i+1);
                feedbackManager.indsætFeedback(nummer, svar);
            }
            editTextuddybning.setVisibility(View.VISIBLE);
        }
    }

}
