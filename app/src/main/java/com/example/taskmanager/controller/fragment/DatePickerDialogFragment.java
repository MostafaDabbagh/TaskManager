package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.taskmanager.R;
import com.example.taskmanager.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerDialogFragment extends DialogFragment {

    public static final String ARG_CURRENT_DATE = "currenDate";
    public static final String EXTRA_DATE_PICKED = "com.example.taskmanager.controller.fragment.datePicked";

    private DatePicker mDatePicker;

    private Date mCurrentDate;

    public static DatePickerDialogFragment newInstance(Date date) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentDate = (Date) getArguments().getSerializable(ARG_CURRENT_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_date_picker, null);
        findViews(view);
        initDatePicker();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_DATE_PICKED, mCurrentDate);
                        Fragment fragment = getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                })
                .create();
    }

    private void initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        if (mCurrentDate == null)
            mCurrentDate = DateUtils.getRandomDate();
        calendar.setTime(mCurrentDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                // Only i1 begins from 0
                GregorianCalendar newDategGC = new GregorianCalendar();
                GregorianCalendar currentDateGC = new GregorianCalendar();
                currentDateGC.setTime(mCurrentDate);
                newDategGC.set(i,
                        i1,
                        i2,
                        currentDateGC.get(Calendar.HOUR_OF_DAY),
                        currentDateGC.get(Calendar.MINUTE));
                mCurrentDate = newDategGC.getTime();
            }
        });
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker);
    }
}