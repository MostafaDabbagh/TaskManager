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

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.taskmanager.R;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

public class TaskSetterDialogFragment extends DialogFragment {

    public static final String ARG_CURRENT_TASK = "currentTask";
    public static final String EXTRA_CURRENT_TASK = "com.example.taskmanager.controller.fragment.currentTask";

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

    /*
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_fragment_task_setter, null);
            findViews(view);
            setListeners();
            initViews();
            return view;
        }
    */
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
    public void onDestroy() {
        super.onDestroy();
        setCurrentTask();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CURRENT_TASK, mCurrentTask);
        TaskListFragment fragment = (TaskListFragment) getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void setCurrentTask() {
        mCurrentTask.setTitle(mEditTextTitle.getText().toString());
        mCurrentTask.setDescrptionn(mEditTextDescription.toString());
        // TODO
        //  mCurrentTask.setDate();
        // State is already set in RadioGroup check listener


    }

    private void initViews() {
        String dateStr = mCurrentTask.getDate().toString().substring(0, 11) + mCurrentTask.getDate().toString().substring(30);
        String timeStr = mCurrentTask.getDate().toString().substring(11, 30);
        mButtonDate.setText(dateStr);
        mButtonTime.setText(timeStr);
        mEditTextTitle.setText(mCurrentTask.getTitle());
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





