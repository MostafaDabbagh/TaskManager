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
import android.view.View;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.IRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.controller.fragment.TaskListFragment;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_OF_TASKS = "numberOfTasks";
    public static final String EXTRA_TITLE = "title";

    IRepository<Task> mRepository = TaskRepository.getInstance();

    ViewPager2 mViewPager2;
    TabLayout mTabLayout;
    FloatingActionButton mFloatingActionButton;

    public static Intent newIntent(Context context, String title) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        findViews();
        setListeners();

        FragmentStateAdapter adapter = new TaskPagerAdapter(this, mRepository.getAll());
        mViewPager2.setAdapter(adapter);
        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0)
                            tab.setText("Todo");
                        else if (position == 1)
                            tab.setText("Doing");
                        else
                            tab.setText("Done");
                    }
                }).attach();
    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.task_view_pager);
        mTabLayout = findViewById(R.id.tab_layout_task);
        mFloatingActionButton = findViewById(R.id.floating_action_button);
    }

    private void setListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
            Fragment fragment;
            if (position == 0)
                fragment = TaskListFragment.newInstance(State.TODO);
            else if (position == 1)
                fragment = TaskListFragment.newInstance(State.DOING);
            else
                fragment = TaskListFragment.newInstance(State.DONE);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}