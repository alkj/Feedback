package com.example.admin.feedback_app.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.feedback_app.R;

import java.util.Calendar;
import java.util.Objects;

public class TidPickerDialog_frg extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar kalender = Calendar.getInstance();

        int timer = kalender.get(Calendar.HOUR_OF_DAY);
        int minutter = kalender.get(Calendar.MINUTE);
        int theme = R.style.DialogThemeGray;


        return new TimePickerDialog(getActivity(), theme, this, timer, minutter, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int timer, int minutter) {

        String t, m;

        if (timer < 10)
            t = "0" + timer;
        else
            t = Integer.toString(timer);

        if (minutter < 10)
            m = "0" + minutter;
        else
            m = Integer.toString(minutter);

        String s = String.format(getString(R.string.tid_format), t, m);

        ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.tidStart)).setText(s);
        ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.tidSlut)).setText(s);

    }
}
