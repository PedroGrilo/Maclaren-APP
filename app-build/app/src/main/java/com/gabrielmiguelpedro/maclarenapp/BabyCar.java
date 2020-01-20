package com.gabrielmiguelpedro.maclarenapp;

public class BabyCar{

    private int babyCarId;
    private BabyCarType babyCarType;
    private boolean inUse;
    private String comments;

    public BabyCar(){}

    public BabyCar(int babyCarId, BabyCarType babyCarType, String comments) {
        this.babyCarId = babyCarId;
        this.babyCarType = babyCarType;
        this.comments = comments;
    }

    public int getBabyCarId() {
        return babyCarId;
    }

    public void setBabyCarId(int babyCarId) {
        this.babyCarId = babyCarId;
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
