package com.example.weatherapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                R.style.AppTheme_DatePickerDialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        //Can't choose day earlier than today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //Can choose up to a week forward from today
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +6);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yy);
        c.set(Calendar.MONTH, mm);
        c.set(Calendar.DAY_OF_MONTH, dd);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(c.getTime());
        Bundle result = new Bundle();
        result.putString("bundleKey", formatted);
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}
