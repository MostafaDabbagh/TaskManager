package com.example.taskmanager.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskDBRepository;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.controller.fragment.TaskListFragment;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    private static List<TaskListFragment> sFragmentList;

    private UserRepository mUserRepository;
    private User mCurrentUser;

    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        findViews();
        setListeners();
        mUserRepository = UserRepository.getInstance(this);
        mCurrentUser = mUserRepository.getCurrentUser();
        sFragmentList = new ArrayList<>();
        sFragmentList.add(TaskListFragment.newInstance(State.TODO, mCurrentUser));
        sFragmentList.add(TaskListFragment.newInstance(State.DOING, mCurrentUser));
        sFragmentList.add(TaskListFragment.newInstance(State.DONE, mCurrentUser));

        FragmentStateAdapter adapter = new TaskPagerAdapter(this);
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
        mFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Task task = new Task(State.TODO, "new task", mUserRepository.getCurrentUser().getUUID());
                        sFragmentList.get(mViewPager2.getCurrentItem()).startTaskSetterDialog(task);
                    }
                });
    }

    private class TaskPagerAdapter extends FragmentStateAdapter {
        List<Task> mTaskList;

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            //     mTaskList = taskList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
           /* Fragment fragment;
            if (position == 0)
                fragment = TaskListFragment.newInstance(State.TODO);
            else if (position == 1)
                fragment = TaskListFragment.newInstance(State.DOING);
            else
                fragment = TaskListFragment.newInstance(State.DONE);
            return fragment; */
            return sFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}