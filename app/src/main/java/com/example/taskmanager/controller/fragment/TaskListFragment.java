package com.example.taskmanager.controller.fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListFragment extends Fragment {

    public static final String ARG_STATE = "com.example.taskmanager.controller.fragment.state";

    private RecyclerView mRecyclerView;

    private State mFragmentTasksState;
    private List mFragmentTasks;

    public static TaskListFragment newInstance(State state) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STATE, state);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentTasksState = (State) getArguments().getSerializable(ARG_STATE);
        setFragmentTaskList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        initRecyclerView();
        return view;
    }

    private void setFragmentTaskList() {
        List<Task> allTasks = TaskRepository.getInstance().getAll();
        List<Task> fragmentTasks = new ArrayList();
        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).getState() == mFragmentTasksState)
                fragmentTasks.add(allTasks.get(i));
        }
        mFragmentTasks = fragmentTasks;
    }

    private void initRecyclerView() {
        int columns;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            columns = 1;
        else
            columns = 2;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new TaskAdapter(mFragmentTasks));
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
    }


    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewState;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.list_row_text_view_title);
            mTextViewState = itemView.findViewById(R.id.list_row_text_view_state);
        }

        public void bindTask(Task task) {
            mTextViewTitle.setText(task.getTitle());
            mTextViewState.setText(String.valueOf(task.getState()));
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTaskList;

        public TaskAdapter(List taskList) {
            mTaskList = taskList;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0)
                return 0;
            else
                return 1;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_task, parent, false);
            if (viewType == 0) {
                view.setBackgroundColor(Color.rgb(235, 235, 250));
            }
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTaskList.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }

}













