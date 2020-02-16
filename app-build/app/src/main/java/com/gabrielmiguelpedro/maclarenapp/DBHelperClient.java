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

    boolean setEmail(String id, String email);

    boolean checkEmail(String email);

    BabyCar getBabyCarById(int id);

    void setIsUsing(int i, User user);

    int getIsUsingById(int aux);

    Historic getHistoricById(int id);

    ArrayList<Transactions> getHistoricByUserId(int id);

    int getLastIdFromTableHistoric();

    String getFirstDateFromCoerdenates(int id);

    String getLastDateFromCoerdenates(int id);

    ArrayList<HistoricCoordinates> getHistoricCoordinatesById(int id);

    double getCarTypeCost(int id);

    int getHistoricCarId();
}
