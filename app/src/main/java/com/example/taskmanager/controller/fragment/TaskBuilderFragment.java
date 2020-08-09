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
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                int number =Integer.parseInt(String.valueOf(mEditTextNumber.getText()));
                String titleStr = String.valueOf(mEditTextTitle.getText());
                initRepository(number, titleStr);
                Intent intent = TaskPagerActivity.newIntent(getActivity(), titleStr);
                startActivity(intent);
            }
        });
    }

    private void initRepository(int n, String title) {
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            taskList.add(new Task(Task.randomState(), title + " " + (i + 1)));
        }
        TaskRepository.getInstance().setAll(taskList);
    }












}