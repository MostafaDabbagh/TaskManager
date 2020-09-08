package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.TaskDBRepository;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.utils.DateUtils;
import com.example.taskmanager.utils.TaskSearcher;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SearchFragment extends Fragment {
    public static final int REQUEST_CODE_DATE_PICKER = 2;
    public static final int REQUEST_CODE_TIME_PICKER = 3;

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonSetDate;
    private Button mButtonSetTime;
    private Button mButtonClearDate;
    private Button mButtonClearTime;
    private Button mButtonSearch;
    private RecyclerView mRecyclerView;

    private TaskDBRepository mTaskRepository;
    private UserRepository mUserRepository;
    private Task mSearchTask;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
        mUserRepository = UserRepository.getInstance(getActivity());
        mSearchTask = new Task(null, null, null, null, null, mUserRepository.getCurrentUser().getUUID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        setListeners();
        //Todo: delete next 2 lines after implementing search in time.
        mButtonSetTime.setEnabled(false);
        mButtonClearTime.setEnabled(false);

        return view;
    }

    public void updateRecyclerView(List<Task> taskList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        TaskAdapter taskAdapter = new SearchFragment.TaskAdapter(taskList);
        mRecyclerView.setAdapter(taskAdapter);
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_text_title_search);
        mEditTextDescription = view.findViewById(R.id.edit_text_description_search);
        mButtonSetDate = view.findViewById(R.id.search_button_date);
        mButtonSetTime = view.findViewById(R.id.seaarch_button_time);
        mButtonClearDate = view.findViewById(R.id.button_clear_date);
        mButtonClearTime = view.findViewById(R.id.button_clear_time);
        mButtonSearch = view.findViewById(R.id.search_button_search);
        mRecyclerView = view.findViewById(R.id.recycler_view_search);
    }

    private void setListeners() {
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mSearchTask.getDate());
                datePickerDialogFragment.setTargetFragment(SearchFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager(), "datePickerDialog");
            }
        });
/*
        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(mSearchTask.getDate());
                timePickerDialogFragment.setTargetFragment(SearchFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager(), "timePickerDialog");
            }
        });
 */
        mButtonClearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonSetDate.setText("SET DATE");
                mSearchTask.setDate(null);
            }
        });
/*
        mButtonClearTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////////////////////
                mButtonSetTime.setText("SET TIME");
            }
        });
 */
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchTask.setTitle(mEditTextTitle.getText().toString());
                mSearchTask.setDescrptionn(mEditTextDescription.getText().toString());
                List<Task> searchResultTasks = TaskSearcher.searchInList(mSearchTask,
                        mTaskRepository.getUserTasks(mUserRepository.getCurrentUser().getUUID()));
                updateRecyclerView(searchResultTasks);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date responseDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE_PICKED);
            GregorianCalendar newTimeGC = new GregorianCalendar();
            newTimeGC.setTime(responseDate);
            mSearchTask.setDate(newTimeGC.getTime());
            mButtonSetDate.setText(DateUtils.getDateText(mSearchTask.getDate()));
        }
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
                    //TODO: open task setter dialog
                }
            });
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<SearchFragment.TaskHolder> {

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
        public SearchFragment.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_task, parent, false);

            if (viewType == 0) {
                view.setBackgroundColor(Color.rgb(235, 235, 250));
            }
            SearchFragment.TaskHolder taskHolder = new SearchFragment.TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchFragment.TaskHolder holder, int position) {
            Task task = mTaskList.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
}











