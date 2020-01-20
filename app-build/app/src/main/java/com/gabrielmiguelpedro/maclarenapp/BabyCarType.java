package com.gabrielmiguelpedro.maclarenapp;

public class BabyCarType {

    private int babyCarTypeId;
    private float price;
    private String name;

    public BabyCarType(int babyCarTypeId, float price, String name) {
        this.babyCarTypeId = babyCarTypeId;
        this.price = price;
        this.name = name;
    }

    public int getBabyCarTypeId() {
        /** if(cartype ==  null) cartype = dbhelper.get(id)
         * return cartype **/
        return babyCarTypeId;

    }

    public void setBabyCarTypeId(int babyCarTypeId) {
        this.babyCarTypeId = babyCarTypeId;
    }

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
}
