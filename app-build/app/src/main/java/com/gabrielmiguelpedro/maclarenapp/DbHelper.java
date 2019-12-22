package com.gabrielmiguelpedro.maclarenapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;

import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "maclarenappDB";
    public static final String TABLE_NAME = "user_table";

    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_LASTCODE = "lastCode";
    public static final boolean COL_LOGGED = false;
    public static final Date COL_LASTLOGIN = new Date();

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
