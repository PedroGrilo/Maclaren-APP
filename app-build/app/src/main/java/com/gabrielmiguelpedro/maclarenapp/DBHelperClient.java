package com.gabrielmiguelpedro.maclarenapp;

import java.util.ArrayList;

public interface DBHelperClient {

    int getLastID();

    long addHistoric(Historic historic);

    void addHistoricCoordinates(HistoricCoordinates historicCoordinates);

    void addTransactions(Transactions transactions);

    void addTransactionsDeposit(Transactions transactions);

    ArrayList<BabyCar> getAllBabyCars();

    float getIdTransactionsValue(int id);

    void setLoggedIn(boolean loggedIn, User user);

    User getUserByEmail(String email);

    int getUseByIdBabyCar(int id);

    boolean setIsUseCar(int value, String aux);

    BabyCar getBabyCarById(int id);

    void setIsUsing(int i, User user);

    int getIsUsingById(int aux);

    Historic getHistoricById(int id);

    int getLastIdFromTableHistoric();

    String getFirstDateFromCoerdenates(int id);

    String getLastDateFromCoerdenates(int id);

    ArrayList<HistoricCoordinates> getHistoricCoordinatesById(int id);
}
