package com.example.taskmanager.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.taskmanager.R;
import com.example.taskmanager.model.Task;

import java.io.Serializable;

public class TaskSetterDialogFragment extends DialogFragment {

    public static final String ARG_CURRENT_TASK = "currentTask";

    private Task mCurrentTask;

    EditText mEditTextTitle;
    EditText mEditTextDescription;
    Button mButtonDate;
    Button mButtonTime;
    RadioGroup mRadioGroupStates;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_task_setter, null);
        findViews(view);
        setListeners();
        return view;
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


            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        mRadioGroupStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_todo :

                        // change task state

                        break;
                    case R.id.radio_button_doing :

                        // change task state

                        break;
                    case R.id.radio_button_done :

                        // change task state

                        break;
                    default:
                        break;
                }
            }
        });
    }

}





