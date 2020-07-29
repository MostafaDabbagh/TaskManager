package com.example.taskmanager.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.TaskListActivity;

public class TaskBuilderFragment extends Fragment {

    private EditText mEditTextNumber;
    private EditText mEditTextTitle;
    private Button mButtonConfirm;

    public static TaskBuilderFragment newInstance() {
        Bundle args = new Bundle();
        TaskBuilderFragment fragment = new TaskBuilderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
     */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_builder, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mEditTextNumber = view.findViewById(R.id.edit_text_number);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mButtonConfirm = view.findViewById(R.id.button_confirm);
    }

    private void setListeners() {
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberStr = String.valueOf(mEditTextNumber.getText());
                String titleStr = String.valueOf(mEditTextTitle.getText());
                Intent intent = TaskListActivity
                        .newIntent(getActivity(),Integer.parseInt(numberStr), titleStr);
                startActivity(intent);
            }
        });
    }




}