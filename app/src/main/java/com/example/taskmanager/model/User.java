package com.example.taskmanager.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String mUsername;
    private String mPassword;
    private UUID mUUID;

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
        mUUID = UUID.randomUUID();
    }

    public User(String username, String password, UUID UUID) {
        mUsername = username;
        mPassword = password;
        mUUID = UUID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }
}
