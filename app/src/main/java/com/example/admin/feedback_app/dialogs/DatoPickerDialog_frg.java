package com.example.admin.feedback_app.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.admin.feedback_app.R;

import java.util.Calendar;

public class DatoPickerDialog_frg extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar kalender = Calendar.getInstance();

        int år = kalender.get(Calendar.YEAR);
        int måned = kalender.get(Calendar.MONTH);
        int dag = kalender.get(Calendar.DAY_OF_MONTH);
        int theme = R.style.DialogThemeGray;


        return new DatePickerDialog(getActivity(),theme, this, år, måned, dag);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int år, int måned, int dag) {
        String d, m;

        if (dag < 10)
            d = "0" + dag;
        else
            d = Integer.toString(dag);

        if (++måned < 10)
            m = "0" + måned;
        else
            m = Integer.toString(måned);

        String s = String.format(getString(R.string.dato_format), d, m, år);

        ((TextView) getActivity().findViewById(R.id.dato)).setText(s);
    }
}
