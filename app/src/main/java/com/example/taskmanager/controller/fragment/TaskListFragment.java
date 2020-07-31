package com.example.taskmanager.controller.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListFragment extends Fragment {

    public static final String ARG_NUMBER_OF_TASKS = "com.example.taskmanager.controller.fragment.numberOfTasks";
    public static final String ARG_TITLE = "com.example.taskmanager.controller.fragment.title";

    private RecyclerView mRecyclerView;

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
        int numberOfTasks = getArguments().getInt(ARG_NUMBER_OF_TASKS);
        String title = getArguments().getString(ARG_TITLE);
        if (savedInstanceState == null) {
            initRepository(numberOfTasks, title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TaskAdapter(TaskRepository.getInstance().getAll()));
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
    }

    private void initRepository(int n, String title) {
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int r = new Random().nextInt(3);
            State state;
            switch (r) {
                case 0:
                    state = State.TODO;
                    break;
                case 1:
                    state = State.DOING;
                    break;
                default:
                    state = State.DONE;
            }
            taskList.add(new Task(state, title));
        }
        TaskRepository.getInstance().setAll(taskList);
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













