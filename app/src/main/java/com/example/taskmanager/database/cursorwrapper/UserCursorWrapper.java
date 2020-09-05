package com.example.taskmanager.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanager.database.DBSchema.*;
import com.example.taskmanager.model.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidStr = getString(getColumnIndex(UserTable.COLS.UUID));
        String username = getString(getColumnIndex(UserTable.COLS.USERNAME));
        String password = getString(getColumnIndex(UserTable.COLS.PASSWORD));
        User user = new User(username, password, UUID.fromString(uuidStr));
        return user;
    }
}
