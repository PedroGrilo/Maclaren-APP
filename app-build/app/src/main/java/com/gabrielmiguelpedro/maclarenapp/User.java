package com.gabrielmiguelpedro.maclarenapp;

import java.util.Date;

interface User {
    Date getLastLogin();

    void setLastLogin(Date lastLogin);

    Boolean getLogged();

    void setLogged(Boolean logged);

    String getEmail();

    void setEmail(String email);

    String getLastCode();

    void setLastCode(String lastCode);


}
