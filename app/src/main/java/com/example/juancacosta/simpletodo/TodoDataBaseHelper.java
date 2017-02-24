package com.example.juancacosta.simpletodo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Juan C. Acosta on 1/24/2017.
 *
 */

class TodoDataBaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODO = "Todo";

    // T0d0 Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_DATE = "date";
    private static final String KEY_TODO_NAME = "name";
    private static final String KEY_TODO_NOTES = "notes";
    private static final String KEY_TODO_PRIORITY = "priority";
    private static final String KEY_TODO_STATUS = "status";

    private static TodoDataBaseHelper instance;

    TodoDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_TODO = "CREATE TABLE IF NOT EXISTS "
                + TABLE_TODO + "("
                + KEY_TODO_ID + " INTEGER PRIMARY KEY," // and auto increment will be handled with                            primary key
                + KEY_TODO_NAME + " TEXT NOT NULL,"
                + KEY_TODO_DATE + " TEXT,"
                + KEY_TODO_NOTES + " TEXT,"
                + KEY_TODO_PRIORITY + " TEXT,"
                + KEY_TODO_STATUS + " TEXT );";
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," +
                KEY_TODO_NAME + " TEXT," +
                KEY_TODO_DATE + " TEXT," +
                KEY_TODO_NOTES + " TEXT," +
                KEY_TODO_PRIORITY + " TEXT," +
                KEY_TODO_STATUS + " TEXT," +
                ")";
        db.execSQL(CREATE_TABLE_TODO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(db);
        }
    }

    //Add new T0D0
    void addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_NAME, todo.getName());
        values.put(KEY_TODO_DATE, todo.getDate());
        values.put(KEY_TODO_NOTES, todo.getNotes());
        values.put(KEY_TODO_PRIORITY, todo.getPriority());
        values.put(KEY_TODO_STATUS, todo.getStatus());

        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    //Get Single T0D0
    public Todo getTodo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_TODO, new String[]
                        {KEY_TODO_ID, KEY_TODO_NAME, KEY_TODO_DATE, KEY_TODO_NOTES, KEY_TODO_PRIORITY, KEY_TODO_STATUS},
                KEY_TODO_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return new Todo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));
    }

    //Get all T0D0
    ArrayList<Todo> getAllTodo() {
        ArrayList<Todo> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setName(cursor.getString(1));
                todo.setDate(cursor.getString(2));
                todo.setNotes(cursor.getString(3));
                todo.setPriority(Integer.parseInt(cursor.getString(4)));
                todo.setStatus(Integer.parseInt(cursor.getString(5)));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        return todoList;
    }

    // Get T0D0 count
    public int getTodoCount() {
        String countQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    void updateTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO_NAME, todo.getName());
        values.put(KEY_TODO_DATE, todo.getDate());
        values.put(KEY_TODO_NOTES, todo.getNotes());
        values.put(KEY_TODO_PRIORITY, todo.getPriority());
        values.put(KEY_TODO_STATUS, todo.getStatus());
        db.update(TABLE_TODO, values, KEY_TODO_ID + " = ?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }

    void deleteTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_TODO_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        db.close();
    }

}
