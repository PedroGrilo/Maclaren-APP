package com.gabrielmiguelpedro.maclarenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    public static final String CARS_ISUSE = "ISUSE";

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
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
                + " ( "+ USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_EMAIL + " TEXT, "
                + USERS_LASTCODE + " INTEGER, "
                + USERS_isOK + " INTEGER, "
                + USERS_LOGGED + " INTEGER, "
                + USERS_LASTLOGIN + " INTEGER, "
                + USERS_USERTYPE + " TEXT " + " );";

        String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS
                + " ( " + TRANSACTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRANSACTIONS_ID_USER + " INTEGER, "
                + TRANSACTIONS_VALUE + " INTEGER, "
                + TRANSACTIONS_ID_HISTORIC + " INTEGER, "
                + " FOREIGN KEY ("+TRANSACTIONS_ID_USER+") REFERENCES "+TABLE_USERS+"("+USERS_ID+"), "
                + " FOREIGN KEY ("+TRANSACTIONS_ID_HISTORIC+") REFERENCES "+TABLE_HISTORIC+"("+HISTORIC_ID+"));";


        String CREATE_TABLE_CARTYPE = "CREATE TABLE " + TABLE_CARTYPE
                + " ( " + CARTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CARTYPE_NAME + " TEXT, "
                + CARTYPE_BASECOST + " REAL );";

        String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS
                + " ( " + CARS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CARS_COMMENTS + " TEXT, "
                + CARS_ISUSE + " INTEGER, "
                + CARS_ID_CARTYPE + " INTEGER, "
                + "FOREIGN KEY ("+CARS_ID_CARTYPE+") REFERENCES "+TABLE_CARTYPE+"("+CARTYPE_ID+"));";

        String CREATE_TABLE_HISTORIC = "CREATE TABLE " + TABLE_HISTORIC
                + " ( " + HISTORIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HISTORIC_HISTORICDATE + " INTEGER, "
                + HISTORIC_COST + " REAL, "
                + HISTORIC_ID_HISTORICCOORDINATES + " INTEGER, "
                + HISTORIC_ID_USER + " INTEGER, "
                + HISTORIC_ID_TRANSACTIONS + " INTEGER, "
                + HISTORIC_ID_CAR + " INTEGER, "
                +"FOREIGN KEY ("+HISTORIC_ID_HISTORICCOORDINATES+") REFERENCES "+TABLE_HISTORICCOORDINATES+"("+HISTORICCOORDINATES_ID+"), "
                +"FOREIGN KEY ("+HISTORIC_ID_USER+") REFERENCES "+TABLE_USERS+"("+USERS_ID+"), "
                +"FOREIGN KEY ("+HISTORIC_ID_TRANSACTIONS+") REFERENCES "+TABLE_TRANSACTIONS+"("+TRANSACTIONS_ID+"), "
                +"FOREIGN KEY ("+HISTORIC_ID_CAR+") REFERENCES "+TABLE_CARS+"("+CARS_ID+"));";

        String CREATE_TABLE_HISTORICCOORDINATES = "CREATE TABLE " + TABLE_HISTORICCOORDINATES
                + " ( " + HISTORICCOORDINATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HISTORICCOORDINATES_DATE + " INT, "
                + HISTORICCOORDINATES_COORLONG+" INT, "
                + HISTORICCOORDINATES_COORLAT+" INT );";

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_CARTYPE);
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_HISTORIC);
        db.execSQL(CREATE_TABLE_HISTORICCOORDINATES);

        BabyCarType BT1 = new BabyCarType(1,"Carrinho Pequeno",5);
        BabyCarType BT2 = new BabyCarType(2,"Carrinho Médio",10);
        BabyCarType BT3 = new BabyCarType(3,"Carrinho Grande",20);
        BabyCarType BT4 = new BabyCarType(4,"Carrinho Gigante Edér",0);

        addBabyCarType(db, new BabyCarType(1,"Carrinho Pequeno",5));
        addBabyCarType(db, new BabyCarType(2,"Carrinho Médio",10));
        addBabyCarType(db, new BabyCarType(3,"Carrinho Grande",20));
        addBabyCarType(db, new BabyCarType(4,"Carrinho Gigante Edér",0));

        addBabyCar(db, new BabyCar(1, BT1, false, "Isto é um carrinho Pequeno do tipo 1"));
        addBabyCar(db, new BabyCar(2, BT2, false, "Isto é um carrinho Médio do tipo 2"));
        addBabyCar(db, new BabyCar(3, BT3, false, "Isto é um carrinho Grande do tipo 3"));
        addBabyCar(db, new BabyCar(4, BT4, false, "Isto é um carrinho Gigante Edér do tipo 4"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTIONS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARTYPE);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORIC);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORICCOORDINATES);
