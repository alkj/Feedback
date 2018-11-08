package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.DatePickerDialog_frg;
import com.example.admin.feedback_app.fragmenter.TidPickerDialog_frg;


public class OpretMoede_akt extends FragmentActivity implements View.OnClickListener {

    TextView tid_txt;
    TextView dato_txt;

    @Override
    protected void onCreate(Bundle savedInstancesBundle) {
        super.onCreate(savedInstancesBundle);

        setContentView(R.layout.activity_opret_moede);

        tid_txt = findViewById(R.id.opretmoeder_tid_txt);
        tid_txt.setOnClickListener(this);

        dato_txt = findViewById(R.id.opretmoeder_dato_txt);
        dato_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.opretmoeder_tid_txt:
                DialogFragment dialog_tid = new TidPickerDialog_frg();
                dialog_tid.show(getSupportFragmentManager(), "tidPicker");
                break;
            case R.id.opretmoeder_dato_txt:
                DialogFragment dialog_dato = new DatePickerDialog_frg();
                dialog_dato.show(getSupportFragmentManager(), "datoPicker");
                break;

        }
    }
}