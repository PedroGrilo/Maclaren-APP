package com.gabrielmiguelpedro.maclarenapp;

import java.util.Date;

public class HistoricCoordinates {
    private int id;
    private Date date;
    private int longe;
    private int lat;
    private Historic historic;

    public HistoricCoordinates() {
    }

    public HistoricCoordinates(int id, Date date, int longe, int lat, Historic historic) {
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

    public Historic getHistoric() {
        return historic;
    }

    public void setHistoric(Historic historic) {
        this.historic = historic;
    }
}
