package com.gabrielmiguelpedro.maclarenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "maclarenapp.db";

    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "ID";
    public static final String USERS_EMAIL = "EMAIL";
    public static final String USERS_LASTCODE = "LASTCODE";
    public static final String USERS_isOK = "ISOK";  //isvalido
    public static final String USERS_LOGGED = "LOGGED";
    public static final String USERS_LASTLOGIN = "LASTLOGIN";
    public static final String USERS_USERTYPE = "USERTYPE";  // C = Customer, U = Usher

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String TRANSACTIONS_ID = "ID";
    public static final String TRANSACTIONS_ID_USER = "ID_USER";
    public static final String TRANSACTIONS_VALUE = "VALOR";
    public static final String TRANSACTIONS_ID_HISTORIC = "ID_HISTORIC";

    public static final String TABLE_CARTYPE = "cartype";
    public static final String CARTYPE_ID = "ID";
    public static final String CARTYPE_NAME = "NAME";
    public static final String CARTYPE_BASECOST = "BASECOST";

    public static final String TABLE_CARS = "cars";
    public static final String CARS_ID = "ID";
    public static final String CARS_ID_CARTYPE = "ID_CARTYPE";
    public static final String CARS_COMMENTS = "COMMENTS";

    public static final String TABLE_HISTORIC = "historic";
    public static final String HISTORIC_ID = "ID";
    public static final String HISTORIC_HISTORICDATE = "HISTORICDATE";
    public static final String HISTORIC_ID_CAR = "ID_CAR";
    public static final String HISTORIC_ID_USER = "ID_USER";
    public static final String HISTORIC_ID_TRANSACTIONS = "ID_TRANSACTIONS";
    public static final String HISTORIC_ID_HISTORICCOORDINATES = "ID_HISTORICCOORDINATES";
    public static final String HISTORIC_COST = "COST";

    public static final String TABLE_HISTORICCOORDINATES = "historiccoordinates";
    public static final String HISTORICCOORDINATES_ID = "ID";
    public static final String HISTORICCOORDINATES_DATE = "DATE";
    public static final String HISTORICCOORDINATES_COORLONG = "COORLONG";
    public static final String HISTORICCOORDINATES_COORLAT = "COORLAT";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ( " + USERS_ID + " INTEGER PRIMARYKEY, " + USERS_EMAIL + " TEXT, " + USERS_LASTCODE + " INT, " + USERS_isOK + " INT, " + USERS_LOGGED + " INT, " + USERS_LASTLOGIN + " INTEGER, " + USERS_USERTYPE + " TEXT " + " )";
        //Log.d("BARBICHA", "onCreate DBHelper: " + TABLE_USERS);
        String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS + " ( " + TRANSACTIONS_ID + " INTEGER PRIMARYKEY, " + TRANSACTIONS_VALUE + " INT "+ " FOREIGN KEY ("+TRANSACTIONS_ID_USER+") REFERENCES "+TABLE_USERS+"("+USERS_ID+") " + " FOREIGN KEY ("+TRANSACTIONS_ID_HISTORIC+") REFERENCES "+TABLE_HISTORIC+"("+HISTORIC_ID+"));";
        String CREATE_TABLE_CARTYPE = "CREATE TABLE " + TABLE_CARTYPE + " ( " + CARTYPE_ID + " INTEGER PRIMARYKEY, " + CARTYPE_NAME + " TEXT, "+ CARTYPE_BASECOST + " CARTYPE_BASECOST "+" )";
        String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + " ( " + CARS_ID + " INTEGER PRIMARYKEY, " + CARS_COMMENTS + " TEXT, "+ " FOREIGN KEY ("+CARS_ID_CARTYPE+") REFERENCES "+TABLE_CARTYPE+"("+CARTYPE_ID+"));";
        String CREATE_TABLE_HISTORIC = "CREATE TABLE " + TABLE_HISTORIC + " ( " + HISTORIC_ID + " INTEGER PRIMARYKEY, " + HISTORIC_HISTORICDATE + " INT, "+ HISTORIC_COST + " REAL "+" )";
        String CREATE_TABLE_HISTORICCOORDINATES = "CREATE TABLE " + TABLE_HISTORICCOORDINATES + " ( " + HISTORICCOORDINATES_ID + " INTEGER PRIMARYKEY, " + HISTORICCOORDINATES_DATE + " INT, "+ HISTORICCOORDINATES_COORLONG+" INT, "+ HISTORICCOORDINATES_COORLAT+" INT, "+" )";
        db.execSQL(TABLE_USERS);
    }
}
