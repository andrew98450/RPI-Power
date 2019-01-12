package com.pi.andrew.rpipower;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLBase extends SQLiteOpenHelper {
    static String db_name = "login";
    static int ver = 1;
    public SQLBase(Context context) {
        super(context, db_name, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE logins(%s text, %s text)","username","password"));
        ContentValues value = new ContentValues();
        value.put("username","pi");
        value.put("password","andrew98450");
        db.insert("logins",null,value);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
