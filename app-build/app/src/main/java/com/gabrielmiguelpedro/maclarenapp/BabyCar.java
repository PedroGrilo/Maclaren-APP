package com.gabrielmiguelpedro.maclarenapp;

public class BabyCar{

    private int id;
    private BabyCarType babyCarType;
    private boolean inUse;
    private String comments;

    public BabyCar(){}

    public BabyCar(int id, BabyCarType babyCarType, boolean inUse, String comments) {
        this.id = id;
        this.babyCarType = babyCarType;
        this.inUse = inUse;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
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
