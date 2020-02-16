package com.gabrielmiguelpedro.maclarenapp;

import java.util.Date;

public class HistoricCoordinates {
    private int id;
    private Date date;
    private double longe;
    private double lat;
    private Historic historic;

    public HistoricCoordinates() {
    }

    public HistoricCoordinates(int id, Date date, double longe, double lat, Historic historic) {
        this.id = id;
        this.date = date;
        this.longe = longe;
        this.lat = lat;
        this.historic = historic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLonge() {
        return longe;
    }

    public void setLonge(double longe) {
        this.longe = longe;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Historic getHistoric() {
        return historic;
    }

    public void setHistoric(Historic historic) {
        this.historic = historic;
    }
}
