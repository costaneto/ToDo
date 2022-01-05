package com.costaneto.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context cxt) {
        context = cxt;
    }

    // Opening the database
    public DatabaseManager open() throws SQLDataException {
        dbHelper =  new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void insert(String task, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TASK_TEXT, task);
        contentValues.put(DatabaseHelper.TASK_STATUS, status);
        database.insert(DatabaseHelper.DATABASE_TABLE, null, contentValues);
    }

    public Cursor fetchAll() {
        String[] columns = new String[] {DatabaseHelper.TASK_ID, DatabaseHelper.TASK_TEXT, DatabaseHelper.TASK_STATUS};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, columns, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor fetchSingle() {
        String[] columns = new String[] {DatabaseHelper.TASK_ID, DatabaseHelper.TASK_TEXT};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, columns, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToLast();
        return cursor;
    }

    public void updateStatus(String task, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TASK_STATUS, status);
        database.update(DatabaseHelper.DATABASE_TABLE, contentValues,"task_text=?", new String[]{task});
        Log.e("TASK_STATUS_UPDATED", "TASK_TEXT > " + task + "  |  NEW_STATUS > " + status);

    }

    public void delete(String task) {
        database.delete(DatabaseHelper.DATABASE_TABLE, "task_text=?", new String[]{task});
        Log.e("TASK_DELETED", "TASK_TEXT > " + task);
    }
}
