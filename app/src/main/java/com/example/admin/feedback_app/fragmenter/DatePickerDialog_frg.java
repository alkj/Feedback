package com.example.admin.feedback_app.fragmenter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.feedback_app.R;

import java.util.Calendar;

public class DatePickerDialog_frg extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar kalender = Calendar.getInstance();

        int år = kalender.get(Calendar.YEAR);
        int måned = kalender.get(Calendar.MONTH);
        int dag = kalender.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, år, måned, dag);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int år, int måned, int dag) {
        String s = String.format(("%d-%d-%d"), dag, måned, år);

        ((TextView) getActivity().findViewById(R.id.opretmoeder_dato_txt)).setText(s);
    }
}
