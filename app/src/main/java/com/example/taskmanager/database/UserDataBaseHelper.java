package com.example.taskmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    public UserDataBaseHelper(@Nullable Context context) {
        super(context, DBSchema.UserTable.NAME, null, DBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DBSchema.UserTable.NAME + " ( " +
                DBSchema.UserTable.COLS.ID + " integer" + " PRIMARY KEY AUTOINCREMENT, " +
                DBSchema.UserTable.COLS.UUID + " text, " +
                DBSchema.UserTable.COLS.USERNAME + " text, " +
                DBSchema.UserTable.COLS.PASSWORD + " text " +
                ") "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
