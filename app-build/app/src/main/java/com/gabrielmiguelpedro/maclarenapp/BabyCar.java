package com.gabrielmiguelpedro.maclarenapp;

public class BabyCar {

    private int id;
    private BabyCarType babyCarType;
    private int inUse;
    private String comments;

    public BabyCar() {
    }

    public BabyCar(int id, BabyCarType babyCarType, int inUse, String comments) {
        this.id = id;
        this.babyCarType = babyCarType;
        this.inUse = inUse;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BabyCarType getBabyCarType() {
        return babyCarType;
    }

    public void setBabyCarType(BabyCarType babyCarType) {
        this.babyCarType = babyCarType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
