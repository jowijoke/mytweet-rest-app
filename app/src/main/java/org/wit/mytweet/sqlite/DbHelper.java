package org.wit.mytweet.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 01/11/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    static final String TAG = DbHelper.class.getSimpleName();
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "mytweet.db";

    static final String USER_TABLE = "users";
    static final String PRIMARY_KEY = "_id";
    static final String COLUMN_FIRSTNAME = "firstName";
    static final String COLUMN_LASTNAME = "lastName";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_PASSWORD = "password";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FIRSTNAME + " TEXT,"
            + COLUMN_LASTNAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    /**
     *  Reference to User object to be added to database
     */
    public void addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, firstName);
        values.put(COLUMN_LASTNAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // Insert record
        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }


    public boolean getUser(String email, String password) {
        String selectQuery = "SELECT * from " + USER_TABLE + " WHERE " +
                COLUMN_EMAIL + " = " + "'" + email + "'" + " AND " + COLUMN_PASSWORD + " = " + "'" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * Queries the database for the number of records.
     *
     * @return The number of records in the dataabase.
     */
    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long numberRecords = DatabaseUtils.queryNumEntries(db, USER_TABLE);
        db.close();
        return numberRecords;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USER_TABLE);
        onCreate(db);
    }


}