package com.gabrielmiguelpedro.maclarenapp;


public class UserClass implements User {

    private String email;
    private String lastCode;

    public UserClass(String email, String lastCode) {
        this.email = email;
        this.lastCode = lastCode;
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
