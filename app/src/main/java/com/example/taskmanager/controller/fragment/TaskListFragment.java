package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskDBRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    public static final String ARG_STATE = "com.example.taskmanager.controller.fragment.state";
    public static final int REQUEST_CODE_TASK_SETTER_DIALOG_FRAGMENT = 0;

    private RecyclerView mRecyclerView;

    private TaskAdapter mTaskAdapter;
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
        updateFragmentTaskList();
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

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void updateFragmentTaskList() {

        List<Task> userTasks = TaskDBRepository
                .getInstance(getActivity())
                .getUserTasks(UserRepository.getInstance(getActivity()).getCurrentUser().getUUID());
        List<Task> fragmentTasks = new ArrayList();
        for (int i = 0; i < userTasks.size(); i++) {
            if (userTasks.get(i).getState() == mFragmentTasksState)
                fragmentTasks.add(userTasks.get(i));
        }
        mFragmentTasks = fragmentTasks;
    }

    public void initRecyclerView() {
        int columns;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            columns = 1;
        else
            columns = 2;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mTaskAdapter = new TaskAdapter(mFragmentTasks);
        mRecyclerView.setAdapter(mTaskAdapter);
    }

    public void update() {
        updateFragmentTaskList();

        mTaskAdapter.setTaskList(mFragmentTasks);
        mTaskAdapter.notifyDataSetChanged();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_TASK_SETTER_DIALOG_FRAGMENT) {
            Task responseTask = (Task) data.getSerializableExtra(TaskSetterDialogFragment.EXTRA_CURRENT_TASK);

            TaskDBRepository taskDBRepository = TaskDBRepository.getInstance(getActivity());
            if (taskDBRepository.get(responseTask.getUUID()) == null)
                taskDBRepository.add(responseTask);
            else
                taskDBRepository.update(responseTask);

            update();
        }
    }

    public void startTaskSetterDialog(Task task) {
        TaskSetterDialogFragment taskSetterDialogFragment = TaskSetterDialogFragment.newInstance(task);
        taskSetterDialogFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_SETTER_DIALOG_FRAGMENT);
        taskSetterDialogFragment.show(getFragmentManager(), "taskSetterDialog");
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private TextView mTextViewTime;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.list_row_text_view_title);
            mTextViewDate = itemView.findViewById(R.id.list_row_text_view_date);
            mTextViewTime = itemView.findViewById(R.id.list_row_text_time);

        }

        public void bindTask(final Task task) {
            mTextViewTitle.setText(task.getTitle());
//            String dateStr = task.getDate().toString().substring(0, 11) + task.getDate().toString().substring(30);
//            String timeStr = task.getDate().toString().substring(11, 30);
            mTextViewDate.setText(DateUtils.getDateText(task.getDate()));
            mTextViewTime.setText(DateUtils.getTimeText(task.getDate()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open task setter dialog
                    startTaskSetterDialog(task);
                }
            });
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTaskList;

        public TaskAdapter(List taskList) {
            mTaskList = taskList;
        }

        public void setTaskList(List taskList) {
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













