package com.gabrielmiguelpedro.maclarenapp;

public class BabyCarType {

    private int id;
    private float price;
    private String name;


    public BabyCarType(int id,String name, float price) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public BabyCarType(){}

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
