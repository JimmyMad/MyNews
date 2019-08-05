package com.sweethome.jimmy.mynews.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;


public class EditTextDatePicker  implements View.OnClickListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {
    private EditText _editText;
    private int _day;
    private int _month;
    private int _year;
    private Context _context;

    public EditTextDatePicker(Context context, int editTextViewID)
    {
        Activity act = (Activity)context;
        this._editText = act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _year = year;
        _month = monthOfYear + 1;
        _day = dayOfMonth;
        updateDisplay();
    }

    private void updateDisplay() {
        String year = Integer.toString(_year),
                month = Integer.toString(_month),
                day = Integer.toString(_day);

        while (year.length() < 4) {
            year = "0" + year;
        }
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }

        _editText.setText(new StringBuilder()
                .append(day).append("/").append(month).append("/").append(year));
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        _editText.clearFocus();
        _editText.setClickable(true);
    }

    @Override
    public void onClick(View view) {
        _editText.setClickable(false);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setOnDismissListener(this);
        dialog.show();
    }
}