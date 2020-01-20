package com.gabrielmiguelpedro.maclarenapp;


import android.location.Location;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private int userID;
    private String email;
    private String lastCode;
    private int isOk;
    private Date lastLogin;
    private char userType;

    public User(){}

    public User(int userID, String email, String lastCode, int isOk, Date lastLogin, char userType) {
        this.userID = userID;
        this.email = email;
        this.lastCode = lastCode;
        this.isOk = isOk;
        this.lastLogin = lastLogin;
        this.userType = userType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastCode() {
        return lastCode;
    }

    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    public String GetName(){
        return email.split("@")[0];
    }




}
