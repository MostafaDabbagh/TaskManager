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

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

    public static Intent newIntent(Context context, int numberOfTasks) {
        Intent intent = new Intent(context, TaskListActivity.class);
        intent.putExtra(EXTRA_NUMBER_OF_TASKS, numberOfTasks);
        return intent;
    }
}