//        this.onCreate(db);
    }


// JB Parece Ok

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
        addBabyCarType(null, babyCarType);
    }

    public void addBabyCarType(SQLiteDatabase db, BabyCarType babyCarType){
        // 1. get reference to writable DB
        boolean dbopened = db != null;
        if ( !dbopened )
            db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(CARTYPE_ID, babyCarType.getId());//TESTE
        values.put(CARTYPE_NAME, babyCarType.getName()); // get name
        values.put(CARTYPE_BASECOST,babyCarType.getPrice()); // get basecost

        db.insert(TABLE_CARTYPE, null, values);

        if ( !dbopened )
            db.close();
    }

    public void addBabyCar(BabyCar babyCar){
        addBabyCar(null, babyCar);
    }

    public void addBabyCar(SQLiteDatabase db, BabyCar babyCar){
        boolean dbopened = db != null;
        if ( !dbopened )
            db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CARS_ISUSE, babyCar.isInUse());
        values.put(CARS_COMMENTS, babyCar.getComments());
        values.put(CARS_ID_CARTYPE, babyCar.getBabyCarType().getId());      // FALTAVA O GETID

        //values.put(CARS_ID_CARTYPE, babyCar.getBabyCarType())

        db.insert(TABLE_CARS, null, values);
        if ( !dbopened )
            db.close();
    }

    public void addHistoric(Historic historic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HISTORIC_COST, historic.getCost());
        values.put(HISTORIC_HISTORICDATE, historic.getDate());
        values.put(HISTORIC_ID_CAR, historic.getBabyCar().getId());
        values.put(HISTORIC_ID_USER, historic.getUser().getUserID());
        values.put(HISTORIC_ID_TRANSACTIONS, historic.getTransactions().getId());
        values.put(HISTORIC_ID_HISTORICCOORDINATES, historic.getHistoricCoordinates().getId());

        db.insert(TABLE_HISTORIC, null, values);

        db.close();
    }

    public void addHistoricCoordinates(HistoricCoordinates historicCoordinates){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HISTORICCOORDINATES_COORLAT, historicCoordinates.getLat());
        values.put(HISTORICCOORDINATES_COORLONG, historicCoordinates.getLonge());
        values.put(HISTORICCOORDINATES_DATE, historicCoordinates.getDate());

        db.insert(TABLE_HISTORICCOORDINATES, null, values);

        db.close();
    }

    public void addTransactions(Transactions transactions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRANSACTIONS_ID_HISTORIC, transactions.getHistoric().getId());
        values.put(TRANSACTIONS_VALUE, transactions.getValue());
        values.put(TRANSACTIONS_ID_USER, transactions.getUser().getUserID());

        db.insert(TABLE_TRANSACTIONS, null, values);

        db.close();
    }

    /*public List<BabyCar> getAllBabyCars(){
        List<BabyCar> babyCarsList = new LinkedList<BabyCar>();

        String query = "SELECT * FROM " + TABLE_CARS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        BabyCar babyCar = null;

        if (cursor.moveToFirst()) {
            do {
                boolean inUse = cursor.getInt(3) > 0;
                if(!inUse){
                    babyCar = new BabyCar();
                    babyCar.setId(Integer.parseInt(cursor.getString(0)));
                    babyCar.setComments(cursor.getString(2));
                    babyCar.setInUse(false);
                }
                babyCarsList.add(babyCar);
            } while (cursor.moveToNext());
        }

        db.close();
        return babyCarsList;
    }*/

    public List<BabyCar> getAllBabyCars(){
        int idAux;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_CARS, null);
        List<BabyCar> cars = new ArrayList<BabyCar>();

        while (cursor.moveToNext()){
            BabyCar bc = new BabyCar();
            bc.setId(cursor.getInt(0));
            bc.setComments(cursor.getString(1));
            bc.setInUse(false);

            idAux = cursor.getInt(3);

            Cursor cursorAux = db.rawQuery("select * from "+TABLE_CARTYPE, null);
            List<BabyCarType> carsType = new ArrayList<BabyCarType>();
            while (cursor.moveToNext()){
                BabyCarType bct = new BabyCarType();
                bct.setId(cursorAux.getInt(0));
                bct.setName(cursorAux.getString(1));
                bct.setPrice(cursorAux.getFloat(2));

                carsType.add(bct);
            }

            for (BabyCarType babyCarType : carsType) {
                if(babyCarType.getId()==idAux){
                    bc.setBabyCarType(babyCarType);
                }
            }

            cars.add(bc);
        }
        db.close();
        return cars;
    }
}