package com.gabrielmiguelpedro.maclarenapp;

public class HistoricCoordinates {
    private int id;
    private int date;
    private int longe;
    private int lat;

    public HistoricCoordinates() {}

    public HistoricCoordinates(int id, int date, int longe, int lat) {
        this.id = id;
        this.date = date;
        this.longe = longe;
        this.lat = lat;
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

    public int getLonge() {
        return longe;
    }

    public void setLonge(int longe) {
        this.longe = longe;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}
