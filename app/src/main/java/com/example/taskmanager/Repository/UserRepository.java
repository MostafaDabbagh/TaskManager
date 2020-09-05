package com.example.taskmanager.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.taskmanager.database.cursorwrapper.UserCursorWrapper;
import com.example.taskmanager.database.UserDataBaseHelper;
import com.example.taskmanager.model.User;
import com.example.taskmanager.database.DBSchema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IDBRepository<User> {

    private static UserRepository sUserRepository;
    private static Context sContext;

    public static UserRepository getInstance(Context context) {
        if (sUserRepository == null) {
            sUserRepository = new UserRepository(context);

        }
        return sUserRepository;
    }

    private UserRepository(Context context) {
        sContext = context.getApplicationContext();
        mDatabase = new UserDataBaseHelper(sContext).getWritableDatabase();
    }

    private SQLiteDatabase mDatabase;
    private User mCurrentUser;

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }

    @Override
    public void add(User user) {
        ContentValues contentValues = getUserContentValues(user);
        mDatabase.insert(UserTable.NAME, null, contentValues);
    }

    @Override
    public void remove(User user) {
        //////////////////////////////
        //////////////////////////////
        //////////////////////////////
    }

    @Override
    public User get(UUID uuid) {
        String where = UserTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{uuid.toString()};
        Cursor cursor = queryUsers(where, whereArgs);
        try {
            UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();
            return user;
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(sContext, "Exception", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            cursor.close();
        }
    }

    public User get(String username) {
        String where = UserTable.COLS.USERNAME + "=?";
        String[] whereArgs = new String[]{username};
        Cursor cursor = queryUsers(where, whereArgs);
        try {
            UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();
            return user;
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(sContext, "Exception", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            cursor.close();
        }

    }

    @Override
    public void update(User user) {
        String selection = UserTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{user.getUUID().toString()};
        ContentValues contentValues = getUserContentValues(user);
        mDatabase.update(UserTable.NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = queryUsers(null, null);
        try {
            UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
            userCursorWrapper.moveToFirst();
            while (!userCursorWrapper.isAfterLast()) {
                User user = userCursorWrapper.getUser();
                userList.add(user);
                userCursorWrapper.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return userList;
    }

    public boolean userExists(String username) {
        List<User> userList = getAll();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }

    private ContentValues getUserContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.COLS.UUID, user.getUUID().toString());
        contentValues.put(UserTable.COLS.USERNAME, user.getUsername());
        contentValues.put(UserTable.COLS.PASSWORD, user.getPassword());
        return contentValues;
    }

    private Cursor queryUsers(String where, String[] whereArgs) {
        return mDatabase.query(UserTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
    }

}
