package com.gabrielmiguelpedro.maclarenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper implements DBHelperClient, DBHelperMaster {
    public static final String DATABASE_NAME = "maclarenapp.db";

    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "ID";
    public static final String USERS_EMAIL = "EMAIL";
    public static final String USERS_LASTCODE = "LASTCODE";
    public static final String USERS_isOK = "ISOK";  //isvalido
    public static final String USERS_LOGGED = "LOGGED";
    public static final String USERS_LASTLOGIN = "LASTLOGIN";
    public static final String USERS_USERTYPE = "USERTYPE";  // C = Customer, U = Usher
    public static final String USERS_ISUSING = "ISUSING";

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String TRANSACTIONS_ID = "ID";
    public static final String TRANSACTIONS_ID_USER = "ID_USER";
    public static final String TRANSACTIONS_VALUE = "VALOR";
    public static final String TRANSACTIONS_ID_HISTORIC = "ID_HISTORIC";//é isto para ter
    public static final String TRANSACTIONS_DATE = "DATE";

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
    //public static final String HISTORIC_ID_TRANSACTIONS = "ID_TRANSACTIONS";
    //public static final String HISTORIC_ID_HISTORICCOORDINATES = "ID_HISTORICCOORDINATES";
    //public static final String HISTORIC_COST = "COST";

    public static final String TABLE_HISTORICCOORDINATES = "historiccoordinates";
    public static final String HISTORICCOORDINATES_ID = "ID";
    public static final String HISTORICCOORDINATES_DATE = "DATE";
    public static final String HISTORICCOORDINATES_COORLONG = "COORLONG";
    public static final String HISTORICCOORDINATES_COORLAT = "COORLAT";
    private static final String HISTORICCOORDINATES_HISTORYID = "HISTORYID";

    SQLiteDatabase db;
    //chave estrangeira para o id do aluger


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
                + " ( " + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_EMAIL + " TEXT, "
                + USERS_LASTCODE + " INTEGER, "
                + USERS_isOK + " INTEGER, "
                + USERS_LOGGED + " INTEGER, "
                + USERS_LASTLOGIN + " INTEGER, "
                + USERS_ISUSING + " INTEGER, "
                + USERS_USERTYPE + " TEXT " + " );";

        String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS
                + " ( " + TRANSACTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRANSACTIONS_ID_USER + " INTEGER, "
                + TRANSACTIONS_VALUE + " INTEGER, "
                + TRANSACTIONS_DATE + " INTEGER, "
                + TRANSACTIONS_ID_HISTORIC + " INTEGER, "
                + " FOREIGN KEY (" + TRANSACTIONS_ID_USER + ") REFERENCES " + TABLE_USERS + "(" + USERS_ID + "), "
                + " FOREIGN KEY (" + TRANSACTIONS_ID_HISTORIC + ") REFERENCES " + TABLE_HISTORIC + "(" + HISTORIC_ID + "));";


        String CREATE_TABLE_CARTYPE = "CREATE TABLE " + TABLE_CARTYPE
                + " ( " + CARTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CARTYPE_NAME + " TEXT, "
                + CARTYPE_BASECOST + " REAL );";

        String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS
                + " ( " + CARS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CARS_COMMENTS + " TEXT, "
                + CARS_ISUSE + " INTEGER, "
                + CARS_ID_CARTYPE + " INTEGER, "
                + "FOREIGN KEY (" + CARS_ID_CARTYPE + ") REFERENCES " + TABLE_CARTYPE + "(" + CARTYPE_ID + "));";

        String CREATE_TABLE_HISTORIC = "CREATE TABLE " + TABLE_HISTORIC
                + " ( " + HISTORIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HISTORIC_HISTORICDATE + " INTEGER, "
                //+ HISTORIC_COST + " REAL, "
                //+ HISTORIC_ID_HISTORICCOORDINATES + " INTEGER, "
                + HISTORIC_ID_USER + " INTEGER, "
                //+ HISTORIC_ID_TRANSACTIONS + " INTEGER, "
                + HISTORIC_ID_CAR + " INTEGER, "
                //+ "FOREIGN KEY (" + HISTORIC_ID_HISTORICCOORDINATES + ") REFERENCES " + TABLE_HISTORICCOORDINATES + "(" + HISTORICCOORDINATES_ID + "), "
                + "FOREIGN KEY (" + HISTORIC_ID_USER + ") REFERENCES " + TABLE_USERS + "(" + USERS_ID + "), "
                //+ "FOREIGN KEY (" + HISTORIC_ID_TRANSACTIONS + ") REFERENCES " + TABLE_TRANSACTIONS + "(" + TRANSACTIONS_ID + "), "
                + "FOREIGN KEY (" + HISTORIC_ID_CAR + ") REFERENCES " + TABLE_CARS + "(" + CARS_ID + "));";

        String CREATE_TABLE_HISTORICCOORDINATES = "CREATE TABLE " + TABLE_HISTORICCOORDINATES
                + " ( " + HISTORICCOORDINATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HISTORICCOORDINATES_HISTORYID + " INTEGER, "
                + HISTORICCOORDINATES_DATE + " INT, "
                + HISTORICCOORDINATES_COORLONG + " REAL, "
                + HISTORICCOORDINATES_COORLAT + " REAL, "
                + "FOREIGN KEY (" + HISTORICCOORDINATES_HISTORYID + ") REFERENCES " + TABLE_HISTORIC + "(" + HISTORIC_ID + "));";

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_CARTYPE);
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_HISTORIC);
        db.execSQL(CREATE_TABLE_HISTORICCOORDINATES);

        BabyCarType BT1 = new BabyCarType(1, "Carrinho Pequeno", 5);
        BabyCarType BT2 = new BabyCarType(2, "Carrinho Médio", 10);
        BabyCarType BT3 = new BabyCarType(3, "Carrinho Grande", 20);
        BabyCarType BT4 = new BabyCarType(4, "Carrinho Gigante Edér", 0);

        addBabyCarType(db, BT1);
        addBabyCarType(db, BT2);
        addBabyCarType(db, BT3);
        addBabyCarType(db, BT4);

        addBabyCar(db, new BabyCar(1, BT1, 0, "Isto é um carrinho Pequeno do tipo 1"));
        addBabyCar(db, new BabyCar(2, BT2, 0, "Isto é um carrinho Médio do tipo 2"));
        addBabyCar(db, new BabyCar(3, BT3, 0, "Isto é um carrinho Grande do tipo 3"));
        addBabyCar(db, new BabyCar(4, BT4, 0, "Isto é um carrinho Gigante Edér do tipo 4"));
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

    @Override
    public void addUser(User user) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(USERS_EMAIL, user.getEmail()); // get username
        values.put(USERS_isOK, (user.isOk() ? 1 : 0)); // get isok
        values.put(USERS_LASTCODE, user.getLastCode()); // get lastcode
        values.put(USERS_LOGGED, user.isLogged()); // get logged
        values.put(USERS_LASTLOGIN, user.getLastLogin() + ""); // get lastlogin
        values.put(USERS_USERTYPE, user.getUserType() + ""); // get usertype
        values.put(USERS_ISUSING, user.getIsUsing()); // get usertype
        db.insert(TABLE_USERS, null, values);

        db.close();
    }

    public void addBabyCarType(BabyCarType babyCarType) {
        addBabyCarType(null, babyCarType);
    }

    public void addBabyCarType(SQLiteDatabase db, BabyCarType babyCarType) {
        // 1. get reference to writable DB
        boolean dbopened = db != null;
        if (!dbopened)
            db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(CARTYPE_ID, babyCarType.getId());//TESTE
        values.put(CARTYPE_NAME, babyCarType.getName()); // get name
        values.put(CARTYPE_BASECOST, babyCarType.getPrice()); // get basecost

        db.insert(TABLE_CARTYPE, null, values);

        if (!dbopened)
            db.close();
    }

    public void addBabyCar(BabyCar babyCar) {
        addBabyCar(null, babyCar);
    }

    public void addBabyCar(SQLiteDatabase db, BabyCar babyCar) {
        boolean dbopened = db != null;
        if (!dbopened)
            db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CARS_ISUSE, babyCar.getInUse());
        values.put(CARS_COMMENTS, babyCar.getComments());
        values.put(CARS_ID_CARTYPE, babyCar.getBabyCarType().getId());      // FALTAVA O GETID

        //values.put(CARS_ID_CARTYPE, babyCar.getBabyCarType())

        db.insert(TABLE_CARS, null, values);
        if (!dbopened)
            db.close();
    }

    @Override
    public long addHistoric(Historic historic) {
        long rowId;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(HISTORIC_COST, historic.getCost());
        values.put(HISTORIC_HISTORICDATE, historic.getDate() + "");
        values.put(HISTORIC_ID_CAR, historic.getBabyCar().getId());
        values.put(HISTORIC_ID_USER, historic.getUser().getUserID());
        //values.put(HISTORIC_ID_TRANSACTIONS, historic.getTransactions().getId());
        //values.put(HISTORIC_ID_HISTORICCOORDINATES, historic.getHistoricCoordinates().getId());

        rowId = db.insert(TABLE_HISTORIC, null, values);

        db.close();
        return rowId;
    }

    @Override
    public void addHistoricCoordinates(HistoricCoordinates historicCoordinates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HISTORICCOORDINATES_COORLAT, historicCoordinates.getLat());
        values.put(HISTORICCOORDINATES_COORLONG, historicCoordinates.getLonge());
        values.put(HISTORICCOORDINATES_DATE, historicCoordinates.getDate() + "");
        values.put(HISTORICCOORDINATES_HISTORYID, historicCoordinates.getHistoric().getId());

        db.insert(TABLE_HISTORICCOORDINATES, null, values);

        db.close();
    }

    @Override
    public void addTransactions(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRANSACTIONS_DATE, transactions.getDate() + "");
        values.put(TRANSACTIONS_ID_HISTORIC, transactions.getHistoric().getId());
        values.put(TRANSACTIONS_VALUE, transactions.getValue());
        values.put(TRANSACTIONS_ID_USER, transactions.getUser().getUserID());

        db.insert(TABLE_TRANSACTIONS, null, values);

        db.close();
    }

    @Override
    public void addTransactionsDeposit(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRANSACTIONS_DATE, transactions.getDate() + "");
        values.put(TRANSACTIONS_VALUE, transactions.getValue());
        values.put(TRANSACTIONS_ID_USER, transactions.getUser().getUserID());

        db.insert(TABLE_TRANSACTIONS, null, values);

        db.close();
    }

    @Override
    public ArrayList<BabyCar> getAllBabyCars() {
        //ArrayList<BabyCar> babyCarsList = new ArrayList<BabyCar>();
        /*                      0              1                2           3           4                 5*/
        String query = "SELECT cars.id, cars.id_cartype, cars.comments, cars.isuse, cartype.name, cartype.basecost FROM " + TABLE_CARS + " JOIN cartype ON cars.id_cartype = cartype.id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<BabyCar> cars = new ArrayList<BabyCar>();
        while (cursor.moveToNext()) {
            BabyCar bc = new BabyCar();
            bc.setId(cursor.getInt(0));
            bc.setComments(cursor.getString(2));
            bc.setInUse(cursor.getInt(3));

            /** GET CAR TYPE**/
            int bctypeid = cursor.getInt(1);
            String bctypename = cursor.getString(4);
            Float bctypebasecost = cursor.getFloat(5);

            bc.setBabyCarType(new BabyCarType(bctypeid, bctypename, bctypebasecost));

            //Log.d(TAG, "getAllBabyCars: " + bc.getComments());

            cars.add(bc);
        }
        db.close();
        return cars;
    }

    @Override
    public float getIdTransactionsValue(int id) {
        float total = 0;

        String query = "SELECT SUM(" + TRANSACTIONS_VALUE + ") FROM " + TABLE_TRANSACTIONS + " WHERE id_user=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext())
            total = cursor.getFloat(0);
        db.close();
        return total;
    }

    @Override
    public int getLastID() {
        int lastId = 0;
        String query = "SELECT * FROM " + TABLE_USERS + " ORDER BY ID DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext())
            lastId = cursor.getInt(0);
        db.close();
        return lastId;
    }


    @Override
    public void setLoggedIn(boolean loggedIn, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_LOGGED, (loggedIn ? 1 : 0));
        String selection = USERS_EMAIL + " LIKE '" + user.getEmail() + "'";
        db.update(TABLE_USERS, values, selection, null);
        db.close();
    }

    @Override
    public void setIsOk(int isOk, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_isOK, isOk);
        String selection = USERS_EMAIL + " LIKE '" + email + "'";
        db.update(TABLE_USERS, values, selection, null);
        db.close();
    }

    public void setIsUsing(int i, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_ISUSING, i);
        String selection = USERS_EMAIL + " LIKE '" + user.getEmail() + "'";
        db.update(TABLE_USERS, values, selection, null);
        db.close();
    }

    ;

    @Override
    public User getUserByEmail(String email) {
        User user = new User();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERS_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            user.setUserID(cursor.getInt(0));
            user.setEmail(cursor.getString(1));
            user.setLastCode(cursor.getString(2));
            user.setOk(cursor.getInt(3) > 0);
            user.setLogged(cursor.getInt(4) > 0);
            user.setLastLogin(new Date());
            user.setUserType(cursor.getString(6).charAt(0));
        }
        db.close();
        return user;
    }

    @Override
    public int getUseByIdBabyCar(int aux) {
        int isUse = 0;

        String query = "SELECT " + CARS_ISUSE + " FROM " + TABLE_CARS + " WHERE " + CARS_ID + "=" + aux;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            isUse = cursor.getInt(0);
        db.close();
        return isUse;
    }

    @Override
    public boolean setIsUseCar(int value, String aux) {
        //String query = "UPDATE " +TABLE_CARS+" SET "+CARS_ISUSE+" = "+value+" WHERE "+CARS_ID+" = "+aux;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARS_ISUSE, value);
        db.update(TABLE_CARS, contentValues, "id = ?", new String[]{aux});
        return true;
        //Cursor cursor = db.rawQuery(query, null);
        //db.close();
    }


    public boolean checkEmail(String email) {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERS_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int counter = 0;

        while (cursor.moveToNext())
            counter++;

        return counter >= 1;

    }

    @Override
    public BabyCar getBabyCarById(int id) {
        BabyCar babyCar = new BabyCar();

        String query = "SELECT " + CARS_ID + " FROM " + TABLE_CARS + " WHERE " + CARS_ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            babyCar.setId(cursor.getInt(0));
        }
        db.close();
        return babyCar;
    }

    @Override
    public int getIsUsingById(int aux) {
        int isUsing = 0;

        String query = "SELECT " + USERS_ISUSING + " FROM " + TABLE_USERS + " WHERE " + USERS_ID + "=" + aux;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            isUsing = cursor.getInt(0);
        db.close();
        return isUsing;
    }

    @Override
    public Historic getHistoricById(int id) {
        Historic historic = new Historic();

        String query = "SELECT " + HISTORIC_ID +" FROM " + TABLE_HISTORIC + " WHERE " + HISTORIC_ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            historic.setId(cursor.getInt(0));
        }
        db.close();
        return historic;
    }


    @Override
    public ArrayList<Historic> getHistoricByUserId(int id) {


        String query = "SELECT HISTORIC.ID, HISTORICDATE,cartype.ID, cartype.NAME , cartype.BASECOST,CARS.COMMENTS FROM HISTORIC JOIN cars on ID_CAR  = CARS.ID JOIN cartype ON cars.ID_CARTYPE = cartype.ID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Historic> historicList = new ArrayList<Historic>()
                ;
        while (cursor.moveToNext()) {
            Historic historic = new Historic();
            historic.setId(cursor.getInt(0));
            historic.setDate(new Date(cursor.getLong(1)));

            BabyCarType babyCarType = new BabyCarType();
            babyCarType.setId(cursor.getInt(2));
            babyCarType.setName(cursor.getString(3));
            babyCarType.setPrice(cursor.getFloat(4));

            BabyCar babyCar = new BabyCar();
            babyCar.setComments(cursor.getString(5));
            babyCar.setBabyCarType(babyCarType);

            historic.setBabyCar(babyCar);
            historicList.add(historic);
        }

        db.close();
        return historicList;
    }

    @Override
    public int getLastIdFromTableHistoric() {
        int id = 0;

        String query = "SELECT " + HISTORIC_ID + " FROM " + TABLE_HISTORIC + " ORDER BY " + HISTORIC_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            id = cursor.getInt(0);
        db.close();
        return id;
    }


    @Override
    public String getFirstDateFromCoerdenates(int id) {
        String date = "";

        //String query = "SELECT " + HISTORICCOORDINATES_DATE + " FROM " + TABLE_HISTORICCOORDINATES + " JOIN " + TABLE_HISTORIC + " ON " + HISTORICCOORDINATES_HISTORYID +"="+ HISTORIC_ID + " WHERE " + HISTORICCOORDINATES_HISTORYID + "=" + id +" ORDER BY " + HISTORICCOORDINATES_ID +" DESC LIMIT 1";
        String query = "SELECT historiccoordinates.date FROM historiccoordinates JOIN historic ON historiccoordinates.HISTORYID = historic.id WHERE historiccoordinates.HISTORYID = " + id + " ORDER BY historiccoordinates.id ASC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            date = cursor.getString(0);
        db.close();
        return date;
    }

    @Override
    public String getLastDateFromCoerdenates(int id) {
        String date = "";

        String query = "SELECT historiccoordinates.date FROM historiccoordinates JOIN historic ON historiccoordinates.HISTORYID = historic.id WHERE historiccoordinates.HISTORYID = " + id + " ORDER BY historiccoordinates.id DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            date = cursor.getString(0);
        db.close();
        return date;
    }

    @Override
    public ArrayList<HistoricCoordinates> getHistoricCoordinatesById(int id) {
        String query = "SELECT historiccoordinates.COORLAT, historiccoordinates.COORLONG FROM historiccoordinates JOIN historic ON historiccoordinates.HISTORYID = historic.id WHERE historiccoordinates.HISTORYID = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<HistoricCoordinates> historicCoordinatesArrayList = new ArrayList<HistoricCoordinates>();
        while (cursor.moveToNext()) {
            HistoricCoordinates historicCoordinates = new HistoricCoordinates();

            historicCoordinates.setLat(cursor.getDouble(0));
            historicCoordinates.setLonge(cursor.getDouble(0));

            historicCoordinatesArrayList.add(historicCoordinates);
        }
        db.close();
        return historicCoordinatesArrayList;
    }

    public double getCarTypeCostByHistoricId(int id){
        double cost = 0;

        String query = "SELECT " + CARTYPE_BASECOST +" FROM " + TABLE_CARTYPE + " WHERE " + HISTORIC_ID_USER + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
            cost = cursor.getDouble(0);
        db.close();
        return cost;
    }

}