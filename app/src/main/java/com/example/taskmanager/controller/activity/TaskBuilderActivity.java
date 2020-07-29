package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.fragment.TaskBuilderFragment;

public class TaskBuilderActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskBuilderFragment();
    }
}