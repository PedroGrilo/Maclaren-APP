package com.gabrielmiguelpedro.maclarenapp;

import java.util.Date;

public class Historic {
    private int id;
    private long date;
    private User user;
    private BabyCar babyCar;

    public Historic() {
    }

    public Historic(int id, long date, User user, BabyCar babyCar) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.babyCar = babyCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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
