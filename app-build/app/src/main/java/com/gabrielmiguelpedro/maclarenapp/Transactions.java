package com.gabrielmiguelpedro.maclarenapp;

public class Transactions {
    private int id;
    private double value;
    private User user;
    private Historic historic;

    public Transactions() { }
//                                                  Historic historic
    public Transactions(int id, double value, User user, Historic historic) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.historic = historic;
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
}
