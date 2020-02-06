package com.gabrielmiguelpedro.maclarenapp;

public class Transactions {
    private int id;
    private float value;
    private User user;
    private Historic historic;

    public Transactions() { }

    public Transactions(int id, float value, User user, Historic historic) {
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
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
