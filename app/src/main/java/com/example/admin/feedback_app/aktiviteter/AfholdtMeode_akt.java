package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;

public class AfholdtMeode_akt extends AppCompatActivity {

    private Møde møde;

    private TextView moedeNavn, moedeID;

    private Button moedeInfo, overordnet_knap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt_meode_akt);
    }
}
