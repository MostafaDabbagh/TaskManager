package com.example.taskmanager.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.taskmanager.database.cursorwrapper.TaskCursorWrapper;
import com.example.taskmanager.database.TaskDataBaseHelper;
import com.example.taskmanager.enums.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.database.DBSchema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository {
    private static TaskDBRepository sRepository;
    private static Context sContext;

    public static TaskDBRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new TaskDBRepository(context);
        }
        return sRepository;
    }

    private TaskDBRepository(Context context) {
        sContext = context.getApplicationContext();
        mDatabase = new TaskDataBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    private SQLiteDatabase mDatabase;

    public void add(Task task) {
        ContentValues contentValues = getTaskContentValues(task);
        mDatabase.insert(TaskTable.NAME, null, contentValues);
    }

    public void remove(UUID uuid) {
        //////////////////////////////////
        //////////////////////////////////
        //////////////////////////////////
    }

    public Task get(UUID uuid) {
        String where = TaskTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{uuid.toString()};
        Cursor cursor = queryTasks(where, whereArgs);
        try {
            TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
            taskCursorWrapper.moveToFirst();
            Task task = taskCursorWrapper.getTask();
            return task;
        } catch (CursorIndexOutOfBoundsException e) {
            return null;
        } finally {
            cursor.close();
        }
    }

    public void update(Task task) {
        ContentValues contentValues = getTaskContentValues(task);
        String where = TaskTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{task.getUUID().toString()};
        mDatabase.update(TaskTable.NAME,
                contentValues,
                where,
                whereArgs);
    }

    public List<Task> getAll() {
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = queryTasks(null, null);
        try {
            TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {
                Task task = taskCursorWrapper.getTask();
                taskList.add(task);
                taskCursorWrapper.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return taskList;
    }

    public List<Task> getUserTasks(UUID userId) {
        List<Task> userTasks = new ArrayList<>();
        String where = TaskTable.COLS.USER_ID + "=?";
        String[] whereArgs = new String[]{userId.toString()};
        Cursor cursor = queryTasks(where, whereArgs);
        try {
            TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {
                Task task = taskCursorWrapper.getTask();
                userTasks.add(task);
                taskCursorWrapper.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return userTasks;
    }

    private ContentValues getTaskContentValues(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskTable.COLS.UUID, task.getUUID().toString());
        contentValues.put(TaskTable.COLS.USER_ID, task.getUserId().toString());
        contentValues.put(TaskTable.COLS.TITLE, task.getTitle());
        contentValues.put(TaskTable.COLS.STATE, stateToIntegr(task.getState()));
        contentValues.put(TaskTable.COLS.DESCRIPTION, task.getDescrptionn());
        contentValues.put(TaskTable.COLS.DATE, task.getDate().getTime());
        return contentValues;
    }

    private Cursor queryTasks(String where, String[] whereArgs) {
        return mDatabase.query(TaskTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    private int stateToIntegr(State state) {
        if (state == State.TODO)
            return 0;
        if (state == State.DOING)
            return 1;
        return 2;
    }

}
