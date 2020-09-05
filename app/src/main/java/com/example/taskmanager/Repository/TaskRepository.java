package com.example.taskmanager.Repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IDBRepository<Task> {
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
            if (t.getUUID().equals(id))
                return t;
        }
        return null;
    }

    @Override
    public void update(Task task) {
        Task updateTask = get(task.getUUID());
        updateTask.setState(task.getState());
        updateTask.setTitle(task.getTitle());
        updateTask.setDescrptionn(task.getDescrptionn());
        updateTask.setDate(task.getDate());
    }

    @Override
    public List<Task> getAll() {
        return mTaskList;
    }


    public void setAll(List<Task> list) {
        mTaskList = list;
    }

    public List<Task> getUserTasks(User user) {
        List<Task> userTasks = new ArrayList<>();
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getUserId().equals(user.getUUID()))
                userTasks.add(mTaskList.get(i));
        }
        return userTasks;
    }
}
