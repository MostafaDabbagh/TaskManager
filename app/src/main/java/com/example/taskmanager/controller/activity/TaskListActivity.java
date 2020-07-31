package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.fragment.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {

    public static final String EXTRA_NUMBER_OF_TASKS = "numberOfTasks";
    public static final String EXTRA_TITLE = "title";

    @Override
    protected Fragment createFragment() {
        int numberOfTasks = getIntent().getIntExtra(EXTRA_NUMBER_OF_TASKS, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        return TaskListFragment.newInstance(numberOfTasks, title);
    }

    public static Intent newIntent(Context context, int numberOfTasks, String title) {
        Intent intent = new Intent(context, TaskListActivity.class);
        intent.putExtra(EXTRA_NUMBER_OF_TASKS, numberOfTasks);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }
}