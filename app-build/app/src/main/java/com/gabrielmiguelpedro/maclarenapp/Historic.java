package com.gabrielmiguelpedro.maclarenapp;

public class Historic {
    private int id;
    private int date;
    private float cost;
    private HistoricCoordinates historicCoordinates;

    public Historic() { }

    public Historic(int id, int date, float cost, HistoricCoordinates historicCoordinates) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.historicCoordinates = historicCoordinates;
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
}
