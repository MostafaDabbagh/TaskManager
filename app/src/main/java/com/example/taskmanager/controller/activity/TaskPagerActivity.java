package com.example.taskmanager.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.IRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.controller.fragment.TaskListFragment;
import com.example.taskmanager.model.Task;

import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_OF_TASKS = "numberOfTasks";
    public static final String EXTRA_TITLE = "title";

    IRepository mRepository = TaskRepository.getInstance();

    ViewPager2 mViewPager2;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        findViews();
       /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fargment_container);
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fargment_container, createFragment())
                    .commit();
        }
        */
       FragmentStateAdapter adapter = new TaskPagerAdapter(this, mRepository.getAll());
       mViewPager2.setAdapter(adapter);
    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.task_view_pager);
    }

    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

    private class TaskPagerAdapter extends FragmentStateAdapter {
        List<Task> mTaskList;

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity, List taskList) {
            super(fragmentActivity);
            mTaskList = taskList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return TaskListFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }












}