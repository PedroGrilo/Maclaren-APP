package com.gabrielmiguelpedro.maclarenapp;

import android.location.Location;

import java.util.Date;

interface User {
    Location getLastLocation();

    void setLastLocation(Location lastLocation);

    Date getLastLogin();

    void setLastLogin(Date lastLogin);

    Boolean getLogged();

    void setLogged(Boolean logged);

    String getEmail();

    void setEmail(String email);

    String getLastCode();

    void setLastCode(String lastCode);


    String GetName();
}
