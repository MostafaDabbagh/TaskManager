package com.example.taskmanager.model;

import com.example.taskmanager.enums.State;
import com.example.taskmanager.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {
    private State mState;
    private String mTitle;
    private UUID mUUID;
    private Date mDate;
    private String mDescrptionn;
    private final UUID mUserId;

    public Task(State state, String title, Date date, UUID userId) {
        mState = state;
        mTitle = title;
        mDate = date;
        mUUID = UUID.randomUUID();
        mUserId = userId;
    }

    public Task(State state, String title, UUID userId) {
        mState = state;
        mTitle = title;
        mDate = DateUtils.getRandomDate();
        mUUID = UUID.randomUUID();
        mUserId = userId;
    }

    public Task(UUID uuid, State state, String title, Date date, String descrptionn, UUID userId) {
        mState = state;
        mTitle = title;
        mUUID = uuid;
        mDate = date;
        mDescrptionn = descrptionn;
        mUserId = userId;
    }

    public String getDescrptionn() {
        return mDescrptionn;
    }

    public void setDescrptionn(String descrptionn) {
        mDescrptionn = descrptionn;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public static State randomState() {
        int r = new Random().nextInt(3);
        if (r == 0)
            return State.TODO;
        else if (r == 1)
            return State.DOING;
        else
            return State.DONE;
    }
}
