package com.gabrielmiguelpedro.maclarenapp;

import java.util.Date;

public class Transactions {
    private int id;
    private double value;
    private User user;
    private Historic historic;
    private Date date;

    public Transactions() {
    }

    //                                                  Historic historic
    public Transactions(int id, double value, User user, Historic historic, Date date) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.historic = historic;
        this.date = date;
    }

    public Transactions(int id, double value, User user, Date date) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Historic getHistoric() {
        return historic;
    }

    public void setHistoric(Historic historic) {
        this.historic = historic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
