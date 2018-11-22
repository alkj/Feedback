package com.example.admin.feedback_app;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class fragment_feedback_smiley extends Fragment implements View.OnClickListener {


    public interface OnButtonClickedFeedbackSmiley {
        void submit(int i);
    }


    private OnButtonClickedFeedbackSmiley onButtonClickedFeedbackSmiley;
    public ImageButton imageViewMegetTilfreds;
    public ImageButton imageViewTilfreds;
    public ImageButton imageViewSur;
    public ImageButton imageViewMegetSur;
    public TextView textViewFeedback;

    private static final String PIC = "feedback";

    public fragment_feedback_smiley() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_fragment_feedback_smiley, container, false);

        imageViewMegetTilfreds = rod.findViewById(R.id.imageBMegetTilfreds);
        imageViewTilfreds = rod.findViewById(R.id.imageBTilfreds);
        imageViewSur = rod.findViewById(R.id.imageBSur);
        imageViewMegetSur = rod.findViewById(R.id.imageBMegetSur);
        textViewFeedback = rod.findViewById(R.id.textView);

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