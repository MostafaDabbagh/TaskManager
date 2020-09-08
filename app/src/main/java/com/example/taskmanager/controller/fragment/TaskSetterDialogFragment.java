package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.R;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.utils.DateUtils;

import java.util.Date;
import java.util.GregorianCalendar;

public class TaskSetterDialogFragment extends DialogFragment {

    public static final String ARG_CURRENT_TASK = "currentTask";
    public static final String EXTRA_CURRENT_TASK = "com.example.taskmanager.controller.fragment.currentTask";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    private Task mCurrentTask;

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private RadioGroup mRadioGroupStates;

    public static TaskSetterDialogFragment newInstance(Task task) {
        TaskSetterDialogFragment fragment = new TaskSetterDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentTask = (Task) getArguments().getSerializable(ARG_CURRENT_TASK);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_task_setter, null);
        findViews(view);
        setListeners();
        initViews();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setCurrentTask();
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_CURRENT_TASK, mCurrentTask);
                        TaskListFragment fragment = (TaskListFragment) getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        //       Toast.makeText(getActivity(), "OK!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date responseDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE_PICKED);
            GregorianCalendar newTimeGC = new GregorianCalendar();
            newTimeGC.setTime(responseDate);
            mCurrentTask.setDate(newTimeGC.getTime());

            mButtonDate.setText(DateUtils.getDateText(mCurrentTask.getDate()));
            mButtonTime.setText(DateUtils.getTimeText(mCurrentTask.getDate()));

        } else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Date responseDate = (Date) data.getSerializableExtra(TimePickerDialogFragment.EXTRA_TIME_PICKED);
            GregorianCalendar settingDate = new GregorianCalendar();
            settingDate.setTime(responseDate);
            mCurrentTask.setDate(responseDate);
            mButtonTime.setText(DateUtils.getTimeText(mCurrentTask.getDate()));
            mButtonDate.setText(DateUtils.getDateText(mCurrentTask.getDate()));
        }
    }

    private void setCurrentTask() {
        mCurrentTask.setTitle(mEditTextTitle.getText().toString());
        mCurrentTask.setDescrptionn(mEditTextDescription.toString());
        // date is set in onActivityResult
        // State is already set in RadioGroup check listener
    }

    private void initViews() {
        mButtonDate.setText(DateUtils.getDateText(mCurrentTask.getDate()));
        mButtonTime.setText(DateUtils.getTimeText(mCurrentTask.getDate()));
        mEditTextTitle.setText(mCurrentTask.getTitle());
        if (mCurrentTask.getDescrptionn() != null)
            mEditTextDescription.setText(mCurrentTask.getDescrptionn());
        if (mCurrentTask.getState() == State.TODO)
            mRadioGroupStates.check(R.id.radio_button_todo);
        else if (mCurrentTask.getState() == State.DOING)
            mRadioGroupStates.check(R.id.radio_button_doing);
        else if (mCurrentTask.getState() == State.DONE)
            mRadioGroupStates.check(R.id.radio_button_done);
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.dilaog_edit_text_title);
        mEditTextDescription = view.findViewById(R.id.dialog_edit_text_description);
        mButtonDate = view.findViewById(R.id.button_set_date);
        mButtonTime = view.findViewById(R.id.button_set_time);
        mRadioGroupStates = view.findViewById(R.id.radio_group_states);
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mCurrentTask.getDate());
                datePickerDialogFragment.setTargetFragment(TaskSetterDialogFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager(), "datePickerDialog");
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(mCurrentTask.getDate());
                timePickerDialogFragment.setTargetFragment(TaskSetterDialogFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager(), "timePickerDialog");
            }
        });

        mRadioGroupStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_todo:
                        mCurrentTask.setState(State.TODO);
                        break;
                    case R.id.radio_button_doing:
                        mCurrentTask.setState(State.DOING);
                        break;
                    case R.id.radio_button_done:
                        mCurrentTask.setState(State.DONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}





