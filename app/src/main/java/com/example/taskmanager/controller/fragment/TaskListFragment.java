package com.example.taskmanager.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskListFragment extends Fragment {

    public static final String ARG_NUMBER_OF_TASKS = "com.example.taskmanager.controller.fragment.numberOfTasks";
    public static final String ARG_TITLE = "com.example.taskmanager.controller.fragment.title";

    private RecyclerView mRecyclerViewTaskList;

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
        Todo: Configuration change handling
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
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        mRecyclerViewTaskList.setAdapter(new TaskAdapter());
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewTaskList = view.findViewById(R.id.recycler_view_task_list);
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

        List<Task> mTaskList = TaskRepository.getInstance().getAll();

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_task, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return null;
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













