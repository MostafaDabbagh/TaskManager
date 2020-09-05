package com.example.taskmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.taskmanager.database.DBSchema.*;

import androidx.annotation.Nullable;

public class TaskDataBaseHelper extends SQLiteOpenHelper {

    public TaskDataBaseHelper(@Nullable Context context) {
        super(context, DBSchema.NAME, null, DBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskTable.NAME + " ( " +
                TaskTable.COLS.ID + " integer" + " PRIMARY KEY AUTOINCREMENT, " +
                TaskTable.COLS.UUID + " text, " +
                TaskTable.COLS.USER_ID + " integer, " +
                TaskTable.COLS.STATE + " int, " +
                TaskTable.COLS.TITLE + " text, " +
                TaskTable.COLS.DESCRIPTION + " text, " +
                TaskTable.COLS.DATE + " long " +
                ") "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
