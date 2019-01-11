package com.fordperformanceclubofact.groupb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Car_Parking.db";
    // User table name
    private static final String TABLE_USER = "users";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_FIRST_NAME = "user_user_name";
    private static final String COLUMN_USER_LAST_NAME = "user_password";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + "(" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_user_name TEXT," +
                "user_password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public boolean addUser(String user, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_FIRST_NAME, user);

        values.put(COLUMN_USER_LAST_NAME, pass);


        long bool = db.insert(TABLE_USER, null, values);

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public boolean updateUser(String user, String id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER_LAST_NAME, user);

        long bool = database.update(TABLE_USER, contentValues, "" + COLUMN_USER_ID + " =?", new String[]{id});

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public Cursor searchEmail() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        return cursor;
    }
}
