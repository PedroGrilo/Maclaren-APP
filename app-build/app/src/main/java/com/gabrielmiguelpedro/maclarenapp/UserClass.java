package com.gabrielmiguelpedro.maclarenapp;


import java.io.Serializable;
import java.util.Date;

public class UserClass implements User, Serializable {

    private String email;
    private String lastCode;
    private Boolean logged;
    private Date lastLogin;

    public UserClass(String email, String lastCode) {
        this.email = email;
        this.lastCode = lastCode;
        logged = true;
        lastLogin = new Date();
    }

    @Override
    public Date getLastLogin() {
        return lastLogin;
    }

    @Override
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public Boolean getLogged() {
        return logged;
    }

    @Override
    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getLastCode() {
        return lastCode;
    }

    @Override
    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

}
