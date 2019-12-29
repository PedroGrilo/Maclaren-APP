package com.gabrielmiguelpedro.maclarenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.location.Location;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "maclarenapp.db";
    public static final String TABLE_NAME = "user_table";

    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_LASTCODE = "LASTCODE";
    public static final String COL_LOGGED = "LOGGED";
    public static final String COL_LASTLOGIN = "LASTLOGIN";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, LASTCODE TEXT, LOGGED INTEGER, LASTLOGIN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertValues(String email, String lastcode){
        /*Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.format(date)*/
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_LASTCODE,lastcode);
        contentValues.put(COL_LOGGED,1);
        contentValues.put(COL_LASTLOGIN, "");

        db.insert(TABLE_NAME, null, contentValues);
    }
}
