package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;

public class Afholdt_MoedeInformationer_akt extends AppCompatActivity {

    private Møde
            møde;

    private TextView
            moedeNavn, moedeFormaal, moedeSted, tidStart, tidSlut, inviterede, fremmoedte, moedeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afholdt__moede_informationer_akt);
    }
}
