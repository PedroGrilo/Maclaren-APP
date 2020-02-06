package com.gabrielmiguelpedro.maclarenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static java.sql.Types.INTEGER;
import static org.xmlpull.v1.XmlPullParser.TEXT;

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
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ( "+ USERS_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + USERS_EMAIL + " TEXT, " + USERS_LASTCODE + " INT, " + USERS_isOK + " INT, " + USERS_LOGGED + " INT, " + USERS_LASTLOGIN + " INTEGER, " + USERS_USERTYPE + " TEXT " + " )";
        String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS + " ( " + TRANSACTIONS_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + TRANSACTIONS_VALUE + " INT "+ " FOREIGN KEY ("+TRANSACTIONS_ID_USER+") REFERENCES "+TABLE_USERS+"("+USERS_ID+") " + " FOREIGN KEY ("+TRANSACTIONS_ID_HISTORIC+") REFERENCES "+TABLE_HISTORIC+"("+HISTORIC_ID+"));";
        String CREATE_TABLE_CARTYPE = "CREATE TABLE " + TABLE_CARTYPE + " ( " + CARTYPE_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + CARTYPE_NAME + " TEXT, "+ CARTYPE_BASECOST + " REAL ) ";
        String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + " ( " + CARS_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + CARS_COMMENTS + " TEXT, "+ " FOREIGN KEY ("+CARS_ID_CARTYPE+") REFERENCES "+TABLE_CARTYPE+"("+CARTYPE_ID+"));";
        String CREATE_TABLE_HISTORIC = "CREATE TABLE " + TABLE_HISTORIC + " ( " + HISTORIC_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + HISTORIC_HISTORICDATE + " INT, "+ HISTORIC_COST + " REAL "+" FOREIGN KEY ("+HISTORIC_ID_HISTORICCOORDINATES+") REFERENCES "+TABLE_HISTORICCOORDINATES+"("+HISTORICCOORDINATES_ID+"));";
        String CREATE_TABLE_HISTORICCOORDINATES = "CREATE TABLE " + TABLE_HISTORICCOORDINATES + " ( " + HISTORICCOORDINATES_ID + " INTEGER PRIMARYKEY AUTOINCREMENT, " + HISTORICCOORDINATES_DATE + " INT, "+ HISTORICCOORDINATES_COORLONG+" INT, "+ HISTORICCOORDINATES_COORLAT+" INT )";
        db.execSQL(TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARTYPE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORIC);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORICCOORDINATES);
        this.onCreate(db);

        addBabyCarType(new BabyCarType(1,"Carrinho Pequeno",5));
        addBabyCarType(new BabyCarType(2,"Carrinho Médio",10));
        addBabyCarType(new BabyCarType(3,"Carrinho Grande",20));
        addBabyCarType(new BabyCarType(4,"Carrinho Gigante Edér",0));
    }




   public void addUser(User user){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(USERS_EMAIL, user.getEmail()); // get username
        values.put(USERS_isOK,user.isOk()); // get isok
        values.put(USERS_LASTCODE, user.getLastCode()); // get lastcode
        values.put(USERS_LOGGED,user.isLogged()); // get logged
        values.put(USERS_LASTLOGIN, user.getLastLogin()+""); // get lastlogin
        values.put(USERS_USERTYPE, user.getUserType()+""); // get usertype

        db.insert(TABLE_USERS, null, values);

        db.close();
    }

    public void addBabyCarType(BabyCarType babyCarType){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(CARTYPE_NAME, babyCarType.getName()); // get name
        values.put(CARTYPE_BASECOST,babyCarType.getPrice()); // get basecost

        db.insert(TABLE_CARTYPE, null, values);

        db.close();
    }


    public void addBabyCar(BabyCar babyCar){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(CARS_COMMENTS, babyCar.getComments());
            //values.put(CARS_ID_CARTYPE, babyCar.getBabyCarType()); ERRO

            db.insert(TABLE_CARS, null, values);
            db.close();
    }

    public void addHistoric(Historic historic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HIS);
    }

}
