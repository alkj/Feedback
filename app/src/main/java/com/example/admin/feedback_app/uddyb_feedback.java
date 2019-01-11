package com.example.admin.feedback_app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.zip.Inflater;

public class uddyb_feedback extends Fragment implements View.OnClickListener {

    private static final String IMAGE = "image";

    private ImageView imageButton;
    private Button buttonSendFeedback, buttonSpringOver;
    int humoer;
    Drawable drawable;
    public int nummer;
    public uddyb_feed uddyb_feed;
    private EditText editTextSvar;


    public interface uddyb_feed{
        int feedbackSendt(int i, String s);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.uddyb_feedback, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.humoer = bundle.getInt("humoer");
        }

        imageButton = r.findViewById(R.id.imageButton);
        buttonSendFeedback = r.findViewById(R.id.buttonIndsendFeedback);
        buttonSpringOver = r.findViewById(R.id.buttonSpringOver);
        editTextSvar = r.findViewById(R.id.editTextUddybSvar);
        imageButton.setOnClickListener(this);
        buttonSendFeedback.setOnClickListener(this);
        buttonSpringOver.setOnClickListener(this);

        updater(humoer);

        return r;
    }




    public void updater(int humoer) {
        if (humoer == 1) {
            imageButton.setImageDrawable(getContext().getDrawable(R.drawable.meget_glad));
        } else if (humoer == 2) {
            imageButton.setImageDrawable(getContext().getDrawable(R.drawable.tilfreds));
        } else if (humoer == 3) {
            imageButton.setImageDrawable(getContext().getDrawable(R.drawable.sur));
        } else if (humoer == 4) {
            imageButton.setImageDrawable(getContext().getDrawable(R.drawable.meget_sur));
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if(i==R.id.buttonIndsendFeedback) {
            String s = editTextSvar.getText().toString();
            uddyb_feed.feedbackSendt(humoer, s);
            editTextSvar.setText("");
        }
        if(i==R.id.buttonSpringOver){
            uddyb_feed.feedbackSendt(humoer, "");
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof uddyb_feedback.uddyb_feed){
            uddyb_feed = (uddyb_feedback.uddyb_feed) context;
        } else {
            throw new RuntimeException(context.toString() + "implement uddyb_feed");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        uddyb_feed = null;
    }





}