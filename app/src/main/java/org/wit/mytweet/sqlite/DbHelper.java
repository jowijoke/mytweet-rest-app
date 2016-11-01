package org.wit.mytweet.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.wit.mytweet.models.User;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 01/11/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    static final String TAG = "DbHelper";
    static final String DATABASE_NAME = "users.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_USERS = "tableUsers";

    static final String PRIMARY_KEY = "id";
    static final String FIRSTNAME = "firstName";
    static final String LASTNAME = "lastName";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";


    Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE tableUsers " +
                        "(id text primary key, " +
                        "firstName text)" +
                        "lastName text," +
                        "email text," +
                        "password text,";

        db.execSQL(createTable);
        Log.d(TAG, "DbHelper.onCreated: " + createTable);
    }

    /**
     * @param user Reference to User object to be added to database
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY, user.id.toString());
        values.put(FIRSTNAME, user.firstName);

        values.put(LASTNAME, user.lastName);
        values.put(EMAIL, user.email);
        values.put(PASSWORD, user.password);
        // Insert record
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User selectUser(UUID userId) {
        User user;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            user = new User();

            cursor = db.rawQuery("SELECT * FROM tableUsers WHERE id = ?", new String[]{userId.toString() + ""});

            if (cursor.getCount() > 0) {
                int columnIndex = 0;
                cursor.moveToFirst();
                user.id = UUID.fromString(cursor.getString(columnIndex++));
                user.firstName = cursor.getString(columnIndex++);
                user.lastName = cursor.getString(columnIndex++);
                user.email = cursor.getString(columnIndex++);
                user.password = cursor.getString(columnIndex++);

            }
        } finally {
            cursor.close();
        }
        return user;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("tableUsers", "id" + "=?", new String[]{user.id.toString() + ""});
        } catch (Exception e) {
            Log.d(TAG, "delete user failure: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }

    /**
     * Query database and select entire tableUsers.
     *
     * @return A list of User object records
     */
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + "tableUsers";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int columnIndex = 0;
            do {
                User user = new User();
                user.id = UUID.fromString(cursor.getString(columnIndex++));
                user.firstName = cursor.getString(columnIndex++);
                user.lastName = cursor.getString(columnIndex++);
                user.email = cursor.getString(columnIndex++);
                user.password = cursor.getString(columnIndex++);

                columnIndex = 0;

                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    /**
     * Delete all records
     */
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from tableUsers");
        } catch (Exception e) {
            Log.d(TAG, "delete users failure: " + e.getMessage());
        }
    }

    /**
     * Queries the database for the number of records.
     *
     * @return The number of records in the dataabase.
     */
    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long numberRecords = DatabaseUtils.queryNumEntries(db, TABLE_USERS);
        db.close();
        return numberRecords;
    }

    /**
     * Update an existing User record.
     * All fields except record id updated.
     *
     * @param user The User record being updated.
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(FIRSTNAME, user.firstName);
            values.put(LASTNAME, user.lastName);
            values.put(EMAIL, user.email);
            values.put(PASSWORD, user.password);
            db.update("tableUsers", values, "id" + "=?", new String[]{user.id.toString() + ""});
        } catch (Exception e) {
            Log.d(TAG, "update users failure: " + e.getMessage());
        }
    }
}