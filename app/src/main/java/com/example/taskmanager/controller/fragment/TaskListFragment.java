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
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.FragmentRepository;
import com.example.taskmanager.Repository.IRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

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

    public void updateFragmentTaskList() {
        List<Task> allTasks = TaskRepository.getInstance().getAll();
        List<Task> fragmentTasks = new ArrayList();
        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).getState() == mFragmentTasksState)
                fragmentTasks.add(allTasks.get(i));
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
            TaskRepository taskRepository = TaskRepository.getInstance();
            if (taskRepository.get(responseTask.getId()) == null)
                taskRepository.add(responseTask);
            else
                taskRepository.update(responseTask);
            //   Toast.makeText(getActivity(), responseTask.getTitle(), Toast.LENGTH_SHORT).show();
            //    TaskPagerActivity.updateAllPages();
            update();

    /*        if (responseTask.getState() == State.TODO) {
                TaskListFragment tsf = (TaskListFragment) getActivity().getSupportFragmentManager().findFragmentByTag("f0");
                tsf.update();
            }
            else if (responseTask.getState() == State.DOING) {
                TaskListFragment tsf = (TaskListFragment) getActivity().getSupportFragmentManager().findFragmentByTag("f1");
                tsf.update();
            }
//                TaskPagerActivity.sFragmentList.get(1).update();
            else {
                TaskListFragment tsf = (TaskListFragment) getActivity().getSupportFragmentManager().findFragmentByTag("f2");
                tsf.update();
            }
*/
//                TaskPagerActivity.sFragmentList.get(2).update();


            //   FragmentRepository.getInstance().get(1).update();
            //  FragmentRepository.getInstance().get(2).update();
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
            String dateStr = task.getDate().toString().substring(0, 11) + task.getDate().toString().substring(30);
            String timeStr = task.getDate().toString().substring(11, 30);
            mTextViewDate.setText(dateStr);
            mTextViewTime.setText(timeStr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open task setter dialog
                    startTaskSetterDialog(task);
                /*    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("taskSetterDialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogFragment taskSetterDialoFragment = TaskSetterDialogFragment.newInstance(task);
                    taskSetterDialoFragment.show(ft, "taskSetterDialog");   */
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













