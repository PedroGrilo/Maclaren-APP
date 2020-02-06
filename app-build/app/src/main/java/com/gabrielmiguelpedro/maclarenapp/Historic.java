package com.gabrielmiguelpedro.maclarenapp;

public class Historic {
    private int id;
    private int date;
    private float cost;
    private HistoricCoordinates historicCoordinates;
    private Transactions transactions;
    private User user;
    private BabyCar babyCar;

    public Historic() { }

    public Historic(int id, int date, float cost, HistoricCoordinates historicCoordinates, Transactions transactions, User user, BabyCar babyCar) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.historicCoordinates = historicCoordinates;
        this.transactions = transactions;
        this.user = user;
        this.babyCar = babyCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public HistoricCoordinates getHistoricCoordinates() {
        return historicCoordinates;
    }

    public void setHistoricCoordinates(HistoricCoordinates historicCoordinates) {
        this.historicCoordinates = historicCoordinates;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BabyCar getBabyCar() {
        return babyCar;
    }

    public void setBabyCar(BabyCar babyCar) {
        this.babyCar = babyCar;
    }
}
