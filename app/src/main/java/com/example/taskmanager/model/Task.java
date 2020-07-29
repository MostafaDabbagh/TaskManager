package com.example.taskmanager.model;

import com.example.taskmanager.enums.State;

import java.util.UUID;

public class Task {
    private State mState;
    private String mTitle;
    private UUID mId;

    public Task(State state, String title) {
        mState = state;
        mTitle = title;
        mId = UUID.randomUUID();
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
