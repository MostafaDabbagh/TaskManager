package com.example.taskmanager.Repository;

import androidx.fragment.app.Fragment;

import com.example.taskmanager.controller.fragment.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentRepository {
    private static FragmentRepository sRepository;

    private FragmentRepository() {
        mFragmentList = new ArrayList<>();
    }

    public static FragmentRepository getInstance() {
        if (sRepository == null)
            sRepository = new FragmentRepository();
        return sRepository;
    }

    private List<TaskListFragment> mFragmentList;

    public void add(TaskListFragment fragment) {
        mFragmentList.add(fragment);
    }

    public TaskListFragment get(int i) {
        return mFragmentList.get(i);
    }

    public void setAll(List list) {
        mFragmentList = list;
    }

    public List<TaskListFragment> getAll() {
        return mFragmentList;
    }

}
