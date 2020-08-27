package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.taskmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerDialogFragment extends DialogFragment {

    public static final String ARG_CURRENT_DATE = "currentDate";
    public static final String EXTRA_TIME_PICKED = "timePicked";

    private TimePicker mTimePicker;

    private Date mCurrentDate;


    public static TimePickerDialogFragment newInstance(Date date) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_time_picker, null);
        findViews(view);
        initTimePicker();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_TIME_PICKED, mCurrentDate);
                        Fragment fragment = getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                })
                .create();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTimePicker() {
        mTimePicker.setIs24HourView(true);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR, i);
                calendar.set(Calendar.MINUTE, i1);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(calendar.getTime());
                mCurrentDate = gc.getTime();
            }
        });

    }


    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker);
    }


}