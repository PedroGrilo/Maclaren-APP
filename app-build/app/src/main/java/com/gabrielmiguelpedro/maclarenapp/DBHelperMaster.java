package com.gabrielmiguelpedro.maclarenapp;

public interface DBHelperMaster {
    void addUser(User user);

    int getLastID();

    void setIsOk(int isOk, String email);

    /*void setLastCode(String lastcode, String email);*/

    boolean checkEmail(String email);
}
