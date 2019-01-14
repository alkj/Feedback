package com.example.admin.feedback_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class fragment_feedback_smiley extends Fragment implements View.OnClickListener {


    public interface OnButtonClickedFeedbackSmiley {
        void submit(int i);
    }


    private OnButtonClickedFeedbackSmiley onButtonClickedFeedbackSmiley;
    public ImageView imageViewMegetTilfreds;
    public ImageView imageViewTilfreds;
    public ImageView imageViewSur;
    public ImageView imageViewMegetSur;
    public TextView textViewFeedback;

    private TextView textViewSpørgsmål;
    private TextView textViewSideNummer;

    private static final String PIC = "feedback";

    private int nummer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_fragment_feedback_smiley, container, false);
        Log.d("debug", "fragment_feedback_smiley: OnCreate" + nummer);


        textViewSideNummer = rod.findViewById(R.id.feedback_nummer_txtView);
        textViewSpørgsmål = rod.findViewById(R.id.textViewFeedbackBeskrivelse);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.nummer = bundle.getInt("nummer");
        }

        Log.d("TAG", "onCreateView: " + nummer);


        textViewSideNummer.setText("side " + nummer + " ud af 4"); //TODO nummer

        switch (nummer){
            case 1:
                textViewSpørgsmål.setText(R.string.spørgsmål_til_feedback2);
                break;
            case 2:
                textViewSpørgsmål.setText(R.string.spørgsmål_til_feedback1);
                break;
            case 3:
                textViewSpørgsmål.setText(R.string.spørgsmål_til_feedback3);
                break;
            case 4:
                textViewSpørgsmål.setText(R.string.spørgsmål_til_feedback4);
                break;
        }


        imageViewMegetTilfreds = rod.findViewById(R.id.imageBMegetTilfreds);
        imageViewTilfreds = rod.findViewById(R.id.imageBTilfreds);
        imageViewSur = rod.findViewById(R.id.imageBSur);
        imageViewMegetSur = rod.findViewById(R.id.imageBMegetSur);
        textViewFeedback = rod.findViewById(R.id.opretMoedeTxtView);

        imageViewMegetTilfreds.setOnClickListener(this);
        imageViewTilfreds.setOnClickListener(this);
        imageViewSur.setOnClickListener(this);
        imageViewMegetSur.setOnClickListener(this);

        return rod;
    }


    @Override
    public void onClick(View v) {

        int humoer = 0;

        if (v == imageViewMegetTilfreds) {
            humoer =1;
        } else if (v == imageViewTilfreds) {
            humoer =2;
        } else if (v == imageViewSur) {
            humoer =3;
        } else if (v == imageViewMegetSur) {
            humoer =4;
        }

        onButtonClickedFeedbackSmiley.submit(humoer);



        Log.i("frag", "onClick: something clicked ");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickedFeedbackSmiley){
            onButtonClickedFeedbackSmiley = (OnButtonClickedFeedbackSmiley) context;
        } else {
            throw new RuntimeException(context.toString() + "implement onButtonClickedFeedBackSmiley");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onButtonClickedFeedbackSmiley = null;
    }

}