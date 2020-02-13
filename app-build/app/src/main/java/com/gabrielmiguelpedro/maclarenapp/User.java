package com.gabrielmiguelpedro.maclarenapp;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private int userID;
    private String email;
    private String lastCode;
    private boolean isOk;
    private Date lastLogin;
    private char userType;
    private boolean logged;
    private int isUsing;

    public User() {
    }


    public User(int userID, String email, String lastCode, boolean isOk, Date lastLogin, char userType, boolean logged, int isUsing) {

        this.userID = userID;
        this.email = email;
        this.lastCode = lastCode;
        this.isOk = isOk;
        this.lastLogin = lastLogin;
        this.userType = userType;
        this.logged = logged;
        this.isUsing = isUsing;
    }

    public int getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(int isUsing) {
        this.isUsing = isUsing;
    }

    public boolean isOk() {
        return isOk;
    }


    public void setOk(boolean ok) {
        isOk = ok;
    }


    public boolean isLogged() {
        return logged;
    }


    public void setLogged(boolean logged) {
        this.logged = logged;
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

    public String GetName() {
        return email.split("@")[0];
    }


}
