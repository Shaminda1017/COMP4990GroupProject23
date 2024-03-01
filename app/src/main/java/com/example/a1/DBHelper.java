package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db",  null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(email TEXT primary key, Name TEXT, hnum TEXT, phone NUMBER, password TEXT, userType TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int il) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password, String userType){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", username);
        contentValues.put("password", password);
        contentValues.put("userType", userType);
        long result = MyDB.insert("users", null, contentValues);
        if(result == -1) return  false;
        else
            return true;
    }


    public  Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return  false;
    }

    public Boolean checkUsernamePassword(String username, String password, String userType) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE email = ? AND password = ? AND userType = ?", new String[]{username, password, userType});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
