package com.gabrielmiguelpedro.maclarenapp;

import java.util.ArrayList;

public interface DBHelperClient {

    int getLastID();

    void addTransactions(Transactions transactions);

    void addTransactionsDeposit(Transactions transactions);

    ArrayList<BabyCar> getAllBabyCars();

    float getIdTransactionsValue(int id);

    void setLoggedIn(boolean loggedIn, String email);

    User getUserByEmail(String email);

    int getUseByIdBabyCar(int id);

    boolean setIsUseCar(int value, String aux);

    void logOut(User user);
}
