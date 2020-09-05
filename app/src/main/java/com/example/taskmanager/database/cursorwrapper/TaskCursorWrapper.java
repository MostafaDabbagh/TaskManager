package com.example.taskmanager.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanager.database.DBSchema.*;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String stringUuid = getString(getColumnIndex(TaskTable.COLS.UUID));
        String stringUserId = getString(getColumnIndex(TaskTable.COLS.USER_ID));
        String title = getString(getColumnIndex(TaskTable.COLS.TITLE));
        int stateInt = getInt(getColumnIndex(TaskTable.COLS.STATE));
        String description = getString(getColumnIndex(TaskTable.COLS.DESCRIPTION));
        long dateLong = getLong(getColumnIndex(TaskTable.COLS.DATE));
        Task task = new Task(UUID.fromString(stringUuid),
                integerToState(stateInt),
                title,
                new Date(dateLong),
                description,
                UUID.fromString(stringUserId));
        return task;
    }

    private State integerToState(int i) {
        if (i == 0)
            return State.TODO;
        if (i == 1)
            return State.DOING;
        return State.DONE;
    }
}
