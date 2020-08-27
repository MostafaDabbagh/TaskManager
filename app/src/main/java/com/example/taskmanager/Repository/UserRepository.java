package com.example.taskmanager.Repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User>{

    private static UserRepository sUserRepository;

    public static UserRepository getInstance() {
        if (sUserRepository == null)
            sUserRepository = new UserRepository();
        return sUserRepository;
    }

    private UserRepository() {
        mUserList = new ArrayList<>();
    }

    private List<User> mUserList;
    private User mCurrentUser;

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }

    @Override
    public void add(User user) {
        mUserList.add(user);
    }

    @Override
    public void remove(User user) {
        mUserList.remove(user);
    }

    @Override
    public User get(UUID id) {
        for (User u:mUserList) {
            if (u.getUUID().equals(id))
                return u;
        }
        return null;
    }

    public User get(String username) {
        for (User u:mUserList) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    @Override
    public void update(User user) {
        User updateUser = get(user.getUUID());
        updateUser.setUsername(user.getPassword());
        updateUser.setUsername(user.getUsername());
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void setAll(List<User> list) {
        mUserList = list;
    }

    public boolean userExists(String username) {
        for (int i = 0; i < mUserList.size(); i++) {
            if (mUserList.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }

}
