package com.example.taskmanager.Repository;

import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements RepositoryInterface<Task> {
    private static TaskRepository sRepository;

    public static TaskRepository getInstance(){
        if (sRepository == null)
            sRepository = new TaskRepository();
        return sRepository;
    }

    private TaskRepository(){
        mTaskList = new ArrayList<>();
    }

    private List<Task> mTaskList;

    @Override
    public void add(Task task) {
        mTaskList.add(task);
    }

    @Override
    public void remove(Task task) {
        mTaskList.remove(task);
    }

    @Override
    public Task get(UUID id) {
        for (Task t: mTaskList) {
            if (t.getId().equals(id))
                return t;
        }
        return null;
    }

    @Override
    public void update(Task task) {
        Task updateTask = get(task.getId());
        updateTask.setState(task.getState());
        updateTask.setTitle(task.getTitle());
    }

    @Override
    public List<Task> getAll() {
        return mTaskList;
    }

    @Override
    public void setAll(List<Task> list) {
        mTaskList = list;
    }
}
