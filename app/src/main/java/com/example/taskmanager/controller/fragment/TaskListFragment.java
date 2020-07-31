package com.example.taskmanager.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.R;

import java.util.UUID;

public class TaskListFragment extends Fragment {

    public static final String ARG_NUMBER_OF_TASKS = "com.example.taskmanager.controller.fragment.numberOfTasks";
    public static final String ARG_TITLE = "com.example.taskmanager.controller.fragment.title";

    public static TaskListFragment newInstance(int numberOfTasks, String title) {
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_TASKS, numberOfTasks);
        args.putString(ARG_TITLE, title);
        TaskListFragment fragment = new TaskListFragment();
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
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }
}